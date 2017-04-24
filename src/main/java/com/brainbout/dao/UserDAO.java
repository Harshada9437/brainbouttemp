package com.brainbout.dao;


import com.brainbout.dto.*;
import java.sql.*;
import java.util.*;


/**
 * Created by System-2 on 12/13/2016.
 */
public class UserDAO {
    public List<UserDTO> getUserList(int company, int loc) throws Exception {
        List<UserDTO> feedbackList;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query;
            feedbackList = new ArrayList<UserDTO>();
            if (loc == 0) {
                query = "select distinct p.PHONE_NO,p.FULL_NAME as NAME, up.USER_ID as EMAIL_ID,c.COMPANY_NAME as COMPANY,\n" +
                        "l.LOCATION_TEXT as LOCATION, coalesce(cp.SCORE,0) as score,  coalesce(cp.PARTICIPANT_SEQ,0) as isParticipated\n" +
                        "from user_profile up \n" +
                        "left join participant p on p.USER_PROFILE_SEQ=up.USER_PROFILE_SEQ\n" +
                        "left join competition_participant cp on cp.PARTICIPANT_SEQ=p.PARTICIPANT_SEQ and cp.COMPETITION_SEQ=2\n" +
                        "left join company c on c.COMPANY_SEQ=up.COMPANY_SEQ\n" +
                        "left join location_mstr l on l.LOCATION_MSTR_SEQ=up.LOCATION_MSTR_SEQ\n" +
                        "WHERE c.`STATUS`='A' and c.COMPANY_SEQ=" + company;
            } else {
                query = "select distinct p.PHONE_NO,p.FULL_NAME as NAME, up.USER_ID as EMAIL_ID,c.COMPANY_NAME as COMPANY,\n" +
                        "l.LOCATION_TEXT as LOCATION, coalesce(cp.SCORE,0) as score,  coalesce(cp.PARTICIPANT_SEQ,0) as isParticipated\n" +
                        "from user_profile up \n" +
                        "left join participant p on p.USER_PROFILE_SEQ=up.USER_PROFILE_SEQ\n" +
                        "left join competition_participant cp on cp.PARTICIPANT_SEQ=p.PARTICIPANT_SEQ and cp.COMPETITION_SEQ=2\n" +
                        "left join company c on c.COMPANY_SEQ=up.COMPANY_SEQ\n" +
                        "left join location_mstr l on l.LOCATION_MSTR_SEQ=up.LOCATION_MSTR_SEQ\n" +
                        "WHERE c.`STATUS`='A' and c.COMPANY_SEQ=" + company + " and l.LOCATION_MSTR_SEQ="+ loc;
            }

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                UserDTO feedbackRequestDTO = new UserDTO();
                feedbackRequestDTO.setUserName(resultSet.getString("NAME"));
                feedbackRequestDTO.setCompany(resultSet.getString("COMPANY"));
                feedbackRequestDTO.setEmail(resultSet.getString("EMAIL_ID"));
                feedbackRequestDTO.setMobile(resultSet.getString("PHONE_NO"));
                feedbackRequestDTO.setLocation(resultSet.getString("LOCATION"));
                feedbackRequestDTO.setIsParticipated(resultSet.getInt("isParticipated"));
                feedbackRequestDTO.setScore(resultSet.getInt("score"));
                feedbackList.add(feedbackRequestDTO);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackList;
    }
}