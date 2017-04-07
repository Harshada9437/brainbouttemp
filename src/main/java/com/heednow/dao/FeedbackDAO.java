package com.heednow.dao;

import com.heednow.bo.FeedbackListRequestBO;
import com.heednow.dao.customer.CustomerDAO;
import com.heednow.dao.question.QuestionDAO;
import com.heednow.dto.request.*;
import com.heednow.exceptions.CustomerNotFoundException;
import com.heednow.exceptions.FeedbackNotFoundException;

import com.heednow.exceptions.QuestionNotFoundException;
import com.heednow.exceptions.UserNotFoundException;
import com.heednow.request.feedback.FeedbackDetails;
import com.heednow.request.report.Feedback;
import com.heednow.request.report.ReportData;
import com.heednow.util.DateUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by System-2 on 12/13/2016.
 */
public class FeedbackDAO {
    public Integer addFeedback(FeedbackRequestDTO feedbackRequestDTO, int customerId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            StringBuilder query = new StringBuilder("INSERT INTO feedback_head( device_id, outlet_id, date, customer_id, table_no, bill_no,client_id");
            query.append(")values (?,?,?,?,?,?,?)");

            preparedStatement = connection.prepareStatement(query.toString());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getDeviceId());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getOutletId());

            Timestamp date = DateUtil.getTimeStampFromString(feedbackRequestDTO.getDate());
            preparedStatement.setTimestamp(parameterIndex++, date);

            preparedStatement.setInt(parameterIndex++, customerId);

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getTableNo());

            preparedStatement.setString(parameterIndex++, feedbackRequestDTO.getBillNo());

            preparedStatement.setInt(parameterIndex++, feedbackRequestDTO.getClientId());


            int i = preparedStatement.executeUpdate();

            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    for (FeedbackDetails feedbackDetailsObj : feedbackRequestDTO.getFeedbacks()) {
                        createFeedbackDetail(id, feedbackDetailsObj.getQuestionId(), feedbackDetailsObj.getAnswerId(), feedbackDetailsObj.getAnswerText(), feedbackDetailsObj.getRating(), connection);
                    }
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Creating feedback failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
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


    public void createFeedbackDetail(int feedback_id, int question_id, int answer_id, String answer_text, int rating, Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        StringBuilder query = new StringBuilder("INSERT INTO feedback(feedback_id, question_id, answer_id, answer_text, rating) values (?,?,?,?,?)");
        try {
            int parameterIndex = 1;
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    feedback_id);
            preparedStatement.setInt(parameterIndex++,
                    question_id);
            preparedStatement.setInt(parameterIndex++,
                    answer_id);
            preparedStatement.setString(parameterIndex++,
                    answer_text);
            preparedStatement.setInt(parameterIndex++,
                    rating);

            int i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<FeedbackDTO> getfeedbackList1(int clientId,String where1,Connection connection,FeedbackListDTO feedbackListDTO, String date, String limit) throws Exception {
        List<FeedbackDTO> feedbackList;
        Statement statement = null;
        String table ="feedbacks_"+date;

        try {

            statement = connection.createStatement();

            feedbackList = new ArrayList<FeedbackDTO>();

                String query = "select f.feedback_id,f.is_poor,f.date,f.customer_name,f.customer_no,f.customer_email" +
                        ",f.dob,f.doa,f.locality,f.table_no,f.outlet_name,f.rating,f.answer_text,f.question_desc,f.question_type" +
                        ",f.answer_desc,f.isNegative,t.feedback_id as isAddressed,t.first_view_date,t.view_count,t.manager_name,t.manager_mobile" +
                        ",t.manager_email from `" + table + "` f\n" +
                        "left join feedback_view_tracking t on f.feedback_id = t.feedback_id\n" +
                        "where f.date>='" + feedbackListDTO.getFromDate() + "' and f.date<='" + feedbackListDTO.getToDate()
                        + "' and f.client_id="+clientId + where1 + limit;

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    FeedbackDTO feedbackRequestDTO = new FeedbackDTO();
                    feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                    feedbackRequestDTO.setViewCount(resultSet.getInt("view_count"));
                    feedbackRequestDTO.setIsPoor(resultSet.getInt("is_poor"));
                    feedbackRequestDTO.setFeedbackDate(resultSet.getString("date"));
                    feedbackRequestDTO.setCustomerName(resultSet.getString("customer_name"));
                    feedbackRequestDTO.setMobileNo(resultSet.getString("customer_no"));
                    feedbackRequestDTO.setEmail(resultSet.getString("customer_email"));
                    feedbackRequestDTO.setDob(resultSet.getString("dob"));
                    feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                    feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                    feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                    feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_name"));
                    feedbackRequestDTO.setMgrName(resultSet.getString("manager_name"));
                    feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                    feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                    feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                    feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                    feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                    feedbackRequestDTO.setMgrMobile(resultSet.getString("manager_mobile"));
                    feedbackRequestDTO.setMgrEmail(resultSet.getString("manager_email"));
                    if (resultSet.getTimestamp("first_view_date") == null) {
                        feedbackRequestDTO.setViewDate("");
                    } else {
                        feedbackRequestDTO.setViewDate(DateUtil.getCurrentServerTimeByRemoteTimestamp(resultSet.getTimestamp("first_view_date")));
                    }
                    feedbackRequestDTO.setIsNegative(resultSet.getInt("isNegative"));
                    feedbackRequestDTO.setIsAddressed(resultSet.getInt("isAddressed"));
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

    public List<FeedbackDetails> getfeedback(int id) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<FeedbackDetails> answerDTOS = new ArrayList<FeedbackDetails>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select f.created_on,f.question_id,f.answer_id,f.answer_text,f.rating,q.question_type,q.question_desc,a.answer_desc\n" +
                    " from feedback f\n" +
                    "left join feedback_head fh\n" +
                    "on f.feedback_id = fh.id\n" +
                    "left join question_bank q\n" +
                    "on f.question_id = q.id\n" +
                    "left join question_answer_link a\n" +
                    "on f.answer_id = a.answer_id\n" +
                    "where f.feedback_id=" + id;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackDetails answerDTO = new FeedbackDetails();
                answerDTO.setAnswerId(resultSet.getInt("answer_id"));
                answerDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                answerDTO.setRating(resultSet.getInt("rating"));
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setAnswerText(resultSet.getString("answer_text"));
                answerDTO.setQuestionDesc(resultSet.getString("question_desc"));
                answerDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                answerDTOS.add(answerDTO);
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
        return answerDTOS;
    }

    public FeedbackRequestDTO getfeedbackById(int id) throws FeedbackNotFoundException {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select f.feedback_id,f.question_id,f.rating,f.answer_text,f.answer_id, fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.id=" + id;
            ResultSet resultSet = statement.executeQuery(query);
            int rowCount = 0;
            while (resultSet.next()) {

                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("feedback_date")));
                feedbackRequestDTO.setCustomerId(resultSet.getInt("customer_id"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("email_id"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setBillNo(resultSet.getString("bill_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setWeightage(resultSet.getInt("weightage"));
                rowCount++;
            }
            if (rowCount == 0) {
                throw new FeedbackNotFoundException("Feedback id invalid");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackRequestDTO;
    }

    public List<FeedbackRequestDTO> getFeedbacksList(int id) throws SQLException, FeedbackNotFoundException {
        List<FeedbackRequestDTO> feedbackRequestDTOs = new ArrayList<FeedbackRequestDTO>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select f.feedback_id,f.question_id,f.rating,f.answer_text,f.answer_id,fh.device_id,a.threshold, fh.date as feedback_date,a.weightage,q.question_type, q.question_desc, a.answer_desc,fh.outlet_id,o.outlet_desc ,fh.customer_id,c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no,fh.bill_no\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.id=" + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setFeedbackDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("feedback_date")));
                feedbackRequestDTO.setCustomerId(resultSet.getInt("customer_id"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("email_id"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setBillNo(resultSet.getString("bill_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setQuestionId(resultSet.getInt("question_id"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setWeightage(resultSet.getInt("weightage"));
                feedbackRequestDTO.setThreshold(resultSet.getString("threshold"));
                feedbackRequestDTO.setDeviceId(resultSet.getInt("device_id"));

                feedbackRequestDTOs.add(feedbackRequestDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return feedbackRequestDTOs;
    }

    public List<CountDTO> getcountById(String date, int id,int clientId) throws SQLException, QuestionNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<CountDTO> countDTOs = new ArrayList<CountDTO>();
        String table = "feedbacks_" + date;
        try {
            connection = new ConnectionHandler().getConnection();
            QuestionDAO.getQuestionById(id);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "select answer_id,coalesce(count(rating),0) as count,question_desc,question_type,rating,answer_desc,question_id from `"+table+"` \n" +
                            "where question_id=" + id + " and rating<>0 and client_id="+clientId+"\n" +
                            "group by answer_id,rating ");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                CountDTO countDTO = new CountDTO();
                countDTO.setQuestionDesc(resultSet.getString("question_desc"));
                countDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                countDTO.setQuestionType(resultSet.getString("question_type"));
                countDTO.setRating(resultSet.getInt("rating"));
                countDTO.setCount(resultSet.getInt("count"));
                countDTOs.add(countDTO);
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
        return countDTOs;
    }


    public List<AverageDTO> getaverageById(String date, int id, int clientId) throws SQLException, QuestionNotFoundException {
        Connection connection = null;
        Statement statement = null;
        List<AverageDTO> averageDTOs = new ArrayList<AverageDTO>();
        String table = "feedbacks_" + date;
        try {
            connection = new ConnectionHandler().getConnection();
            QuestionDAO.getQuestionById(id);
            statement = connection.createStatement();

            StringBuilder query = new StringBuilder(
                    "select question_desc,question_type,rating,answer_desc,question_id,answer_id,coalesce(ROUND(avg(rating),0),0) as average from `"+table+"` \n" +
                            "where question_id=" + id + " and rating<>0 and client_id="+clientId+"\n" +
                            "group by answer_id ");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                AverageDTO averageDTO = new AverageDTO();
                averageDTO.setQuestionDesc(resultSet.getString("question_desc"));
                averageDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                averageDTO.setAverage(resultSet.getFloat("average"));
                averageDTOs.add(averageDTO);
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
        return averageDTOs;
    }


    public CustomerReportDTO getcustomerByPhoneNo(String date,String phoneNo) throws SQLException, CustomerNotFoundException {
        CustomerReportDTO customerReportDTO = new CustomerReportDTO();
        String table = "feedbacks_" + date;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            CustomerDAO.getValidationForPhoneNumber(phoneNo);
            StringBuilder query = new StringBuilder(
                    "select customer_name,customer_email,customer_no,dob,doa,locality from `"+table+"` \n" +
                            "where customer_no=\"" + phoneNo + "\"\n" +
                            "group by customer_no"
            );
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                customerReportDTO.setMobile(resultSet.getString("customer_no"));
                customerReportDTO.setName(resultSet.getString("customer_name"));
                customerReportDTO.setEmailId(resultSet.getString("customer_email"));
                customerReportDTO.setDob(resultSet.getString("dob"));
                customerReportDTO.setDoa(resultSet.getString("doa"));
                customerReportDTO.setLocality(resultSet.getString("locality"));
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
        return customerReportDTO;
    }

    public static List<FeedbackDetails> getCustomerFeedbackDetail(String table,int id) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<FeedbackDetails> answerDTOS = new ArrayList<FeedbackDetails>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select question_id,answer_id,answer_text,rating,question_type,question_desc,answer_desc\n" +
                    " from `"+table+"`\n" +
                    "where feedback_id=" + id;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackDetails answerDTO = new FeedbackDetails();
                answerDTO.setAnswerId(resultSet.getInt("answer_id"));
                answerDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                answerDTO.setRating(resultSet.getInt("rating"));
                answerDTO.setQuestionId(resultSet.getInt("question_id"));
                answerDTO.setAnswerText(resultSet.getString("answer_text"));
                answerDTO.setQuestionDesc(resultSet.getString("question_desc"));
                answerDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                answerDTOS.add(answerDTO);
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
        return answerDTOS;
    }

    public static List<Feedback> getcustomerFeedback(String date,String name,int clientId) throws SQLException {
        Statement statement = null;
        Connection connection = null;
        List<Feedback> customerFeedbackDTOS = new ArrayList<Feedback>();
        String table = "feedbacks_"+ date;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "select feedback_id,outlet_name,date,table_no  from `"+table+"` \n" +
                    "where customer_no='" + name +"'"+" and client_id="+clientId+
                    "\ngroup by feedback_id\n";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Feedback customerFeedbackDTO = new Feedback();
                customerFeedbackDTO.setId(resultSet.getInt("feedback_id"));
                customerFeedbackDTO.setOutletDesc(resultSet.getString("outlet_name"));
                customerFeedbackDTO.setTableNo(resultSet.getString("table_no"));
                customerFeedbackDTO.setFeedbackDate(resultSet.getString("date"));
                customerFeedbackDTO.setFeedbackDetail(FeedbackDAO.getCustomerFeedbackDetail(table,customerFeedbackDTO.getId()));
                customerFeedbackDTOS.add(customerFeedbackDTO);

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
        return customerFeedbackDTOS;
    }


    public Boolean createFeedbackTracking(FeedbackTrackingDTO feedbackTrackingDTO, int clientId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO feedback_view_tracking(feedback_id,manager_mobile,manager_name,manager_email,client_id) values (?,?,?,?,?)");
        Boolean isCreate = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++, feedbackTrackingDTO.getFeedbackId());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerMobile());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerName());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerEmail());
            preparedStatement.setInt(parameterIndex++,clientId);


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                isCreate = Boolean.TRUE;
                connection.commit();
            } else {
                connection.rollback();
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
        return isCreate;
    }

    public FeedbackTrackingDTO getcustomer(int feedbackId) throws SQLException {

        FeedbackTrackingDTO feedbackTrackingDTO = new FeedbackTrackingDTO();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select * from feedback_view_tracking where feedback_id=").append(feedbackId);

            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                feedbackTrackingDTO.setFeedbackId(resultSet.getInt("feedback_id"));
                feedbackTrackingDTO.setManagerName(resultSet.getString("manager_name"));
                feedbackTrackingDTO.setManagerMobile(resultSet.getString("manager_mobile"));
                feedbackTrackingDTO.setManagerEmail(resultSet.getString("manager_email"));
                feedbackTrackingDTO.setFirstViewDate(DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("first_view_date")));
                feedbackTrackingDTO.setViewCount(resultSet.getInt("view_count"));
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
        return feedbackTrackingDTO;
    }


    public Boolean updateFeedbackTracking(FeedbackTrackingDTO feedbackTrackingDTO, int clientId) throws SQLException {
        boolean isUpdate = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE feedback_view_tracking SET manager_name=?,manager_email=?,manager_mobile=?,view_count =? WHERE feedback_id =? ");

            FeedbackTrackingDTO feedbackTrackingDTO1 = getcustomer(feedbackTrackingDTO.getFeedbackId());

            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerName());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerEmail());
            preparedStatement.setString(parameterIndex++, feedbackTrackingDTO.getManagerMobile());
            int count = feedbackTrackingDTO1.getViewCount();
            preparedStatement.setInt(parameterIndex++, count + 1);
            preparedStatement.setInt(parameterIndex++, feedbackTrackingDTO.getFeedbackId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isUpdate = Boolean.TRUE;
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
        return isUpdate;
    }


    public List<FeedbackTrackingResponseDTO> getFeedbackTrackingList(int clientId,Connection connection,String where1,String limit,FeedbackListRequestBO feedbackListRequestBO, int isNegative, String date) throws SQLException, UserNotFoundException {
        List<FeedbackTrackingResponseDTO> trackingDTOList = new ArrayList<FeedbackTrackingResponseDTO>();
        Statement statement = null;
        String where = "",table="feedbacks_"+date;
        try {

            statement = connection.createStatement();

            if (isNegative == 1) {
                where += " and isNegative=1\n";
            }

            String query = "select f.feedback_id,f.outlet_id,f.outlet_name,f.date,f.table_no,f.customer_name,f.isNegative" +
                    ",f.customer_no,f.customer_email,t.manager_name,t.manager_mobile,t.manager_email,t.feedback_id as isAddressed" +
                    ",t.view_count,t.first_view_date from `"+table+"` f\n" +
                    "left join feedback_view_tracking t on f.feedback_id = t.feedback_id\n" +
                    "where f.date>='" + feedbackListRequestBO.getFromDate() + "' and f.date<='"
                    + feedbackListRequestBO.getToDate() + "' and f.client_id="+ clientId + where1 + where+"group by f.feedback_id\n" + limit;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackTrackingResponseDTO feedbackTrackingResponseDTO = new FeedbackTrackingResponseDTO();
                feedbackTrackingResponseDTO.setFeedbackId(resultSet.getInt("feedback_id"));
                feedbackTrackingResponseDTO.setOutletId(resultSet.getInt("outlet_id"));
                feedbackTrackingResponseDTO.setOutletName(resultSet.getString("outlet_name"));
                feedbackTrackingResponseDTO.setDate(resultSet.getString("date"));
                feedbackTrackingResponseDTO.setTableNo(resultSet.getString("table_no"));
                feedbackTrackingResponseDTO.setCustomerName(resultSet.getString("customer_name"));
                feedbackTrackingResponseDTO.setEmail(resultSet.getString("customer_email"));
                feedbackTrackingResponseDTO.setPhoneNo(resultSet.getString("customer_no"));
                feedbackTrackingResponseDTO.setMgrName(resultSet.getString("manager_name"));
                feedbackTrackingResponseDTO.setMgrMobileNo(resultSet.getString("manager_mobile"));
                feedbackTrackingResponseDTO.setMgrEmail(resultSet.getString("manager_email"));
                if(resultSet.getTimestamp("first_view_date") == null){
                  feedbackTrackingResponseDTO.setFistViewDate("");
                }else {
                    feedbackTrackingResponseDTO.setFistViewDate(DateUtil.getCurrentServerTimeByRemoteTimestamp(resultSet.getTimestamp("first_view_date")));
                }
                feedbackTrackingResponseDTO.setViewCount(resultSet.getInt("view_count"));
                feedbackTrackingResponseDTO.setIsAddressed(resultSet.getInt("isAddressed"));
                feedbackTrackingResponseDTO.setIsNegative(resultSet.getInt("isNegative"));
                trackingDTOList.add(feedbackTrackingResponseDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return trackingDTOList;
    }

    public static void updateFeedback(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE feedback_head SET isNegative =1 WHERE id =?");

            preparedStatement.setInt(parameterIndex++, id);


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
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
    }

    public ReportDTO getDailyReport(int clientId,String date,String outlets, String from, String to) throws SQLException {
        ReportDTO reportDTO = new ReportDTO();
        String table="feedbacks_"+date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection
                    ();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select  sum(tab.total_count)as total_count,sum(tab.negative_count)as negative_count,\n" +
                            " sum(tab.addressed_count)as addressed_count,sum(tab.daily_bill_count)as daily_bill,sum(tab.monthly_bill_count)as monthly_bill from \n" +
                            "(SELECT o.id,COUNT(fm.feedback_id) as total_count,coalesce(sum(fm.isNegative=1),0) as negative_count,\n" +
                            "count(t.feedback_id) as addressed_count\n" +
                            ",o.outlet_desc,o.daily_bill_count, o.monthly_bill_count from (select cluster_id,outlet_desc,daily_bill_count, monthly_bill_count,id from outlet where id in("+outlets+") and client_id"+clientId+") as o\n" +
                            "left join (select f.feedback_id, f.isNegative, f.outlet_id from `"+table+"` f  where f.date >='"+from+"' and f.date <= '"+to+"' group by f.feedback_id) fm on o.id = fm.outlet_id\n" +
                            "left join feedback_view_tracking t on fm.feedback_id = t.feedback_id\n" +
                            "group by o.id) tab\n");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                reportDTO.setTotalCount(resultSet.getInt("total_count"));
                reportDTO.setNegativeCount(resultSet.getInt("negative_count"));
                reportDTO.setUnAddressedCount(reportDTO.getNegativeCount() - resultSet.getInt("addressed_count"));
                reportDTO.setDailyBillCount(resultSet.getInt("daily_bill"));
                reportDTO.setMonthlyBillCount(resultSet.getInt("monthly_bill"));
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
        return reportDTO;
    }

    public List<ReportData> getOutletReport(int clientId,String date, String outlets, String from, String to) throws SQLException {
        List<ReportData> reports = new ArrayList<ReportData>();
        String table="feedbacks_"+date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "SELECT o.id,COUNT(fm.feedback_id) as total_count,coalesce(sum(fm.isNegative=1),0) as negative_count,\n" +
                            "count(t.feedback_id) as addressed_count\n" +
                            ",c.cluster_desc,o.outlet_desc,o.daily_bill_count, o.monthly_bill_count from (select cluster_id,outlet_desc,daily_bill_count, monthly_bill_count,id from outlet where id in("+outlets+") and client_id="+clientId+") as o\n" +
                            "join cluster c on c.id=o.cluster_id\n" +
                            "left join (select f.feedback_id, f.isNegative, f.outlet_id from `"+table+"` f  where f.date >='"+from+"' and f.date <= '"+to+"' group by f.feedback_id) fm on o.id = fm.outlet_id\n" +
                            "left join feedback_view_tracking t on fm.feedback_id = t.feedback_id\n" +
                            "group by o.id\n" +
                            "order by negative_count desc");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                ReportData report = new ReportData();
                report.setTotalCount(resultSet.getInt("total_count"));
                report.setStoreId(resultSet.getString("outlet_desc"));
                report.setCity(resultSet.getString("cluster_desc"));
                report.setNegativeCount(resultSet.getInt("negative_count"));
                report.setUnAddressedCount(report.getNegativeCount() - resultSet.getInt("addressed_count"));
                report.setDailyBillCount(resultSet.getInt("daily_bill_count"));
                report.setMonthlyBillCount(resultSet.getInt("monthly_bill_count"));
                reports.add(report);
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
        return reports;
    }

    public ReportDTO getDailyReport1(String date,String outlets, String from, String to) throws SQLException {
        ReportDTO reportDTO = new ReportDTO();
        String table="feedbacks_"+date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection
                    ();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "select  sum(tab.total_count)as total_count,sum(tab.negative_count)as negative_count,\n" +
                            " sum(tab.addressed_count)as addressed_count,sum(tab.daily_bill_count)as daily_bill,sum(tab.monthly_bill_count)as monthly_bill from \n" +
                            "(SELECT o.id,COUNT(fm.feedback_id) as total_count,coalesce(sum(fm.isNegative=1),0) as negative_count,\n" +
                            "count(t.feedback_id) as addressed_count\n" +
                            ",o.outlet_desc,o.daily_bill_count, o.monthly_bill_count from (select cluster_id,outlet_desc,daily_bill_count, monthly_bill_count,id from outlet where id in("+outlets+")) as o\n" +
                            "left join (select f.feedback_id, f.isNegative, f.outlet_id from `"+table+"` f  where f.date >='"+from+"' and f.date <= '"+to+"' group by f.feedback_id) fm on o.id = fm.outlet_id\n" +
                            "left join feedback_view_tracking t on fm.feedback_id = t.feedback_id\n" +
                            "group by o.id) tab\n");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                reportDTO.setTotalCount(resultSet.getInt("total_count"));
                reportDTO.setNegativeCount(resultSet.getInt("negative_count"));
                reportDTO.setUnAddressedCount(reportDTO.getNegativeCount() - resultSet.getInt("addressed_count"));
                reportDTO.setDailyBillCount(resultSet.getInt("daily_bill"));
                reportDTO.setMonthlyBillCount(resultSet.getInt("monthly_bill"));
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
        return reportDTO;
    }

    public List<ReportData> getOutletReport1(String date, String outlets, String from, String to) throws SQLException {
        List<ReportData> reports = new ArrayList<ReportData>();
        String table="feedbacks_"+date;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuffer query = new StringBuffer(
                    "SELECT o.id,COUNT(fm.feedback_id) as total_count,coalesce(sum(fm.isNegative=1),0) as negative_count,\n" +
                            "count(t.feedback_id) as addressed_count\n" +
                            ",c.cluster_desc,o.outlet_desc,o.daily_bill_count, o.monthly_bill_count from (select cluster_id,outlet_desc,daily_bill_count, monthly_bill_count,id from outlet where id in("+outlets+")) as o\n" +
                            "join cluster c on c.id=o.cluster_id\n" +
                            "left join (select f.feedback_id, f.isNegative, f.outlet_id from `"+table+"` f  where f.date >='"+from+"' and f.date <= '"+to+"' group by f.feedback_id) fm on o.id = fm.outlet_id\n" +
                            "left join feedback_view_tracking t on fm.feedback_id = t.feedback_id\n" +
                            "group by o.id\n" +
                            "order by negative_count desc");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                ReportData report = new ReportData();
                report.setTotalCount(resultSet.getInt("total_count"));
                report.setStoreId(resultSet.getString("outlet_desc"));
                report.setCity(resultSet.getString("cluster_desc"));
                report.setNegativeCount(resultSet.getInt("negative_count"));
                report.setUnAddressedCount(report.getNegativeCount() - resultSet.getInt("addressed_count"));
                report.setDailyBillCount(resultSet.getInt("daily_bill_count"));
                report.setMonthlyBillCount(resultSet.getInt("monthly_bill_count"));
                reports.add(report);
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
        return reports;
    }


    public List<FeedbackDTO> getArchiveData(int id) throws SQLException {

        List<FeedbackDTO> feedbackList = new ArrayList<FeedbackDTO>();
        Connection connection = null;
        Statement statement = null;

        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            String query = "select o.id,f.answer_id,f.feedback_id,f.rating,f.answer_text,fh.date," +
                    "q.question_type, q.question_desc,a.weightage,a.rating as answer_rating,a.threshold, a.answer_desc,o.outlet_desc," +
                    "c.name,c.phone_no,c.email_id,c.dob,c.doa,c.locality, fh.table_no\n" +
                    ",fh.isNegative\n" +
                    "from feedback f\n" +
                    "left join feedback_head fh on fh.id=f.feedback_id\n" +
                    "left join outlet o on fh.outlet_id = o.id\n" +
                    "left join question_bank q on q.id = f.question_id\n" +
                    "left join customer c on c.id = fh.customer_id\n" +
                    "left join question_answer_link a on a.answer_id = f.answer_id\n" +
                    "where fh.id > "+id;
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                FeedbackDTO feedbackRequestDTO = new FeedbackDTO();
                feedbackRequestDTO.setId(resultSet.getInt("feedback_id"));
                feedbackRequestDTO.setOutletId(resultSet.getInt("id"));
                feedbackRequestDTO.setAnswerId(resultSet.getInt("answer_id"));
                feedbackRequestDTO.setWeightage(resultSet.getInt("weightage"));
                feedbackRequestDTO.setAnsRating(resultSet.getInt("answer_rating"));
                feedbackRequestDTO.setFeedbackDate(resultSet.getString("date"));
                feedbackRequestDTO.setCustomerName(resultSet.getString("name"));
                feedbackRequestDTO.setMobileNo(resultSet.getString("phone_no"));
                feedbackRequestDTO.setEmail(resultSet.getString("email_id"));
                feedbackRequestDTO.setDob(resultSet.getString("dob"));
                feedbackRequestDTO.setDoa(resultSet.getString("doa"));
                feedbackRequestDTO.setLocality(resultSet.getString("locality"));
                feedbackRequestDTO.setThreshold(resultSet.getString("threshold"));
                feedbackRequestDTO.setTableNo(resultSet.getString("table_no"));
                feedbackRequestDTO.setOutletDesc(resultSet.getString("outlet_desc"));
                feedbackRequestDTO.setRating(resultSet.getInt("rating"));
                feedbackRequestDTO.setAnswerText(resultSet.getString("answer_text"));
                feedbackRequestDTO.setQuestionDesc(resultSet.getString("question_desc"));
                feedbackRequestDTO.setAnswerDesc(resultSet.getString("answer_desc"));
                feedbackRequestDTO.setQuestionType(resultSet.getString("question_type").charAt(0));
                feedbackRequestDTO.setIsNegative(resultSet.getInt("isNegative"));
                feedbackList.add(feedbackRequestDTO);
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
        return feedbackList;
    }

}