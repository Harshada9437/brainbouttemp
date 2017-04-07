package com.heednow.dao.answer;

import com.heednow.dao.ConnectionHandler;
import com.heednow.dto.request.AnswerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 12/15/2016.
 */
public class AnswerDAO {
    public Integer createAnswer(int queId, String ans, int rating, int weightage, String threshold,int clientId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO question_answer_link(question_id , answer_desc, rating, weightage,threshold,client_id) values (?,?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    queId);
            preparedStatement.setString(parameterIndex++,
                    ans);
            preparedStatement.setInt(parameterIndex++,
                    rating);
            preparedStatement.setInt(parameterIndex++,
                    weightage);
            preparedStatement.setString(parameterIndex++,
                    threshold);
            preparedStatement.setInt(parameterIndex++,
                    clientId);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }

            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException(
                            "Creating answer failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                throw e;
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public static List<AnswerDTO> getAnswer(int questionId, int clientId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "SELECT qa.threshold,qa.question_id,qa.weightage,qb.question_desc as description,qa.answer_id,qa.answer_desc,qa.rating\n" +
                    " FROM question_answer_link qa\n" +
                    " left join question_bank qb\n" +
                    " ON\n" +
                    " qa.question_id=qb.id\n" +
                    " where question_id=" + questionId + " and qa.client_id=" + clientId;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setId(resultSet.getInt("answer_id"));
                answerDTO.setWeightage(resultSet.getInt("weightage"));
                answerDTO.setAnswerText(resultSet.getString("answer_desc"));
                answerDTO.setThreshold(resultSet.getString("threshold"));
                answerDTO.setRating(resultSet.getInt("rating"));
                answerDTOs.add(answerDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return answerDTOs;
    }

    public static List<AnswerDTO> getAnswer1(int questionId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "SELECT qa.threshold,qa.question_id,qa.weightage,qb.question_desc as description,qa.answer_id,qa.answer_desc,qa.rating\n" +
                    " FROM question_answer_link qa\n" +
                    " left join question_bank qb\n" +
                    " ON\n" +
                    " qa.question_id=qb.id\n" +
                    " where question_id=" + questionId;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setId(resultSet.getInt("answer_id"));
                answerDTO.setWeightage(resultSet.getInt("weightage"));
                answerDTO.setAnswerText(resultSet.getString("answer_desc"));
                answerDTO.setThreshold(resultSet.getString("threshold"));
                answerDTO.setRating(resultSet.getInt("rating"));
                answerDTOs.add(answerDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return answerDTOs;
    }

    public static AnswerDTO getAnswerById(int ansId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        AnswerDTO answerDTO = new AnswerDTO();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT qa.threshold,qa.question_id,qa.weightage,qb.question_desc as description,qa.answer_id,qa.answer_desc,qa.rating\n" +
                    " FROM question_answer_link qa\n" +
                    " left join question_bank qb\n" +
                    " ON\n" +
                    " qa.question_id=qb.id\n" +
                    " where qa.answer_id=" + ansId);
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setId(resultSet.getInt("answer_id"));
                answerDTO.setWeightage(resultSet.getInt("weightage"));
                answerDTO.setAnswerText(resultSet.getString("answer_desc"));
                answerDTO.setThreshold(resultSet.getString("threshold"));
                answerDTO.setRating(resultSet.getInt("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return answerDTO;
    }

    public Boolean updateAnswer(int id, String label, int rating, int weightage, String threshold) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE question_answer_link SET weightage=?, answer_desc =?, rating =?, threshold=? WHERE answer_id =?");

            preparedStatement.setInt(parameterIndex++, weightage);

            preparedStatement.setString(parameterIndex++, label);

            preparedStatement.setInt(parameterIndex++, rating);

            preparedStatement.setString(parameterIndex++, threshold);

            preparedStatement.setInt(parameterIndex++, id);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isCreated = Boolean.TRUE;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreated;
    }


    public Boolean deleteAnswer(int id) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("delete from question_answer_link WHERE answer_id = " + id);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isCreated = Boolean.TRUE;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreated;
    }
}
