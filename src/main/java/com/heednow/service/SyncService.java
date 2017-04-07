package com.heednow.service;

import com.heednow.bo.SettingRequestBO;
import com.heednow.bo.SmsSettingRequestBO;
import com.heednow.bo.UpdateSettingRequestBO;
import com.heednow.dao.Sync.SmsDAO;
import com.heednow.request.user.SettingRequest;
import com.heednow.request.user.SmsSettingRequest;
import com.heednow.request.user.UpdateSettingRequest;
import com.heednow.requesthandler.SyncRequestHandler;
import com.heednow.response.user.SettingResponse;
import com.heednow.response.user.SmsSettingResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by System-2 on 1/9/2017.
 */

@Path("/settings")
public class SyncService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response saveSetting(SettingRequest settingRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
                SettingRequestBO settingRequestBO = new SettingRequestBO();
                MessageResponse messageResponse = new MessageResponse();
                settingRequestBO.setSmsTemplate(settingRequest.getSmsTemplate());
                settingRequestBO.setArchiveTime(settingRequest.getArchiveTime());
                settingRequestBO.setReportTime(settingRequest.getReportTime());
                settingRequestBO.setClientId(UserRequestValidation.getClient(auth));
                settingRequestBO.setEmailTemplate(settingRequest.getEmailTemplate());
                settingRequestBO.setVersionCode(settingRequest.getVersionCode());
                settingRequestBO.setVersionName(settingRequest.getVersionName());
                if (syncRequestHandler.saveSetting(settingRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Saved successfully");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fetch")
    public Response fetchSettings(@HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
                SettingResponse settingResponse = syncRequestHandler.fetchSettings(UserRequestValidation.getClient(auth));
                return ResponseGenerator.generateSuccessResponse(settingResponse, "Message template");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveSmsSettings")
    public Response saveSmsSettings(SmsSettingRequest settingRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
                SmsSettingRequestBO settingRequestBO = new SmsSettingRequestBO();
                MessageResponse messageResponse = new MessageResponse();
                settingRequestBO.setApi(settingRequest.getApi());
                settingRequestBO.setSenderId(settingRequest.getSenderId());
                settingRequestBO.setCampaign(settingRequest.getCampaign());
                settingRequestBO.setCountryCode(settingRequest.getCountryCode());
                settingRequestBO.setName(settingRequest.getName());
                settingRequestBO.setClientId(UserRequestValidation.getClient(auth));
                if (!SmsDAO.getSettingsByName(settingRequestBO.getName())) {
                    int id = syncRequestHandler.saveSmsSettings(settingRequestBO);
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Name already exist.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateSmsSettings")
    public Response updateSmsSettings(UpdateSettingRequest settingRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
                UpdateSettingRequestBO settingRequestBO = new UpdateSettingRequestBO();
                MessageResponse messageResponse = new MessageResponse();
                settingRequestBO.setApi(settingRequest.getApi());
                settingRequestBO.setId(settingRequest.getId());
                settingRequestBO.setSenderId(settingRequest.getSenderId());
                settingRequestBO.setCampaign(settingRequest.getCampaign());
                settingRequestBO.setCountryCode(settingRequest.getCountryCode());
                settingRequestBO.setName(settingRequest.getName());
                if (!SmsDAO.getSettingsByUpdateName(settingRequestBO.getName(), settingRequestBO.getId())) {
                    syncRequestHandler.updateSmsSettings(settingRequestBO);
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "settings are updated.");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Name already exist.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in saving settings.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fetchSmsSettings")
    public Response fetchSmsSettings(@HeaderParam("Auth") String auth) throws Exception {
        try {
            if (auth != null && (UserRequestValidation.isRequestValid(auth))) {
                SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
                SmsSettingResponseList settingResponse = new SmsSettingResponseList();
                settingResponse.setSmsSettings(syncRequestHandler.fetchSmsSettings(UserRequestValidation.getClient(auth)));
                return ResponseGenerator.generateSuccessResponse(settingResponse, "Message settings");
            }
            else{
                MessageResponse messageResponse = new MessageResponse();
                return ResponseGenerator.generateFailureResponse(messageResponse, "Name already exist.");
            }
        } catch(Exception e){
                e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
                return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving details.");
            }

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createArchive")
    public Response archiveFeedback() {
        SyncRequestHandler syncRequestHandler = new SyncRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            syncRequestHandler.archiveFeedback();
            return ResponseGenerator.generateSuccessResponse(messageResponse, "Data is archived.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in creation.");
        }
    }
}
