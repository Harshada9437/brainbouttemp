package com.brainbout.service;

import com.brainbout.requesthandler.UserRequestHandler;
import com.brainbout.response.Answer.UserResponse;
import com.brainbout.response.util.MessageResponse;
import com.brainbout.response.util.ResponseGenerator;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{company_id}")
    public Response getUserList(@PathParam("company_id") int companyId, @QueryParam("location") int loc) {
        try {
                UserRequestHandler userRequestHandler = new UserRequestHandler();
                UserResponse userResponse = new UserResponse();
                userResponse.setUsers(userRequestHandler.getUserList(companyId, loc));
                return ResponseGenerator.generateSuccessResponse(userResponse, "Answers are available");
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "No answers are assigned. ");
        }

    }
}