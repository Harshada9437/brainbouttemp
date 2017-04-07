package com.heednow.service;

import com.heednow.bo.GroupRequestBO;
import com.heednow.bo.UpdateGroupBO;
import com.heednow.request.group.GroupRequest;
import com.heednow.request.group.UpdateGroupRequest;
import com.heednow.requesthandler.GroupRequestHandler;
import com.heednow.response.group.GetGroupResponse;
import com.heednow.response.group.GroupResponse;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 4/4/2017.
 */
@Path("/group")
public class GroupService {
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getGroupList(@HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                GroupRequestHandler groupRequestHandler = new GroupRequestHandler();
                GroupResponse groupResponse = new GroupResponse();
                int clientId = UserRequestValidation.getClient(auth);
                groupResponse.setGroups(groupRequestHandler.getGroups(clientId));
                return ResponseGenerator.generateSuccessResponse(groupResponse, "groups are available");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve the list. ");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/groupInfo/{id}")
    public Response getGroup(@PathParam("id") int id, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                GroupRequestHandler groupRequestHandler = new GroupRequestHandler();
                GetGroupResponse group = groupRequestHandler.getGroup(id);
                return ResponseGenerator.generateSuccessResponse(group, "Group details.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve group details. ");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addGroup(GroupRequest groupRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                GroupRequestBO groupRequesttBO = new GroupRequestBO();
                groupRequesttBO.setShortDesc(groupRequest.getShortDesc());
                groupRequesttBO.setDesc(groupRequest.getDesc());
                GroupRequestHandler groupRequestHandler = new GroupRequestHandler();
                int clientId = UserRequestValidation.getClient(auth);
                int groupId = groupRequestHandler.addGroup(groupRequesttBO,clientId);
                if (groupId > 0) {

                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(groupId));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "group creation failed.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "group creation failed.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateGroup(UpdateGroupRequest groupRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateGroupBO groupRequesttBO = new UpdateGroupBO(groupRequest.getId(),
                        groupRequest.getDesc(),
                        groupRequest.getShortDesc());
                GroupRequestHandler groupRequestHandler = new GroupRequestHandler();
                Boolean isProcessed = groupRequestHandler.updateGroup(groupRequesttBO);
                if (isProcessed) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Group updated successfully.");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Group updation failed.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Group updation failed.");
        }
    }
}
