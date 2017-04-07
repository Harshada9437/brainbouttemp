package com.heednow.requesthandler;

import com.heednow.bo.*;
import com.heednow.dao.ConnectionHandler;
import com.heednow.dao.FeedbackDAO;
import com.heednow.dao.Sync.SmsDAO;
import com.heednow.dao.Sync.SyncDAO;
import com.heednow.dao.answer.AnswerDAO;
import com.heednow.dao.customer.CustomerDAO;
import com.heednow.dao.outlet.OutletDAO;
import com.heednow.dao.question.QuestionDAO;
import com.heednow.dao.user.UsersDAO;
import com.heednow.dto.request.*;
import com.heednow.dto.response.LoginResponseDTO;
import com.heednow.exceptions.FeedbackNotFoundException;
import com.heednow.exceptions.QuestionNotFoundException;
import com.heednow.exceptions.UserNotFoundException;
import com.heednow.request.feedback.FeedbackDetails;
import com.heednow.request.report.ReportData;
import com.heednow.response.feedback.CreateCustomer;
import com.heednow.response.feedback.FeedbackByIdResponse;
import com.heednow.response.feedback.FeedbackResponse;
import com.heednow.response.feedback.FeedbackTrackingResponse;

import java.sql.Connection;
import java.util.Date;

import com.heednow.util.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by user on 10/18/2016.
 */
public class FeedbackRequestHandler {
    public Integer addFeedback(FeedbackRequestBO feedbackRequestBO) throws SQLException, QuestionNotFoundException, FeedbackNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int customerId;

        String mobile = feedbackRequestBO.getCustomer().getPhoneNo();
        customerId = CustomerDAO.getValidationForPhoneNumber(mobile);
        if (customerId == 0) {
            customerId = createCustomer(feedbackRequestBO.getCustomer());
        } else {
            UpdateCustomerRequestBO updateCustomerRequestBO = new UpdateCustomerRequestBO();
            updateCustomerRequestBO.setId(customerId);
            updateCustomerRequestBO.setName(feedbackRequestBO.getCustomer().getName());
            updateCustomerRequestBO.setLocality(feedbackRequestBO.getCustomer().getLocality());
            updateCustomerRequestBO.setPhoneNo(feedbackRequestBO.getCustomer().getPhoneNo());
            updateCustomerRequestBO.setEmailId(feedbackRequestBO.getCustomer().getEmailId());
            updateCustomerRequestBO.setDob(feedbackRequestBO.getCustomer().getDob());
            updateCustomerRequestBO.setDoa(feedbackRequestBO.getCustomer().getDoa());
            CustomerRequestHandler customerRequestHandler = new CustomerRequestHandler();
            customerRequestHandler.updateCustomer(updateCustomerRequestBO);
        }

        int id = feedbackDAO.addFeedback(buildFeedbackRequestDTOFromBO(feedbackRequestBO), customerId);
        List<FeedbackDetails> feedbacks = feedbackDAO.getfeedback(id);
        Boolean isExist = Boolean.FALSE;
        UpdateSettingsDTO setting = OutletDAO.getSetting(feedbackRequestBO.getOutletId());
        for (FeedbackDetails feedbackDetails : feedbacks) {
            AnswerDTO answerDTO = AnswerDAO.getAnswerById(feedbackDetails.getAnswerId());
            QuestionRequestDTO questionRequestDTO = QuestionDAO.getQuestionById(feedbackDetails.getQuestionId());
            if (setting.getSmsGatewayId() != null && !setting.getSmsGatewayId().equals("") &&
                    answerDTO.getThreshold() != null && !answerDTO.getThreshold().equals("")) {
                if ((questionRequestDTO.getQuestionType() == '2' || questionRequestDTO.getQuestionType() == '3')
                        && feedbackDetails.getRating() != 0) {
                    int ans = answerDTO.getRating() / answerDTO.getWeightage();
                    int weightage = feedbackDetails.getRating() / ans;
                    if (weightage <= Integer.parseInt(answerDTO.getThreshold())) {
                        isExist = Boolean.TRUE;
                        FeedbackDAO.updateFeedback(id);
                        break;
                    }
                }
                if ((questionRequestDTO.getQuestionType() == '1' || questionRequestDTO.getQuestionType() == '5' ||
                        questionRequestDTO.getQuestionType() == '6') && answerDTO.getThreshold().equals("1")) {
                    isExist = Boolean.TRUE;
                    FeedbackDAO.updateFeedback(id);
                    break;
                }
            }
        }
        if (isExist) {
            SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(feedbackRequestBO.getClientId());
            SmsSettingDTO smsSettingDTO = SmsDAO.fetchSmsSettingsById(setting.getSmsGatewayId());
            SendSms sendSms = new SendSms();
            sendSms.sendThresholdSms(id, settingRequestDTO.getSmsTemplate(), smsSettingDTO);
        }
        return id;
    }

    private FeedbackRequestDTO buildFeedbackRequestDTOFromBO(FeedbackRequestBO feedbackRequestBO) {
        FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();

        feedbackRequestDTO.setOutletId(feedbackRequestBO.getOutletId());
        feedbackRequestDTO.setClientId(feedbackRequestBO.getClientId());
        feedbackRequestDTO.setDeviceId(feedbackRequestBO.getDeviceId());
        feedbackRequestDTO.setFeedbacks(feedbackRequestBO.getFeedbacks());
        feedbackRequestDTO.setTableNo(feedbackRequestBO.getTableNo());
        feedbackRequestDTO.setBillNo(feedbackRequestBO.getBillNo());
        feedbackRequestDTO.setDate(feedbackRequestBO.getDate());
        feedbackRequestDTO.setCustomer(feedbackRequestBO.getCustomer());

        return feedbackRequestDTO;
    }

    public int createCustomer(CreateCustomer createCustomer) throws SQLException {

        CustomerDAO customerDAO = new CustomerDAO();
        int id = customerDAO.addCustomer(createCustomer.getLocality(), createCustomer.getName(),
                createCustomer.getPhoneNo(), createCustomer.getEmailId(), createCustomer.getDob(),
                createCustomer.getDoa());
        return id;

    }

    public List<FeedbackResponse> getfeedbackList1(FeedbackListRequestBO feedbackListRequestBO,int clientId) throws Exception {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<FeedbackDTO> feedbackRequestDTOS;
        String limit, where1 = "", ids;
        int threshold = 100000, base = 100000, length;

        Timestamp t1 = DateUtil.getTimeStampFromString(feedbackListRequestBO.getFromDate());
        String date = DateUtil.format(t1, "MM-yyyy");

        Connection connection = null;

        if (feedbackListRequestBO.getTableNo() != null && !feedbackListRequestBO.getTableNo().equals("")) {
            where1 = " and f.table_no=\"" + feedbackListRequestBO.getTableNo() + "\"\n";
        }
        if (feedbackListRequestBO.getOutletId() != null && feedbackListRequestBO.getOutletId().size() > 0) {
            ids = CommaSeparatedString.generate(feedbackListRequestBO.getOutletId());
            where1 += " and f.outlet_id IN(" + ids + ")\n";
        }
        if (feedbackListRequestBO.getOutletId() == null || feedbackListRequestBO.getOutletId().size() == 0) {
            LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListRequestBO.getUserId());
            RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
            where1 += " and f.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")\n";
        }

        SyncDAO syncDAO = new SyncDAO();
        int max = syncDAO.getCount(clientId,where1, date, feedbackListRequestBO.getFromDate(), feedbackListRequestBO.getToDate());

        if (max < threshold) {
            length = 1;
        } else {
            length = max / threshold;
            if (max % threshold > 0) {
                length = length + 1;
            }
        }

        List<FeedbackResponse> uniqueList = new ArrayList<FeedbackResponse>(max);

        try {
            connection = new ConnectionHandler().getConnection();

            for (int i = 0; i < length; i++) {

                if (i == 0) {
                    limit = "limit 100000";
                } else {
                    limit = "limit " + base + "," + threshold;
                }

                if (i == length - 1) {
                    feedbackRequestDTOS = feedbackDAO.getfeedbackList1(clientId,where1, connection, buildFeedbackDTO
                            (feedbackListRequestBO), date, limit);
                } else {
                    feedbackRequestDTOS = feedbackDAO.getfeedbackList1(clientId,where1, connection, buildFeedbackDTO
                            (feedbackListRequestBO), date, limit);
                }

                List<Integer> uniqueIds = new ArrayList<Integer>();
                for (int j = 0; j < feedbackRequestDTOS.size(); j++) {
                    if (feedbackRequestDTOS.get(j).getIsAddressed() > 0) {
                        feedbackRequestDTOS.get(j).setIsAddressed(1);
                    }
                    if (!uniqueIds.contains(feedbackRequestDTOS.get(j).getId())) {
                        FeedbackResponse feedbackResp = new FeedbackResponse(feedbackRequestDTOS.get(j).getId(),
                                feedbackRequestDTOS.get(j).getViewCount(),
                                feedbackRequestDTOS.get(j).getFeedbackDate(),
                                feedbackRequestDTOS.get(j).getTableNo(),
                                feedbackRequestDTOS.get(j).getCustomerName(),
                                feedbackRequestDTOS.get(j).getOutletDesc(),
                                feedbackRequestDTOS.get(j).getMobileNo(),
                                feedbackRequestDTOS.get(j).getEmail(),
                                feedbackRequestDTOS.get(j).getDob(),
                                feedbackRequestDTOS.get(j).getDoa(),
                                feedbackRequestDTOS.get(j).getLocality(),
                                feedbackRequestDTOS.get(j).getIsAddressed(),
                                feedbackRequestDTOS.get(j).getViewDate(),
                                feedbackRequestDTOS.get(j).getIsNegative());
                        List<FeedbackDetails> newAnswerList = new ArrayList<FeedbackDetails>();
                        FeedbackDetails answer = new FeedbackDetails();
                        answer.setAnswerDesc(feedbackRequestDTOS.get(j).getAnswerDesc());
                        answer.setAnswerText(feedbackRequestDTOS.get(j).getAnswerText());
                        answer.setQuestionDesc(feedbackRequestDTOS.get(j).getQuestionDesc());
                        answer.setRating(feedbackRequestDTOS.get(j).getRating());
                        answer.setIsNegative(feedbackRequestDTOS.get(j).getIsPoor());
                        answer.setQuestionType(feedbackRequestDTOS.get(j).getQuestionType());
                        newAnswerList.add(answer);
                        feedbackResp.setFeedbacks(newAnswerList);
                        uniqueIds.add(feedbackRequestDTOS.get(j).getId());
                        uniqueList.add(feedbackResp);
                    } else {
                        FeedbackResponse existingResp = getResponseFromList(uniqueList, feedbackRequestDTOS.get(j).getId());
                        if (existingResp != null) {
                            List<FeedbackDetails> curAnswerList = existingResp.getFeedbacks();
                            FeedbackDetails answer = new FeedbackDetails();
                            answer.setAnswerDesc(feedbackRequestDTOS.get(j).getAnswerDesc());
                            answer.setAnswerText(feedbackRequestDTOS.get(j).getAnswerText());
                            answer.setQuestionDesc(feedbackRequestDTOS.get(j).getQuestionDesc());
                            answer.setRating(feedbackRequestDTOS.get(j).getRating());
                            answer.setIsNegative(feedbackRequestDTOS.get(j).getIsPoor());
                            answer.setQuestionType(feedbackRequestDTOS.get(j).getQuestionType());
                            curAnswerList.add(answer);
                        }
                    }
                }
                if (i > 0) {
                    base = base + threshold;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return uniqueList;
    }

    private FeedbackResponse getResponseFromList(List<FeedbackResponse> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                return list.get(i);
            }
        }
        return null;
    }

    private FeedbackListDTO buildFeedbackDTO(FeedbackListRequestBO feedbackListRequestBO) {
        FeedbackListDTO feedbackListDTO = new FeedbackListDTO();

        feedbackListDTO.setFromDate(feedbackListRequestBO.getFromDate());
        feedbackListDTO.setToDate(feedbackListRequestBO.getToDate());
        feedbackListDTO.setTableNo(feedbackListRequestBO.getTableNo());
        feedbackListDTO.setOutletId(feedbackListRequestBO.getOutletId());
        feedbackListDTO.setUserId(feedbackListRequestBO.getUserId());

        return feedbackListDTO;
    }

    public FeedbackByIdResponse getfeedbackById(int id) throws SQLException, FeedbackNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackByIdResponse feedbackByIdResponse = buildResponseFromDTO(feedbackDAO.getFeedbacksList(id));
        return feedbackByIdResponse;
    }

    public FeedbackByIdResponse buildResponseFromDTO(List<FeedbackRequestDTO> feedbackRequestDTOs) throws SQLException, FeedbackNotFoundException {
        FeedbackByIdResponse feedbackResp = null;
        List<FeedbackByIdResponse> uniqueList = new ArrayList<FeedbackByIdResponse>();
        List<Integer> uniqueIds = new ArrayList<Integer>();
        for (FeedbackRequestDTO feedbackRequestDTO : feedbackRequestDTOs) {
            if (!uniqueIds.contains(feedbackRequestDTO.getId())) {
                feedbackResp = new FeedbackByIdResponse(feedbackRequestDTO.getQuestionType(),
                        feedbackRequestDTO.getId(),
                        feedbackRequestDTO.getDeviceId(),
                        feedbackRequestDTO.getFeedbackDate(),
                        feedbackRequestDTO.getOutletId(),
                        feedbackRequestDTO.getTableNo(),
                        feedbackRequestDTO.getBillNo(),
                        feedbackRequestDTO.getCustomerId(),
                        feedbackRequestDTO.getCustomerName(),
                        feedbackRequestDTO.getMobileNo(),
                        feedbackRequestDTO.getEmail(),
                        feedbackRequestDTO.getDob(),
                        feedbackRequestDTO.getDoa(),
                        feedbackRequestDTO.getLocality(),
                        feedbackRequestDTO.getOutletDesc());
                List<FeedbackDetails> newAnswerList = new ArrayList<FeedbackDetails>();
                FeedbackDetails answer = new FeedbackDetails();
                answer.setAnswerDesc(feedbackRequestDTO.getAnswerDesc());
                answer.setAnswerText(feedbackRequestDTO.getAnswerText());
                answer.setQuestionDesc(feedbackRequestDTO.getQuestionDesc());
                answer.setRating(feedbackRequestDTO.getRating());
                answer.setAnswerId(feedbackRequestDTO.getAnswerId());
                answer.setQuestionId(feedbackRequestDTO.getQuestionId());
                answer.setQuestionType(feedbackRequestDTO.getQuestionType());
                answer.setWeightage(feedbackRequestDTO.getWeightage());
                answer.setThreshold(feedbackRequestDTO.getThreshold());
                AnswerDTO answerDTO = AnswerDAO.getAnswerById(answer.getAnswerId());
                if (answer.getThreshold() != null && !answer.getThreshold().equals("")) {
                    if ((answer.getQuestionType() == '2' || answer.getQuestionType() == '3') && answer.getRating()
                            != 0) {
                        int ans = answerDTO.getRating() / answerDTO.getWeightage();
                        int weightage = answer.getRating() / ans;
                        if (weightage <= Integer.parseInt(answerDTO.getThreshold())) {
                            answer.setIsNegative(1);
                        } else {
                            answer.setIsNegative(0);
                        }
                    }
                    if ((answer.getQuestionType() == '1' || answer.getQuestionType() == '5' ||
                            answer.getQuestionType() == '6')) {
                        if (answerDTO.getThreshold().equals("1")) {
                            answer.setIsNegative(1);
                        } else {
                            answer.setIsNegative(0);
                        }
                    }
                } else {
                    answer.setIsNegative(0);
                }
                newAnswerList.add(answer);
                feedbackResp.setFeedbacks(newAnswerList);

                uniqueIds.add(feedbackRequestDTO.getId());
                uniqueList.add(feedbackResp);
            } else {
                FeedbackByIdResponse existingResp = getResponseFromList1(uniqueList, feedbackRequestDTO.getId());
                if (existingResp != null) {
                    List<FeedbackDetails> curAnswerList = existingResp.getFeedbacks();
                    FeedbackDetails answer = new FeedbackDetails();
                    answer.setAnswerDesc(feedbackRequestDTO.getAnswerDesc());
                    answer.setAnswerText(feedbackRequestDTO.getAnswerText());
                    answer.setQuestionDesc(feedbackRequestDTO.getQuestionDesc());
                    answer.setRating(feedbackRequestDTO.getRating());
                    answer.setAnswerId(feedbackRequestDTO.getAnswerId());
                    answer.setQuestionId(feedbackRequestDTO.getQuestionId());
                    answer.setQuestionType(feedbackRequestDTO.getQuestionType());
                    answer.setWeightage(feedbackRequestDTO.getWeightage());
                    answer.setThreshold(feedbackRequestDTO.getThreshold());
                    AnswerDTO answerDTO = AnswerDAO.getAnswerById(answer.getAnswerId());
                    if (answer.getThreshold() != null && !answer.getThreshold().equals("")) {
                        if ((answer.getQuestionType() == '2' || answer.getQuestionType() == '3') &&
                                answer.getRating() != 0) {
                            int ans = answerDTO.getRating() / answerDTO.getWeightage();
                            int weightage = answer.getRating() / ans;
                            if (weightage <= Integer.parseInt(answerDTO.getThreshold())) {
                                answer.setIsNegative(1);
                            } else {
                                answer.setIsNegative(0);
                            }
                        }
                        if ((answer.getQuestionType() == '1' || answer.getQuestionType() == '5' ||
                                answer.getQuestionType() == '6')) {
                            if (answerDTO.getThreshold().equals("1")) {
                                answer.setIsNegative(1);
                            } else {
                                answer.setIsNegative(0);
                            }
                        }
                    } else {
                        answer.setIsNegative(0);
                    }
                    curAnswerList.add(answer);
                }
            }
        }

        return feedbackResp;
    }


    private FeedbackByIdResponse getResponseFromList1(List<FeedbackByIdResponse> list, int id) {
        for (FeedbackByIdResponse aList : list) {
            if (id == aList.getId()) {
                return aList;
            }
        }
        return null;
    }


    public Boolean createFeedbackTracking(FeedbackTrackingRequestBO feedbackTrackingRequestBO, int clientId) throws SQLException, FeedbackNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Boolean isUpdate;
        FeedbackTrackingDTO feedbackTrackingDTO = buildFeedbackTrackingDTOFromBO(feedbackTrackingRequestBO);
        FeedbackTrackingDTO feedbackTrackingDTO1 = feedbackDAO.getcustomer(feedbackTrackingDTO.getFeedbackId());
        if (feedbackTrackingDTO1.getFeedbackId() == 0) {
            isUpdate = feedbackDAO.createFeedbackTracking(feedbackTrackingDTO,clientId);
        } else {
            isUpdate = feedbackDAO.updateFeedbackTracking(feedbackTrackingDTO,clientId);
        }
        return isUpdate;
    }


    private FeedbackTrackingDTO buildFeedbackTrackingDTOFromBO(FeedbackTrackingRequestBO feedbackTrackingRequestBO) throws FeedbackNotFoundException {
        FeedbackTrackingDTO feedbackTrackingDTO = new FeedbackTrackingDTO();

        feedbackTrackingDTO.setFeedbackId(feedbackTrackingRequestBO.getFeedbackId());
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackRequestDTO feedbackRequestDTO = feedbackDAO.getfeedbackById(feedbackTrackingRequestBO.getFeedbackId());
        UpdateSettingsDTO updateSettingsDTO = OutletDAO.getSetting(feedbackRequestDTO.getOutletId());
        if (updateSettingsDTO.getMgrEmail() == null && updateSettingsDTO.getMgrMobile() == null && updateSettingsDTO.getMgrName() == null) {
            feedbackTrackingDTO.setManagerEmail("");
            feedbackTrackingDTO.setManagerMobile("");
            feedbackTrackingDTO.setManagerName("");
        }
        feedbackTrackingDTO.setManagerMobile(updateSettingsDTO.getMgrMobile());
        feedbackTrackingDTO.setManagerName(updateSettingsDTO.getMgrName());
        feedbackTrackingDTO.setManagerEmail(updateSettingsDTO.getMgrEmail());
        return feedbackTrackingDTO;
    }


    public List<FeedbackTrackingResponse> getFeedbackTrackingList(FeedbackListRequestBO feedbackListRequestBO, int isNegative,int clientId) throws SQLException, UserNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<FeedbackTrackingResponseDTO> trackingList;
        String limit, where1 = "", ids;
        int threshold = 100000, base = 100000, length;

        Timestamp t1 = DateUtil.getTimeStampFromString(feedbackListRequestBO.getFromDate());
        String date = DateUtil.format(t1, "MM-yyyy");

        Connection connection = null;

        if (feedbackListRequestBO.getTableNo() != null && !feedbackListRequestBO.getTableNo().equals("")) {
            where1 = " and f.table_no=\"" + feedbackListRequestBO.getTableNo() + "\"\n";
        }
        if (feedbackListRequestBO.getOutletId() != null && feedbackListRequestBO.getOutletId().size() > 0) {
            ids = CommaSeparatedString.generate(feedbackListRequestBO.getOutletId());
            where1 += " and f.outlet_id IN(" + ids + ")\n";
        }
        if (feedbackListRequestBO.getOutletId() == null || feedbackListRequestBO.getOutletId().size() == 0) {
            LoginResponseDTO loginResponseDTO = UsersDAO.getuserById(feedbackListRequestBO.getUserId());
            RoleRequestDTO rollRequestDTO = UsersDAO.getroleById(loginResponseDTO.getRoleId());
            where1 += " and f.outlet_id IN(" + rollRequestDTO.getOutletAccess() + ")\n";
        }

        SyncDAO syncDAO = new SyncDAO();
        int max = syncDAO.getCount(clientId,where1, date, feedbackListRequestBO.getFromDate(), feedbackListRequestBO.getToDate());

        if (max < threshold) {
            length = 1;
        } else {
            length = max / threshold;
            if (max % threshold > 0) {
                length = length + 1;
            }
        }

        List<FeedbackTrackingResponse> feedbackTrackingDTOList = new ArrayList<FeedbackTrackingResponse>(max);

        try {
            connection = new ConnectionHandler().getConnection();

            for (int i = 0; i < length; i++) {

                if (i == 0) {
                    limit = "limit 100000";
                } else {
                    limit = "limit " + base + "," + threshold;
                }

                if (i == length - 1) {
                    trackingList = feedbackDAO.getFeedbackTrackingList(clientId,connection,where1,limit,feedbackListRequestBO, isNegative, date);
                } else {
                    trackingList = feedbackDAO.getFeedbackTrackingList(clientId,connection,where1,limit,feedbackListRequestBO, isNegative, date);
                }

                for (FeedbackTrackingResponseDTO feedbackTrackingResponseDTO : trackingList) {
                    if (feedbackTrackingResponseDTO.getIsAddressed() > 0) {
                        feedbackTrackingResponseDTO.setIsAddressed(1);
                    }
                    FeedbackTrackingResponse feedbackTrackingResponse = new FeedbackTrackingResponse(feedbackTrackingResponseDTO.getFeedbackId(),
                            feedbackTrackingResponseDTO.getOutletId(),
                            feedbackTrackingResponseDTO.getOutletName(),
                            feedbackTrackingResponseDTO.getEmail(),
                            feedbackTrackingResponseDTO.getDate(),
                            feedbackTrackingResponseDTO.getTableNo(),
                            feedbackTrackingResponseDTO.getCustomerId(),
                            feedbackTrackingResponseDTO.getCustomerName(),
                            feedbackTrackingResponseDTO.getPhoneNo(),
                            feedbackTrackingResponseDTO.getMgrName(),
                            feedbackTrackingResponseDTO.getMgrMobileNo(),
                            feedbackTrackingResponseDTO.getMgrEmail(),
                            feedbackTrackingResponseDTO.getFistViewDate(),
                            feedbackTrackingResponseDTO.getViewCount(),
                            feedbackTrackingResponseDTO.getIsAddressed(),
                            feedbackTrackingResponseDTO.getIsNegative());

                    feedbackTrackingDTOList.add(feedbackTrackingResponse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return feedbackTrackingDTOList;
    }

    public Boolean getDailyReport() throws SQLException, UserNotFoundException {
        Boolean isSent = Boolean.FALSE;
        UsersDAO usersDAO = new UsersDAO();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String currentDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);
        String date = DateUtil.format(t1, "MM-yyyy");

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, -1);
        Date date2 = cal.getTime();
        Timestamp t2 = new Timestamp(date2.getTime());
        String previousDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date3 = cal.getTime();
        Timestamp t3 = new Timestamp(date3.getTime());
        String previousMonth = DateUtil.getCurrentServerTimeByRemoteTimestamp(t3);

        List<LoginResponseDTO> users = usersDAO.getUserList1();
        for (LoginResponseDTO user : users) {
            if (!user.getOutletAccess().equals("")) {
                String outlets = user.getOutletAccess();
                ReportDTO dailyReportDTO = feedbackDAO.getDailyReport1(date, outlets, previousDate, currentDate);
                List<ReportData> dailyOutletReport = feedbackDAO.getOutletReport1(date, outlets, previousDate, currentDate);
                dailyReportDTO.setOutlets(dailyOutletReport);
                ReportDTO monthlyReportDTO = feedbackDAO.getDailyReport1(date, outlets, previousMonth, currentDate);
                List<ReportData> monthlyOutletReport = feedbackDAO.getOutletReport1(date, outlets, previousMonth, currentDate);
                monthlyReportDTO.setOutlets(monthlyOutletReport);
                dailyReportDTO.setUserName(user.getName());
                isSent = EmailService.sendReport(currentDate, user.getEmail(), dailyReportDTO, monthlyReportDTO);
            }
        }
        return isSent;
    }
}