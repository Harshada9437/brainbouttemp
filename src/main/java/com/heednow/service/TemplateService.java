package com.heednow.service;

import com.heednow.bo.UpdateAssignQuestionRequestBO;
import com.heednow.dao.template.QueTempDAO;
import com.heednow.dao.template.TemplateDAO;
import com.heednow.exceptions.TemplateNotFoundException;
import com.heednow.bo.AssignQuestionRequestBO;
import com.heednow.bo.TemplateRequestBO;
import com.heednow.bo.UpdateTemplateRequestBO;
import com.heednow.request.template.AssignQuestionRequest;
import com.heednow.request.template.TemplateRequest;
import com.heednow.request.template.UpdateAssignQuestionRequest;
import com.heednow.request.template.UpdateTemplateRequest;
import com.heednow.requesthandler.TemplateRequestHandler;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.template.*;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/template")
public class TemplateService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createTemplate(TemplateRequest templateRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                TemplateRequestBO templateRequestBO = new TemplateRequestBO();
                templateRequestBO.setTemplateDesc(templateRequest.getTemplateDesc());
                int clientId = UserRequestValidation.getClient(auth);
                if (!TemplateDAO.getTemplateByName(templateRequest.getTemplateDesc())) {
                    int templateId = templateRequestHandler.createTemplate(templateRequestBO,clientId);
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(templateId));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Template description already exist");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Template Creation Failed");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateTemplate(UpdateTemplateRequest updateTemplateRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateTemplateRequestBO updateTemplateRequestBO = new UpdateTemplateRequestBO();
                updateTemplateRequestBO.setId(updateTemplateRequest.getId());
                updateTemplateRequestBO.setTemplateDesc(updateTemplateRequest.getTemplateDesc());
                updateTemplateRequestBO.setStatus(updateTemplateRequest.getStatus());

                TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();

                if (templateRequestHandler.updateTemplate(updateTemplateRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Template updated successfully");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the template.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the template.");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignQuestion/{template_id}")
    public Response assignQuestion(AssignQuestionRequest assignQuestionRequest, @HeaderParam("Auth") String auth, @PathParam("template_id") int templateId) {
        MessageResponse tempQueResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                AssignQuestionRequestBO assignQuestionRequestBO = new AssignQuestionRequestBO();
                assignQuestionRequestBO.setQuestionId(assignQuestionRequest.getQuestionId());
                assignQuestionRequestBO.setPriority(assignQuestionRequest.getPriority());
                TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();

                if (!QueTempDAO.isAlreadyAssigned(assignQuestionRequest.getQuestionId(), templateId)) {
                    templateRequestHandler.assignQuestion(assignQuestionRequestBO, templateId);
                    return ResponseGenerator.generateSuccessResponse(tempQueResponse, "Questions are assigned");
                } else {
                    return ResponseGenerator.generateFailureResponse(tempQueResponse, "Questiuon is already assigned.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(tempQueResponse, "Assign question Failed");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/listQuestions/{template_id}")
    public Response getAssignedQuestions(@PathParam("template_id") int templateId, @HeaderParam("Auth") String auth) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        TempQueListResponse response = new TempQueListResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                int clientId = UserRequestValidation.getClient(auth);
                response.setQuestions(templateRequestHandler.getAssignedQuestions(templateId,clientId));
                return ResponseGenerator.generateSuccessResponse(response, "list of questions.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "failed to retrieve.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateteAssignQuestion")
    public Response getUpdatedAssignedQuestions(UpdateAssignQuestionRequest updateAssignQuestionRequest, @HeaderParam("Auth") String auth) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                UpdateAssignQuestionRequestBO updateAssignQuestionRequestBO = new UpdateAssignQuestionRequestBO();
                updateAssignQuestionRequestBO.setQuestionId(updateAssignQuestionRequest.getQuestionId());
                updateAssignQuestionRequestBO.setPriority(updateAssignQuestionRequest.getPriority());
                updateAssignQuestionRequestBO.setTemplateId(updateAssignQuestionRequest.getTemplateId());
                TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
                templateRequestHandler.updateAssignQuestion(updateAssignQuestionRequestBO);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Priority of question updated successfully");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update Priority of question");
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteAssignQuestion/{template_id}/{question_id}")
    public Response deleteAssignQuestion(@PathParam("template_id") int templateId, @PathParam("question_id") int queId, @HeaderParam("Auth") String auth) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                templateRequestHandler.removeQuestionDetails(templateId, queId);
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Question has been removed.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Please first remove the details assigned to question.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getTemplateList(@HeaderParam("Auth") String auth) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        TemplateResponse templateResponse = new TemplateResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                int clientId = UserRequestValidation.getClient(auth);
                templateResponse.setTemplateResponseList(templateRequestHandler.getTemplate(clientId));
                return ResponseGenerator.generateSuccessResponse(templateResponse, "Template are available");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse();
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/templateInfo/{template_id}/{outlet_id}")
    public Response getOutletByStoreId(@PathParam("template_id") int templateId, @PathParam("outlet_id") int outletId, @HeaderParam("Auth") String auth) {
        TemplateRequestHandler templateRequestHandler = new TemplateRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                GetTemplateResponse response = templateRequestHandler.getTemplateById(templateId, outletId);
                return ResponseGenerator.generateSuccessResponse(response, "Template Information");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (TemplateNotFoundException e) {
            return ResponseGenerator.generateFailureResponse(messageResponse, "Invalid template id ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Error in retrieving outlet details. ");
        }
    }
}
