package com.heednow.service;

import com.heednow.exceptions.QuestionNotFoundException;
import com.heednow.bo.UpdateQueRequestBO;
import com.heednow.request.question.QuestionRequest;
import com.heednow.bo.QuestionRequestBO;
import com.heednow.request.question.UpdateQueRequest;
import com.heednow.requesthandler.QuestionRequestHandler;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.question.GetQuestionResponse;
import com.heednow.response.question.QuestionResponseList;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/question")
public class QuestionService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addQuestion(QuestionRequest questionRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                System.out.println(auth);
                QuestionRequestBO questionRequestBO = new QuestionRequestBO();
                questionRequestBO.setQuestionDesc(questionRequest.getQuestionDesc());
                questionRequestBO.setQuestionType(questionRequest.getQuestionType());
                questionRequestBO.setParentQuestionId(questionRequest.getParentQuestionId());
                questionRequestBO.setParentAnswerId(questionRequest.getParentAnswerId());
                questionRequestBO.setAnswerSymbol(questionRequest.getAnswerSymbol());
                questionRequestBO.setAnswerOption(questionRequest.getAnswerOption());
                int clientId = UserRequestValidation.getClient(auth);
                questionRequestBO.setClientId(clientId);
                QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
                int questionId = questionRequestHandler.addQuestion(questionRequestBO);
                if (questionId > 0) {

                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(questionId));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Question creation failed.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Question creation failed.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getQuestionList(@HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
                QuestionResponseList questionResponseList = new QuestionResponseList();
                int clientId = UserRequestValidation.getClient(auth);
                questionResponseList.setQuestions(questionRequestHandler.getQuestionList(clientId));
                return ResponseGenerator.generateSuccessResponse(questionResponseList, "List of questions.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateQuestion(UpdateQueRequest updateQueRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateQueRequestBO updateQueRequestBO = new UpdateQueRequestBO();
                updateQueRequestBO.setId(updateQueRequest.getId());
                updateQueRequestBO.setQuestionDesc(updateQueRequest.getQuestionDesc());
                updateQueRequestBO.setQuestionType(updateQueRequest.getQuestionType());
                updateQueRequestBO.setParentQuestionId(updateQueRequest.getParentQuestionId());
                updateQueRequestBO.setParentAnswerId(updateQueRequest.getParentAnswerId());
                updateQueRequestBO.setAnswerSymbol(updateQueRequest.getAnswerSymbol());
                updateQueRequestBO.setAnswerOption(updateQueRequest.getAnswerOption());
                int clientId = UserRequestValidation.getClient(auth);
                QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
                if (questionRequestHandler.updateQuestion(updateQueRequestBO,clientId)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Question updated successfully");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the question.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (QuestionNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid question id");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to update.");
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/questionInfo/{id}")
    public Response getQuestionList(@PathParam("id") int id, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        {
            try {
                if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                    QuestionRequestHandler questionRequestHandler = new QuestionRequestHandler();
                    int clientId = UserRequestValidation.getClient(auth);
                    GetQuestionResponse questionResponse = questionRequestHandler.getQuestionById(id,clientId);
                    return ResponseGenerator.generateSuccessResponse(questionResponse, "SUCCESS");
                } else {
                    return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
                }
            } catch (QuestionNotFoundException e) {
                return ResponseGenerator.generateFailureResponse(messageResponse, "INVALID QuestionId ");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve question details. ");
            }
        }
    }
}
