package com.heednow.service;

/**
 * Created by System-3 on 2/13/2017.
 */

import com.heednow.exceptions.CustomerNotFoundException;

import com.heednow.exceptions.QuestionNotFoundException;
import com.heednow.requesthandler.ReportRequestHandler;
import com.heednow.response.report.AverageResponseList;
import com.heednow.response.report.CountResponseList;
import com.heednow.response.report.CustomerReportResponseList;
import com.heednow.response.report.SummaryResponse;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/report")
public class ReportService {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionRating/count/{id}")
    public Response getcountById(@PathParam("id") int id, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
                CountResponseList countResponseList = new CountResponseList();
                int clientId = UserRequestValidation.getClient(auth);
                countResponseList.setCount(reportRequestHandler.getcountById(id,clientId));
                return ResponseGenerator.generateSuccessResponse(countResponseList, "List of counts.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid Question Id ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failure.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionRating/average/{id}")

    public Response getaverageById(@PathParam("id") int id, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
                AverageResponseList averageResponseList = new AverageResponseList();
                int clientId = UserRequestValidation.getClient(auth);
                averageResponseList.setAverage(reportRequestHandler.getaverageById(id,clientId));
                return ResponseGenerator.generateSuccessResponse(averageResponseList, "List of average rating.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID Question Id ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failure.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/customer/{phoneNo}")

    public Response getcustomerByPhoneNo(@PathParam("phoneNo") String phoneNo, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
                int clientId = UserRequestValidation.getClient(auth);
                CustomerReportResponseList customerReportResponseList = reportRequestHandler.getcustomerByPhoneNo(phoneNo,clientId);
                return ResponseGenerator.generateSuccessResponse(customerReportResponseList, "Personal Details.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (CustomerNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID Phone No. ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failure.");
        }
    }

    @GET
    @Path("/dailyReport/{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response getDailyReport(@PathParam("user_id") int userId, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ReportRequestHandler reportRequestHandler = new ReportRequestHandler();
                int clientId = UserRequestValidation.getClient(auth);
                SummaryResponse isProcessed = reportRequestHandler.getReport(userId,clientId);
                return ResponseGenerator.generateSuccessResponse(isProcessed, "Outlet's feedback summery.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve. ");
        }
    }
}





