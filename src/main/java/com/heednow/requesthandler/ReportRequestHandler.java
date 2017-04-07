package com.heednow.requesthandler;

import com.heednow.dao.FeedbackDAO;
import com.heednow.dao.Sync.SmsDAO;
import com.heednow.dao.user.UsersDAO;
import com.heednow.dto.request.*;
import com.heednow.dto.response.LoginResponseDTO;
import com.heednow.exceptions.CustomerNotFoundException;
import com.heednow.exceptions.QuestionNotFoundException;
import com.heednow.exceptions.UserNotFoundException;
import com.heednow.request.report.ReportData;
import com.heednow.response.report.*;
import com.heednow.util.DateUtil;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by System-3 on 2/13/2017.
 */
public class ReportRequestHandler {

    public List<CountResponse> getcountById(int id,int clientId) throws SQLException, QuestionNotFoundException {
        List<CountResponse> countList = new ArrayList<CountResponse>();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        //String to = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);
        String tab = DateUtil.format(t1,"MM-yyyy");

        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(clientId);
        Time archiveTime = settingRequestDTO.getArchiveTime();

       /* Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.SECOND, archiveTime.getSeconds());
        cal.set(Calendar.MINUTE, archiveTime.getMinutes());
        cal.set(Calendar.HOUR_OF_DAY, archiveTime.getHours());
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);*/

        List<CountDTO> countDTOList = feedbackDAO.getcountById(tab,id,clientId);

        for (com.heednow.dto.request.CountDTO countDTO : countDTOList) {
            CountResponse countResponse = new CountResponse();
            countResponse.setQuestionDesc(countDTO.getQuestionDesc());
            countResponse.setAnswerDesc(countDTO.getAnswerDesc());
            countResponse.setQuestionType(countDTO.getQuestionType());
            countResponse.setCount(countDTO.getCount());
            countResponse.setRating(countDTO.getRating());
            countList.add(countResponse);
        }

        return countList;
    }


    public List<AverageResponse> getaverageById(int id,int clientId) throws SQLException, QuestionNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<AverageResponse> averageList = new ArrayList<AverageResponse>();

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String to = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);
        String tab = DateUtil.format(t1,"MM-yyyy");

        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(clientId);
        Time archiveTime = settingRequestDTO.getArchiveTime();

       /* Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.SECOND, archiveTime.getSeconds());
        cal.set(Calendar.MINUTE, archiveTime.getMinutes());
        cal.set(Calendar.HOUR_OF_DAY, archiveTime.getHours());
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);*/


        List<AverageDTO> averageDTOList = feedbackDAO.getaverageById(tab,id,clientId);

        for (com.heednow.dto.request.AverageDTO averageDTO : averageDTOList) {
            AverageResponse averageResponse = new AverageResponse();
            averageResponse.setQuestionDesc(averageDTO.getQuestionDesc());
            averageResponse.setAnswerDesc(averageDTO.getAnswerDesc());
            averageResponse.setAverage(averageDTO.getAverage());

            averageList.add(averageResponse);
        }

        return averageList;
    }


    public CustomerReportResponseList getcustomerByPhoneNo(String phoneNo,int clientId) throws SQLException, CustomerNotFoundException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        Date date1 = new Date();
        Timestamp t1 = new Timestamp(date1.getTime());
        String to = DateUtil.getCurrentServerTimeByRemoteTimestamp(t1);
        String tab = DateUtil.format(t1,"MM-yyyy");

        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(clientId);
        Time archiveTime = settingRequestDTO.getArchiveTime();

       /* Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.SECOND, archiveTime.getSeconds());
        cal.set(Calendar.MINUTE, archiveTime.getMinutes());
        cal.set(Calendar.HOUR_OF_DAY, archiveTime.getHours());
        Date date = cal.getTime();
        Timestamp t2 = new Timestamp(date.getTime());
        String from = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);
*/
        CustomerReportResponseList customerReportResponseList = buildFeedbackCustomerFromDTO(clientId,tab,feedbackDAO.getcustomerByPhoneNo("03-2017",phoneNo));
        return customerReportResponseList;
    }

    public CustomerReportResponseList buildFeedbackCustomerFromDTO(int clientId,String tab,CustomerReportDTO customerReportDTO) throws SQLException, CustomerNotFoundException {
        CustomerReportResponseList customerReportResponseList = new CustomerReportResponseList();
        customerReportResponseList.setMobile(customerReportDTO.getMobile());
        customerReportResponseList.setName(customerReportDTO.getName());
        customerReportResponseList.setEmailId(customerReportDTO.getEmailId());
        customerReportResponseList.setDob(customerReportDTO.getDob());
        customerReportResponseList.setDoa(customerReportDTO.getDoa());
        customerReportResponseList.setLocality(customerReportDTO.getLocality());
        customerReportResponseList.setFeedback(FeedbackDAO.getcustomerFeedback(tab,customerReportResponseList.getMobile(),clientId));
        return customerReportResponseList;
    }

    public SummaryResponse getReport(int userId,int clientId) throws SQLException, UserNotFoundException {

        SummaryResponse summaryResponse = new SummaryResponse();
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(clientId);
        Time reportTime = settingRequestDTO.getReportTime();

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, reportTime.getSeconds());
        cal.set(Calendar.MINUTE, reportTime.getMinutes());
        cal.set(Calendar.HOUR_OF_DAY, reportTime.getHours());
        Date date1 = cal.getTime();
        Timestamp t1 = new Timestamp(date1.getTime());
        String currentDate = DateUtil.getDateStringFromTimeStamp(t1);
        String date = DateUtil.format(t1,"MM-yyyy");


        cal.setTime(date1);
        cal.add(Calendar.DATE, -1);
        Date date2 = cal.getTime();
        Timestamp t2 = new Timestamp(date2.getTime());
        String previousDate = DateUtil.getCurrentServerTimeByRemoteTimestamp(t2);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date3 = cal.getTime();
        Timestamp t3 = new Timestamp(date3.getTime());
        String previousMonth = DateUtil.getCurrentServerTimeByRemoteTimestamp(t3);

        LoginResponseDTO user = UsersDAO.getuserById(userId);
        String outlets = user.getOutletAccess();

        ReportDTO dailyReportDTO = feedbackDAO.getDailyReport(clientId,date,outlets, previousDate, currentDate);
        List<ReportData> dailyOutletReport = feedbackDAO.getOutletReport(clientId,date,outlets, previousDate, currentDate);
        dailyReportDTO.setOutlets(dailyOutletReport);

        ReportDTO monthlyReportDTO = feedbackDAO.getDailyReport(clientId,date,outlets, previousMonth, currentDate);
        List<ReportData> monthlyOutletReport = feedbackDAO.getOutletReport(clientId,date,outlets, previousMonth, currentDate);
        monthlyReportDTO.setOutlets(monthlyOutletReport);
        dailyReportDTO.setUserName(user.getName());

        summaryResponse.setMonthlyBillCount(monthlyReportDTO.getMonthlyBillCount());
        summaryResponse.setDailyBillCount(dailyReportDTO.getDailyBillCount());
        summaryResponse.setDailyFeedback(dailyReportDTO.getTotalCount());
        summaryResponse.setAvgDailyFeedback(calAverage(dailyReportDTO.getTotalCount(), dailyReportDTO.getDailyBillCount()));
        summaryResponse.setAvgMonthlyFeedback(calAverage(monthlyReportDTO.getTotalCount(), monthlyReportDTO.getMonthlyBillCount()));
        summaryResponse.setMonthlyFeedback(monthlyReportDTO.getTotalCount());
        summaryResponse.setDailyNegativeCount(dailyReportDTO.getNegativeCount());
        summaryResponse.setMonthlyNegativeCount(monthlyReportDTO.getNegativeCount());
        summaryResponse.setDailyUnAddressedCount(dailyReportDTO.getUnAddressedCount());
        summaryResponse.setMonthlyUnAddressedCount(monthlyReportDTO.getUnAddressedCount());
        summaryResponse.setAvgDailyUnaddressed(calAverage(dailyReportDTO.getUnAddressedCount(), dailyReportDTO.getNegativeCount()));
        summaryResponse.setAvgMonthlyUnaddressed(calAverage(monthlyReportDTO.getUnAddressedCount(), monthlyReportDTO.getNegativeCount()));

        List<OutletSummery> outletsdata = new ArrayList<OutletSummery>();
        for (ReportData dailyData :  dailyReportDTO.getOutlets()){
            ReportData monthlyData= new ReportData();
            for(ReportData data : monthlyReportDTO.getOutlets()) {
                if(dailyData.getStoreId().equals(data.getStoreId())) {
                    monthlyData = data;
                }
            }
            OutletSummery outletSummery = new OutletSummery();
            outletSummery.setCity(dailyData.getCity());
            outletSummery.setOutletName(dailyData.getStoreId());
            outletSummery.setMonthlyBillCount(monthlyData.getMonthlyBillCount());
            outletSummery.setDailyBillCount(dailyData.getDailyBillCount());
            outletSummery.setDailyFeedback(dailyData.getTotalCount());
            outletSummery.setMonthlyFeedback(monthlyData.getTotalCount());
            outletSummery.setAvgCount(calAverage(monthlyData.getTotalCount(), monthlyData.getMonthlyBillCount()));
            outletSummery.setDailyNegativeCount(dailyData.getNegativeCount());
            outletSummery.setMonthlyNegativeCount(monthlyData.getNegativeCount());
            outletSummery.setDailyUnAddressedCount(dailyData.getUnAddressedCount());
            outletSummery.setMonthlyUnAddressedCount(monthlyData.getUnAddressedCount());
            outletsdata.add(outletSummery);
        }
        summaryResponse.setOutlets(outletsdata);
        return summaryResponse;
    }

    public static float calAverage(int a, int b) {

        float result;
        if (b > 0) {
            result = (float) a / b;
            result = result * 100;
            result = Math.round(result * 100) / 100f;
        } else {
            result = 0;
        }
        return result;
    }
}



