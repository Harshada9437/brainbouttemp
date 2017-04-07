package com.heednow.service;

import com.heednow.bo.FeedbackListRequestBO;
import com.heednow.bo.FeedbackRequestBO;
import com.heednow.bo.FeedbackTrackingRequestBO;
import com.heednow.dao.device.DeviceDAO;
import com.heednow.dao.outlet.OutletDAO;
import com.heednow.dto.request.DeviceDTO;
import com.heednow.dto.request.OutletDTO;
import com.heednow.exceptions.FeedbackNotFoundException;
import com.heednow.request.feedback.*;
import com.heednow.requesthandler.FeedbackRequestHandler;
import com.heednow.response.feedback.*;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by user on 10/18/2016.
 */
@Path("/feedback")
public class FeedbackService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addFeedback(FeedbackRequest feedbackRequest) {
        FeedbackRequestBO feedbackRequestBO = new FeedbackRequestBO();
        feedbackRequestBO.setOutletId(feedbackRequest.getOutletId());
        feedbackRequestBO.setFeedbacks(feedbackRequest.getFeedbacks());
        feedbackRequestBO.setTableNo(feedbackRequest.getTableNo());
        feedbackRequestBO.setBillNo(feedbackRequest.getBillNo());
        feedbackRequestBO.setDate(feedbackRequest.getDate());
        feedbackRequestBO.setCustomer(feedbackRequest.getCustomer());
        feedbackRequestBO.setToken(feedbackRequest.getToken());

        MessageResponse messageResponse = new MessageResponse();
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        int feedbackId = 0;
        try {
            OutletDTO dto = OutletDAO.getOutletById(feedbackRequestBO.getOutletId());
            DeviceDTO deviceDTO = DeviceDAO.getDevice(feedbackRequestBO.getToken());
            if (deviceDTO.getStatus().equals("A") && deviceDTO!=null && dto.getPosStoreId().equals(deviceDTO.getStoreId()) ) {
                feedbackRequestBO.setClientId(deviceDTO.getClientId());
                feedbackRequestBO.setDeviceId(deviceDTO.getId());
                feedbackId = feedbackRequestHandler.addFeedback(feedbackRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(feedbackId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Inactive or invalid device.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "feedback creation failed.");
        } finally {
            return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(feedbackId));
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getfeedbackList(FeedbackListRequest feedbackListRequest, @HeaderParam("Auth") String auth) {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        FeedbackListRequestBO feedbackListRequestBO = new FeedbackListRequestBO();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                feedbackListRequestBO.setFromDate(feedbackListRequest.getFromDate());
                feedbackListRequestBO.setToDate(feedbackListRequest.getToDate());
                feedbackListRequestBO.setOutletId(feedbackListRequest.getOutletId());
                feedbackListRequestBO.setTableNo(feedbackListRequest.getTableNo());
                feedbackListRequestBO.setUserId(feedbackListRequest.getUserId());
                FeedbackResponseList feedbackResponse = new FeedbackResponseList();
                int clientId = UserRequestValidation.getClient(auth);
                feedbackResponse.setFeedbacks(feedbackRequestHandler.getfeedbackList1(feedbackListRequestBO,clientId));
                return ResponseGenerator.generateSuccessResponse(feedbackResponse, "Successfully retrieved.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve the list");
        }

    }

    @GET
    @Path("/feedbackDetail/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getfeedbackById(@PathParam("id") int id) {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            FeedbackByIdResponse feedbackByIdResponse = feedbackRequestHandler.getfeedbackById(id);
            return ResponseGenerator.generateSuccessResponse(feedbackByIdResponse, "SUCCESS");
        } catch (FeedbackNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID feedback id. ");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve. ");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createfeedbackTracking")
    public Response createFeedbackTracking(FeedbackTrackingRequest feedbackTrackingRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                FeedbackTrackingRequestBO feedbackTrackingRequestBO = new FeedbackTrackingRequestBO();
                feedbackTrackingRequestBO.setFeedbackId(feedbackTrackingRequest.getFeedbackId());
                MessageResponse createUserResponse = new MessageResponse();
                FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
                int clientId = UserRequestValidation.getClient(auth);
                Boolean isCreate = feedbackRequestHandler.createFeedbackTracking(feedbackTrackingRequestBO,clientId);
                if (isCreate) {
                    return ResponseGenerator.generateSuccessResponse(createUserResponse, "Negative feedback url tracked.");
                } else {
                    return ResponseGenerator.generateFailureResponse(createUserResponse, "Feedback tracking creation Failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse createUserResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(createUserResponse, "Feedback tracking creation Failed");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/negativeReport")

    public Response getNegativeReport(FeedbackListRequest feedbackListRequest, @QueryParam("isNegative") int isNegative, @HeaderParam("Auth") String auth) {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        FeedbackTrackingResponseList feedbackTrackingResponseList = new FeedbackTrackingResponseList();
        FeedbackListRequestBO feedbackListRequestBO = new FeedbackListRequestBO();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                feedbackListRequestBO.setFromDate(feedbackListRequest.getFromDate());
                feedbackListRequestBO.setToDate(feedbackListRequest.getToDate());
                feedbackListRequestBO.setOutletId(feedbackListRequest.getOutletId());
                feedbackListRequestBO.setTableNo(feedbackListRequest.getTableNo());
                feedbackListRequestBO.setUserId(feedbackListRequest.getUserId());
                int clientId = UserRequestValidation.getClient(auth);
                feedbackTrackingResponseList.setFeedbackTrackingDetails(feedbackRequestHandler.getFeedbackTrackingList(feedbackListRequestBO, isNegative, clientId));
                return ResponseGenerator.generateSuccessResponse(feedbackTrackingResponseList, "List of Negative feedbacks.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse();
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve the feedbacks.");
        }
    }

    @GET
    @Path("/dailyReport")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getDailyReport() {
        FeedbackRequestHandler feedbackRequestHandler = new FeedbackRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
                Boolean isProcessed = feedbackRequestHandler.getDailyReport();
                if (isProcessed) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Mails are sent to all users.");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to send the mails. ");
                }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve. ");
        }
    }
}

