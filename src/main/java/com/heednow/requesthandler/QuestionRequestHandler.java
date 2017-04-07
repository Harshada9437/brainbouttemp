package com.heednow.requesthandler;

import com.heednow.dao.answer.AnswerDAO;
import com.heednow.dao.question.QuestionDAO;
import com.heednow.dto.request.AnswerDTO;
import com.heednow.dto.request.QuestionRequestDTO;
import com.heednow.exceptions.QuestionNotFoundException;
import com.heednow.bo.QuestionRequestBO;
import com.heednow.bo.UpdateQueRequestBO;
import com.heednow.request.question.OptionsList;
import com.heednow.request.question.UpdateOptionsList;
import com.heednow.response.Answer.AnswerResponseList;
import com.heednow.response.question.GetQuestionResponse;
import com.heednow.response.question.QuestionResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionRequestHandler {
    public Integer addQuestion(QuestionRequestBO questionRequestBO) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
        int id = questionDAO.addQuestion(buildRequestDTOFromBO(questionRequestBO),questionRequestBO.getClientId());
        List<Integer> ansIds = getAssignAnswer(id, questionRequestBO.getAnswerOption(),questionRequestBO.getClientId());

        return id;
    }

    private QuestionRequestDTO buildRequestDTOFromBO(QuestionRequestBO questionRequestBO) {
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();

        questionRequestDTO.setQuestionDesc(questionRequestBO.getQuestionDesc());
        questionRequestDTO.setQuestionType(questionRequestBO.getQuestionType());
        questionRequestDTO.setParentQuestionId(questionRequestBO.getParentQuestionId());
        questionRequestDTO.setParentAnswerId(questionRequestBO.getParentAnswerId());
        questionRequestDTO.setAnswerSymbol(questionRequestBO.getAnswerSymbol());


        return questionRequestDTO;
    }


    public List<QuestionResponse> getQuestionList(int clientId) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO();
        List<QuestionResponse> questionList = new ArrayList<QuestionResponse>();

        List<QuestionRequestDTO> questionRequestDTOList = questionDAO.getAllQuestions(clientId);

        for (QuestionRequestDTO questionRequestDTO : questionRequestDTOList) {
            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setId(questionRequestDTO.getId());
            questionResponse.setQuestionDesc(questionRequestDTO.getQuestionDesc());
            questionResponse.setParentAnswerDesc(questionRequestDTO.getParentAnswerDesc());
            questionResponse.setParentQuestionDesc(questionRequestDTO.getParentQuestionDesc());
            questionResponse.setQuestionType(questionRequestDTO.getQuestionType());
            questionResponse.setParentQuestionId(questionRequestDTO.getParentQuestionId());
            questionResponse.setParentAnswerId(questionRequestDTO.getParentAnswerId());
            questionResponse.setAnswerSymbol(questionRequestDTO.getAnswerSymbol());
            questionList.add(questionResponse);
        }

        return questionList;
    }

    public boolean updateQuestion(UpdateQueRequestBO updateQueRequestBO,int clientId) throws SQLException, QuestionNotFoundException {

        QuestionDAO questionDAO = new QuestionDAO();

        Boolean isProcessed = questionDAO.updateQuestion(buildDTOFromBO(updateQueRequestBO));
        updateAnswer(updateQueRequestBO.getAnswerOption(), updateQueRequestBO.getId(),clientId);
        return isProcessed;
    }

    private Boolean updateAnswer(List<UpdateOptionsList> answerOption, int queId, int clientId) throws SQLException {
        Boolean isCreated = Boolean.FALSE;
        List<AnswerResponseList> savedList = getAnswer(queId,clientId);
        AnswerDAO answerDAO = new AnswerDAO();

        for (int i = 0; i < answerOption.size(); i++) {
            UpdateOptionsList optionItem = answerOption.get(i);
            if (!isAnswerInDB(optionItem.getAnswer_id(), savedList)) {
                answerDAO.createAnswer(queId, optionItem.getAnswerDesc(), optionItem.getRating(), optionItem.getWeightage(), optionItem.getThreshold(),1);
            } else {
                answerDAO.updateAnswer(optionItem.getAnswer_id(), optionItem.getAnswerDesc(), optionItem.getRating(), optionItem.getWeightage(), optionItem.getThreshold());
            }
            isCreated = true;
        }

        //DELETE AFTER PROCESSING
        for (int j = 0; j < savedList.size(); j++) {
            AnswerResponseList savedItem = savedList.get(j);
            if (!isAnswerInPayload(savedItem.getAnswer_id(), answerOption)) {
                answerDAO.deleteAnswer(savedItem.getAnswer_id());
            }
        }

        return isCreated;
    }

    private boolean isAnswerInDB(int ansId, List<AnswerResponseList> ansList) {
        for (int i = 0; i < ansList.size(); i++) {
            if (ansList.get(i).getAnswer_id() == ansId) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnswerInPayload(int ansId, List<UpdateOptionsList> ansList) {
        for (int i = 0; i < ansList.size(); i++) {
            if (ansList.get(i).getAnswer_id() == ansId) {
                return true;
            }
        }
        return false;
    }

    private QuestionRequestDTO buildDTOFromBO(UpdateQueRequestBO updateQueRequestBO) {
        QuestionRequestDTO questionRequestDTO = new QuestionRequestDTO();
        questionRequestDTO.setId(updateQueRequestBO.getId());
        questionRequestDTO.setQuestionDesc(updateQueRequestBO.getQuestionDesc());
        questionRequestDTO.setQuestionType(updateQueRequestBO.getQuestionType());
        questionRequestDTO.setParentQuestionId(updateQueRequestBO.getParentQuestionId());
        questionRequestDTO.setParentAnswerId(updateQueRequestBO.getParentAnswerId());
        questionRequestDTO.setAnswerSymbol(updateQueRequestBO.getAnswerSymbol());
        return questionRequestDTO;
    }

    public GetQuestionResponse getQuestionById(int id,int clientId) throws SQLException, QuestionNotFoundException {
        QuestionDAO questionDAO = new QuestionDAO();
        GetQuestionResponse getQuestionResponse = buildQuestionInfoDTOFromBO(questionDAO.getQuestionById(id),clientId);
        return getQuestionResponse;
    }

    public GetQuestionResponse buildQuestionInfoDTOFromBO(QuestionRequestDTO questionRequestDTO,int clientId) throws SQLException {
        GetQuestionResponse getQuestionResponse = new GetQuestionResponse();
        getQuestionResponse.setId(questionRequestDTO.getId());
        getQuestionResponse.setAnswerSymbol(questionRequestDTO.getAnswerSymbol());
        getQuestionResponse.setParentAnswerId(questionRequestDTO.getParentAnswerId());
        getQuestionResponse.setParentQuestionId(questionRequestDTO.getParentQuestionId());
        getQuestionResponse.setQuestionDesc(questionRequestDTO.getQuestionDesc());
        getQuestionResponse.setParentQuestionDesc(questionRequestDTO.getParentQuestionDesc());
        getQuestionResponse.setParentAnswerDesc(questionRequestDTO.getParentAnswerDesc());
        getQuestionResponse.setQuestionType(questionRequestDTO.getQuestionType());
        getQuestionResponse.setOptions(getAnswer(questionRequestDTO.getId(),clientId));

        return getQuestionResponse;

    }

    public List<Integer> getAssignAnswer(int id, List<OptionsList> answerOption,int clientId) throws SQLException {

        int ansId = 0;
        Iterator<OptionsList> asnwerListIterator = answerOption.iterator();
        List<Integer> ansIds = new ArrayList<Integer>();

        AnswerDAO answerDAO = new AnswerDAO();
        while (asnwerListIterator.hasNext()) {
            OptionsList optionsList = new OptionsList();
            optionsList = asnwerListIterator.next();

            ansId = answerDAO.createAnswer(id, optionsList.getLabel(), optionsList.getRating(), optionsList.getWeightage(), optionsList.getThreshold(),clientId);
            ansIds.add(ansId);
        }

        return ansIds;
    }

    public static List<AnswerResponseList> getAnswer(int questionId,int clientId) throws SQLException {
        AnswerDAO answerDAO = new AnswerDAO();
        return getAnswerListDTOFromBO(answerDAO.getAnswer(questionId,clientId));
    }

    public static List<AnswerResponseList> getAnswer1(int questionId) throws SQLException {
        AnswerDAO answerDAO = new AnswerDAO();
        return getAnswerListDTOFromBO(answerDAO.getAnswer1(questionId));
    }

    public static List<AnswerResponseList> getAnswerListDTOFromBO(List<AnswerDTO> answerDTOs) throws SQLException {
        List<AnswerResponseList> answerResponseLists = new ArrayList<AnswerResponseList>();
        for (AnswerDTO answerDTO : answerDTOs) {
            AnswerResponseList answerResponseList = new AnswerResponseList(answerDTO.getAnswerText(),
                    answerDTO.getRating(), answerDTO.getId(), answerDTO.getWeightage(), answerDTO.getThreshold());
            answerResponseLists.add(answerResponseList);
        }
        return answerResponseLists;
    }
}