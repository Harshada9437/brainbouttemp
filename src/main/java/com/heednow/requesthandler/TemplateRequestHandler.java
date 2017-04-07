package com.heednow.requesthandler;

import com.heednow.bo.UpdateAssignQuestionRequestBO;
import com.heednow.dao.template.QueTempDAO;
import com.heednow.dao.template.TemplateDAO;
import com.heednow.dto.request.QueTempDTO;
import com.heednow.dto.request.TempDTO;
import com.heednow.dto.request.TemplateDTO;
import com.heednow.dto.request.UpdateAssignQuestionDTO;
import com.heednow.exceptions.TemplateNotFoundException;
import com.heednow.bo.AssignQuestionRequestBO;
import com.heednow.bo.TemplateRequestBO;
import com.heednow.bo.UpdateTemplateRequestBO;
import com.heednow.response.template.GetTemplateResponse;
import com.heednow.response.template.QueResponse;
import com.heednow.response.template.TemplateResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateRequestHandler {
    public Integer createTemplate(TemplateRequestBO templateRequestBO,int clientId) throws SQLException {
        TemplateDAO templateDAO = new TemplateDAO();
        int id = templateDAO.createTemplate(buildTemplateDTOFromBO(templateRequestBO),clientId);
        return id;
    }

    private TemplateDTO buildTemplateDTOFromBO(TemplateRequestBO templateRequestBO) {
        TemplateDTO templateDTO = new TemplateDTO();
        templateDTO.setTemplateDesc(templateRequestBO.getTemplateDesc());
        return templateDTO;
    }

    public Boolean assignQuestion(AssignQuestionRequestBO assignQuestionRequestBO, int templateId) throws SQLException, TemplateNotFoundException {
        QueTempDAO queTempDAO = new QueTempDAO();
        Boolean isCreated = queTempDAO.assignQuestion(buildDTOFromBO(assignQuestionRequestBO), templateId);
        return isCreated;
    }

    private QueTempDTO buildDTOFromBO(AssignQuestionRequestBO assignQuestionRequestBO) {
        QueTempDTO queTempDTO = new QueTempDTO();

        queTempDTO.setQueId(assignQuestionRequestBO.getQuestionId());
        queTempDTO.setPriority(assignQuestionRequestBO.getPriority());

        return queTempDTO;
    }

    public List<QueResponse> getAssignedQuestions(int templateId,int clientId) throws SQLException {
        QueTempDAO queTempDAO = new QueTempDAO();
        List<QueResponse> queList = getTempQueResponseListFromDTOs(queTempDAO.getAssignedQuestions(templateId),clientId);
        return queList;
    }

    public List<QueResponse> getAssignedQuestions1(int templateId) throws SQLException {
        QueTempDAO queTempDAO = new QueTempDAO();
        List<QueResponse> queList = getTempQueResponseListFromDTOs1(queTempDAO.getAssignedQuestions(templateId));
        return queList;
    }

    private List<QueResponse> getTempQueResponseListFromDTOs(List<QueTempDTO> QueTempDTOs,int clientId) throws SQLException {
        List<QueResponse> queResponses = new ArrayList<QueResponse>();
        Iterator<QueTempDTO> queTempDTOIterator = QueTempDTOs.iterator();
        while (queTempDTOIterator.hasNext()) {
            QueTempDTO queTempDTO = queTempDTOIterator.next();
            QueResponse queResponse = new QueResponse();
            queResponse.setId(queTempDTO.getQueId());
            queResponse.setAnswerSymbol(queTempDTO.getAnswerSymbol());
            queResponse.setParentAnswerId(queTempDTO.getParentAnswerId());
            queResponse.setParentQuestionId(queTempDTO.getParentQuestionId());
            queResponse.setQuestionDesc(queTempDTO.getQuestionDesc());
            queResponse.setParentQuestionDesc(queTempDTO.getParentQuestionDesc());
            queResponse.setParentAnswerDesc(queTempDTO.getParentAnswerDesc());
            queResponse.setQuestionType(queTempDTO.getQuestionType());
            queResponse.setOptions(QuestionRequestHandler.getAnswer(queTempDTO.getQueId(),clientId));
            queResponse.setPriority(queTempDTO.getPriority());
            queResponses.add(queResponse);
        }
        return queResponses;
    }

    private List<QueResponse> getTempQueResponseListFromDTOs1(List<QueTempDTO> QueTempDTOs) throws SQLException {
        List<QueResponse> queResponses = new ArrayList<QueResponse>();
        Iterator<QueTempDTO> queTempDTOIterator = QueTempDTOs.iterator();
        while (queTempDTOIterator.hasNext()) {
            QueTempDTO queTempDTO = queTempDTOIterator.next();
            QueResponse queResponse = new QueResponse();
            queResponse.setId(queTempDTO.getQueId());
            queResponse.setAnswerSymbol(queTempDTO.getAnswerSymbol());
            queResponse.setParentAnswerId(queTempDTO.getParentAnswerId());
            queResponse.setParentQuestionId(queTempDTO.getParentQuestionId());
            queResponse.setQuestionDesc(queTempDTO.getQuestionDesc());
            queResponse.setParentQuestionDesc(queTempDTO.getParentQuestionDesc());
            queResponse.setParentAnswerDesc(queTempDTO.getParentAnswerDesc());
            queResponse.setQuestionType(queTempDTO.getQuestionType());
            queResponse.setOptions(QuestionRequestHandler.getAnswer1(queTempDTO.getQueId()));
            queResponse.setPriority(queTempDTO.getPriority());
            queResponses.add(queResponse);
        }
        return queResponses;
    }


    public void removeQuestionDetails(int templateId, int queId) throws SQLException, TemplateNotFoundException {
        QueTempDAO tempDAO = new QueTempDAO();
        tempDAO.removeQuestionDetails(templateId, queId);
    }

    public boolean updateTemplate(UpdateTemplateRequestBO updateTemplateRequestBO) throws SQLException, TemplateNotFoundException {
        TemplateDAO templateDAO = new TemplateDAO();
        Boolean isProcessed = templateDAO.updateTemplate(buildUpdateBOFromDTO(updateTemplateRequestBO));
        return isProcessed;
    }

    private TemplateDTO buildUpdateBOFromDTO(UpdateTemplateRequestBO updateTemplateRequestBO) {
        TemplateDTO templateDTO = new TemplateDTO();
        templateDTO.setId(updateTemplateRequestBO.getId());
        templateDTO.setTemplateDesc(updateTemplateRequestBO.getTemplateDesc());
        templateDTO.setStatus(updateTemplateRequestBO.getStatus());
        return templateDTO;
    }

    public boolean updateAssignQuestion(UpdateAssignQuestionRequestBO updateAssignQuestionRequestBO) throws SQLException {

        QueTempDAO queTempDAO = new QueTempDAO();
        Boolean isProcessed = queTempDAO.updateAssignQuestion(buildUpdateBOFromDTO(updateAssignQuestionRequestBO));
        return isProcessed;
    }

    private UpdateAssignQuestionDTO buildUpdateBOFromDTO(UpdateAssignQuestionRequestBO updateAssignQuestionRequestBO) {
        UpdateAssignQuestionDTO updateAssignQuestionDTO = new UpdateAssignQuestionDTO();
        updateAssignQuestionDTO.setTemplateId(updateAssignQuestionRequestBO.getTemplateId());
        updateAssignQuestionDTO.setPriority(updateAssignQuestionRequestBO.getPriority());
        updateAssignQuestionDTO.setQuestionId(updateAssignQuestionRequestBO.getQuestionId());
        return updateAssignQuestionDTO;
    }


    public List<TemplateResponseList> getTemplate(int clientId) throws SQLException {
        TemplateDAO templateDAO = new TemplateDAO();
        List<TemplateResponseList> templateResponseLists = getTemplateListDTOsFromBO(templateDAO.getTemplate(clientId));
        return templateResponseLists;
    }

    public List<TemplateResponseList> getTemplateListDTOsFromBO(List<TemplateDTO> templateDTOs) throws SQLException {
        List<TemplateResponseList> templateResponseList = new ArrayList<TemplateResponseList>();
        Iterator<TemplateDTO> templateDTOIterator = templateDTOs.iterator();
        while (templateDTOIterator.hasNext()) {
            TemplateDTO templateDTO = templateDTOIterator.next();
            TemplateResponseList templateResponseList1 = new TemplateResponseList(templateDTO.getId(), templateDTO.getOutlets(),
                    templateDTO.getTemplateDesc(),
                    templateDTO.getStatus());
            templateResponseList.add(templateResponseList1);
        }
        return templateResponseList;
    }

    public GetTemplateResponse getTemplateById(int templateId, int outletId) throws SQLException, TemplateNotFoundException {
        TemplateDAO templateDAO = new TemplateDAO();
        GetTemplateResponse response = buildResponseFromDTO(templateDAO.getTemplateInfo(templateId, outletId));
        return response;
    }

    private GetTemplateResponse buildResponseFromDTO(TempDTO tempDTO) {
        GetTemplateResponse response = new GetTemplateResponse(tempDTO.getTemplateId(),
                tempDTO.getDesc(),
                tempDTO.getStatus(),
                tempDTO.getFromDate(),
                tempDTO.getToDate(),
                tempDTO.getOutletId());
        return response;
    }
}