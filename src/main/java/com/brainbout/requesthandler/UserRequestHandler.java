package com.brainbout.requesthandler;

import com.brainbout.dao.*;
import com.brainbout.dto.*;
import com.brainbout.response.Answer.UserResponseList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 12/26/2016.
 */
public class UserRequestHandler {
    public List<UserResponseList> getUserList(int company, int loc) throws Exception {
        UserDAO answerDAO = new UserDAO();
        List<UserResponseList> userResponseLists = getAnswerListDTOFromBO(answerDAO.getUserList(company, loc));
        return userResponseLists;
    }

    public List<UserResponseList> getAnswerListDTOFromBO(List<UserDTO> answerDTOs) throws SQLException {
        List<UserResponseList> userResponseLists = new ArrayList<UserResponseList>();
        Iterator<UserDTO> answerDTOIterator = answerDTOs.iterator();
        while (answerDTOIterator.hasNext()) {
            UserDTO answerDTO = answerDTOIterator.next();
            UserResponseList userResponseList = new UserResponseList(answerDTO.getUserName(),
                    answerDTO.getEmail(),
                    answerDTO.getMobile(),
                    answerDTO.getCompany(),
                    answerDTO.getLocation(),
                    answerDTO.getIsParticipated(),
                    answerDTO.getScore());
            userResponseLists.add(userResponseList);
        }
        return userResponseLists;
    }
}

