package com.heednow.service;

import com.heednow.bo.*;
import com.heednow.dao.template.TemplateDAO;
import com.heednow.dto.request.TempDTO;
import com.heednow.exceptions.OutletNotFoundException;
import com.heednow.exceptions.UserNotFoundException;
import com.heednow.request.outlet.*;
import com.heednow.requesthandler.OutletRequestHandler;
import com.heednow.response.outlet.OutletInfo;
import com.heednow.response.outlet.OutletResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.outlet.OutletResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.Base64Encoder;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
@Path("/outlet")
public class OutLetService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignTemplate/{outlet_id}")
    public Response assignTemplate(AssignTemplateRequest assignTemplateRequest, @PathParam("outlet_id") int outletId, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                AssignTemplateRequestBO assignTemplateRequestBO = new AssignTemplateRequestBO();
                assignTemplateRequestBO.setTemplateId(assignTemplateRequest.getTemplateId());
                assignTemplateRequestBO.setFromDate(assignTemplateRequest.getFromDate());
                assignTemplateRequestBO.setToDate(assignTemplateRequest.getToDate());
                OutletRequestHandler outletRequestHandler = new OutletRequestHandler();

                List<TempDTO> tempDTOs = TemplateDAO.getTemplateByOutletId(outletId);
                if (tempDTOs.size() == 0) {
                    outletRequestHandler.assignTemplate(assignTemplateRequestBO, outletId);
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Templates are assigned");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Template is already assigned to this outlet.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Template assignment Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSettings/{outlet_id}")
    public Response updateSettings(UpdateSettingsRequest updateSettingsRequest, @PathParam("outlet_id") int outletId, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateSettingsRequestBO updateSettingsRequestBO = new UpdateSettingsRequestBO();
                updateSettingsRequestBO.setMobileNoLength(updateSettingsRequest.getMobileNoLength());
                updateSettingsRequestBO.setBannerUrl(updateSettingsRequest.getBannerUrl());
                updateSettingsRequestBO.setTableNoRange(updateSettingsRequest.getTableNoRange());
                updateSettingsRequestBO.setPocName(updateSettingsRequest.getPocName());
                updateSettingsRequestBO.setPocMobile(updateSettingsRequest.getPocMobile());
                updateSettingsRequestBO.setPocEmail(updateSettingsRequest.getPocEmail());
                updateSettingsRequestBO.setMgrEmail(updateSettingsRequest.getMgrEmail());
                updateSettingsRequestBO.setMgrMobile(updateSettingsRequest.getMgrMobile());
                updateSettingsRequestBO.setMgrName(updateSettingsRequest.getMgrName());
                updateSettingsRequestBO.setSmsGatewayId(updateSettingsRequest.getSmsGatewayId());

                OutletRequestHandler outletRequestHandler = new OutletRequestHandler();

                if (outletRequestHandler.updateSettings(updateSettingsRequestBO, outletId)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Outlet settings are updated");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "update setting Failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "update setting Failed");
        }


    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getOutletList(OutletListRequest outletListRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            System.out.println(auth);
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                System.out.println(auth);
                OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
                OutletResponse outletResponse = new OutletResponse();
                OutletListRequestBO outletListRequestBO = new OutletListRequestBO();
                outletListRequestBO.setOutletId(outletListRequest.getOutletId());
                outletListRequestBO.setUserId(outletListRequest.getUserId());
                int clientId = UserRequestValidation.getClient(auth);
                outletResponse.setOutletResponseList(outletRequestHandler.getOutlate(outletListRequestBO, clientId));
                return ResponseGenerator.generateSuccessResponse(outletResponse, "outlet are available");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (UserNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "invalid user id");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "invalid user id");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/outletInfoById/{outlet_id}")
    public Response getOutletById(@PathParam("outlet_id") int outletId, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
                OutletInfo outletResponse = outletRequestHandler.getOutletById(outletId);
                return ResponseGenerator.generateSuccessResponse(outletResponse, "Outlet Information");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (OutletNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid outlet id ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/outletInfo/{token}/{installation_id}")
    public Response getOutletByStoreId(@PathParam("token") String token,@PathParam("installation_id") String installationId) {
        OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            List<String> decode = Base64Encoder.decode(token);
            if(decode.get(0).equals(installationId)) {
                OutletResponseList outletResponse = outletRequestHandler.getOutletByStoreId(token);
                return ResponseGenerator.generateSuccessResponse(outletResponse, "Outlet Information");
            }else{
                return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid device. ");
            }
        } catch (OutletNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid store id ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createOutlet(OutletRequest outletRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                OutletRequestBO outletRequestBO = new OutletRequestBO();
                outletRequestBO.setOutletDesc(outletRequest.getOutletDesc());
                outletRequestBO.setShortDesc(outletRequest.getShortDesc());
                outletRequestBO.setClusterId(outletRequest.getClusterId());
                outletRequestBO.setRegionId(outletRequest.getRegionId());
                outletRequestBO.setCompanyId(outletRequest.getCompanyId());
                outletRequestBO.setGroupId(outletRequest.getGroupId());
                outletRequestBO.setPosStoreId(outletRequest.getPosStoreId());
                outletRequestBO.setClientId(UserRequestValidation.getClient(auth));

                MessageResponse messageResponse = new MessageResponse();
                OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
                int id = outletRequestHandler.createOutlet(outletRequestBO, outletRequestBO.getClientId());
                if (id > 0) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Outlet creation failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Outlet creation failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateOutlet(UpdateOutletRequest outletRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateOutletRequestBO outletRequestBO = new UpdateOutletRequestBO();
                outletRequestBO.setId(outletRequest.getId());
                outletRequestBO.setOutletDesc(outletRequest.getOutletDesc());
                outletRequestBO.setShortDesc(outletRequest.getShortDesc());
                outletRequestBO.setClusterId(outletRequest.getClusterId());
                outletRequestBO.setRegionId(outletRequest.getRegionId());
                outletRequestBO.setCompanyId(outletRequest.getCompanyId());
                outletRequestBO.setGroupId(outletRequest.getGroupId());
                outletRequestBO.setPosStoreId(outletRequest.getPosStoreId());

                MessageResponse messageResponse = new MessageResponse();
                OutletRequestHandler outletRequestHandler = new OutletRequestHandler();
                boolean id = outletRequestHandler.updateOutlet(outletRequestBO);
                if (id) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Region creation failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Region creation failed");
        }
    }

}


