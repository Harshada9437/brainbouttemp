package com.heednow.service;

import com.heednow.requesthandler.AnswerRequestHandler;
import com.heednow.response.Answer.AnswerResponse;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/answer")
public class AnswerService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{question_id}")
    public Response getAnswerList(@PathParam("question_id") int questionId, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                AnswerRequestHandler answerRequestHandler = new AnswerRequestHandler();
                AnswerResponse answerResponse = new AnswerResponse();
                int clientId = UserRequestValidation.getClient(auth);
                answerResponse.setAnswerResponseList(answerRequestHandler.getAnswer(questionId, clientId));
                return ResponseGenerator.generateSuccessResponse(answerResponse, "Answers are available");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "No answers are assigned. ");
        }

    }
}