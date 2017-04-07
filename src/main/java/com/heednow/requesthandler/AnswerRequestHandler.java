package com.heednow.requesthandler;

import com.heednow.dao.answer.AnswerDAO;
import com.heednow.dto.request.AnswerDTO;
import com.heednow.response.Answer.AnswerResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 12/26/2016.
 */
public class AnswerRequestHandler {
    public List<AnswerResponseList> getAnswer(int questionId,int clientId) throws SQLException {
        AnswerDAO answerDAO = new AnswerDAO();
        List<AnswerResponseList> answerResponseLists = getAnswerListDTOFromBO(answerDAO.getAnswer(questionId,clientId));
        return answerResponseLists;
    }

    public List<AnswerResponseList> getAnswerListDTOFromBO(List<AnswerDTO> answerDTOs) throws SQLException {
        List<AnswerResponseList> answerResponseLists = new ArrayList<AnswerResponseList>();
        Iterator<AnswerDTO> answerDTOIterator = answerDTOs.iterator();
        while (answerDTOIterator.hasNext()) {
            AnswerDTO answerDTO = answerDTOIterator.next();
            AnswerResponseList answerResponseList = new AnswerResponseList(answerDTO.getAnswerText(),
                    answerDTO.getRating(), answerDTO.getId(),answerDTO.getWeightage(),answerDTO.getThreshold());
            answerResponseLists.add(answerResponseList);
        }
        return answerResponseLists;
    }
}

