package com.heednow.requesthandler;

import com.heednow.bo.SettingRequestBO;
import com.heednow.bo.SmsSettingRequestBO;
import com.heednow.bo.UpdateSettingRequestBO;
import com.heednow.dao.FeedbackDAO;
import com.heednow.dao.Sync.*;
import com.heednow.dto.request.*;
import com.heednow.response.user.SettingResponse;
import com.heednow.response.user.SmsSettingResponse;
import com.heednow.util.DateUtil;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by System-2 on 1/9/2017.
 */
public class SyncRequestHandler {

    public Boolean saveSetting(SettingRequestBO settingRequestBO) throws SQLException {
        SettingRequestDTO settingRequestDTO = new SettingRequestDTO();
        Boolean isProcessed;
        settingRequestDTO.setSmsTemplate(settingRequestBO.getSmsTemplate());
        settingRequestDTO.setArchiveTime(settingRequestBO.getArchiveTime());
        settingRequestDTO.setReportTime(settingRequestBO.getReportTime());
        settingRequestDTO.setClientId(settingRequestBO.getClientId());
        settingRequestDTO.setEmailTemplate(settingRequestBO.getEmailTemplate());
        settingRequestDTO.setVersionCode(settingRequestBO.getVersionCode());
        settingRequestDTO.setVersionName(settingRequestBO.getVersionName());
        SettingRequestDTO settingRequestDTO1 = SmsDAO.fetchSettings(settingRequestBO.getClientId());
        if (settingRequestDTO1 == null) {
            isProcessed = SmsDAO.saveSetting(settingRequestDTO, settingRequestBO.getClientId());
        } else {
            isProcessed = SmsDAO.updateSetting(settingRequestDTO, settingRequestBO.getClientId());
        }
        return isProcessed;
    }

    public SettingResponse fetchSettings(int clientId) throws SQLException {
        SettingResponse settingResponse = new SettingResponse();
        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(clientId);
        settingResponse.setId(settingRequestDTO.getId());
        settingResponse.setSmsTemplate(settingRequestDTO.getSmsTemplate());
        settingResponse.setArchiveTime(settingRequestDTO.getArchiveTime());
        settingResponse.setReportTime(settingRequestDTO.getReportTime());
        settingResponse.setClientId(settingRequestDTO.getClientId());
        settingResponse.setEmailTemplate(settingRequestDTO.getEmailTemplate());
        settingResponse.setVersionCode(settingRequestDTO.getVersionCode());
        settingResponse.setVersionName(settingRequestDTO.getVersionName());
        return settingResponse;
    }

    public Integer saveSmsSettings(SmsSettingRequestBO settingRequestBO) throws SQLException {
        SmsSettingDTO settingRequestDTO = new SmsSettingDTO();
        settingRequestDTO.setApi(settingRequestBO.getApi());
        settingRequestDTO.setSenderId(settingRequestBO.getSenderId());
        settingRequestDTO.setCampaign(settingRequestBO.getCampaign());
        settingRequestDTO.setCountryCode(settingRequestBO.getCountryCode());
        settingRequestDTO.setName(settingRequestBO.getName());
        settingRequestDTO.setClientId(settingRequestBO.getClientId());
        Integer id = SmsDAO.saveSmsSettings(settingRequestDTO);
        return id;
    }

    public List<SmsSettingResponse> fetchSmsSettings( int clientId) throws SQLException {
        List<SmsSettingResponse> responses = new ArrayList<SmsSettingResponse>();
        List<SmsSettingDTO> smsSettingDTO = SmsDAO.fetchSmsSettings(clientId);

        for (SmsSettingDTO dto : smsSettingDTO) {
            SmsSettingResponse response = new SmsSettingResponse(
                    dto.getId(),
                    dto.getApi(),
                    dto.getName(),
                    dto.getSenderId(),
                    dto.getCampaign(),
                    dto.getCountryCode());
            responses.add(response);
        }
        return responses;
    }

    public void updateSmsSettings(UpdateSettingRequestBO settingRequestBO) throws SQLException {
        SmsSettingDTO settingRequestDTO = new SmsSettingDTO();
        settingRequestDTO.setApi(settingRequestBO.getApi());
        settingRequestDTO.setSenderId(settingRequestBO.getSenderId());
        settingRequestDTO.setCampaign(settingRequestBO.getCampaign());
        settingRequestDTO.setCountryCode(settingRequestBO.getCountryCode());
        settingRequestDTO.setName(settingRequestBO.getName());
        settingRequestDTO.setId(settingRequestBO.getId());
        SmsDAO.updateSmsSettings(settingRequestDTO);
    }

    public void archiveFeedback() throws SQLException {

        SyncDAO syncDAO = new SyncDAO();
        String current = "", previous = "", table = "";

        SettingRequestDTO settingRequestDTO = SmsDAO.fetchSettings(1);
        Time archiveTime = settingRequestDTO.getArchiveTime();

        final Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        Timestamp timestamp1 = new Timestamp(today.getTime());
        String currentDay = DateUtil.format(timestamp1, "yyyy-MM-dd");

        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.SECOND, archiveTime.getSeconds());
        c.set(Calendar.MINUTE, archiveTime.getMinutes());
        c.set(Calendar.HOUR_OF_DAY, archiveTime.getHours());
        Date lastDate = c.getTime();
        Timestamp timestamp2 = new Timestamp(lastDate.getTime());
        String lastDay = DateUtil.format(timestamp2, "yyyy-MM-dd");

        if (currentDay.equals(lastDay) && timestamp1.before(timestamp2)) {
            c.setTime(today);
            c.add(Calendar.MONTH, -1);
            Date date2 = c.getTime();
            Timestamp t2 = new Timestamp(date2.getTime());
            previous = DateUtil.format(t2, "MM-yyyy");
            table = syncDAO.createArchive(previous);
            current = previous;
        } else {
            current = DateUtil.format(timestamp1, "MM-yyyy");
            table = syncDAO.createArchive(current);
        }


        int id = syncDAO.getMaxId(current);
        if (id == 0) {
            c.setTime(today);
            c.add(Calendar.MONTH, -1);
            Date date2 = c.getTime();
            Timestamp t2 = new Timestamp(date2.getTime());
            previous = DateUtil.format(t2, "MM-yyyy");
            id = syncDAO.getMaxId(previous);
        }

        List<FeedbackDTO> feedbackResponses = getfeedback(id);

        syncDAO.addArchiveData(table, feedbackResponses);


    }

    public List<FeedbackDTO> getfeedback(int id) throws SQLException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        List<FeedbackDTO> feedbackRequestDTOS = feedbackDAO.getArchiveData(id);
        for (FeedbackDTO feedbackRequestDTO : feedbackRequestDTOS) {
            if (feedbackRequestDTO.getIsAddressed() > 0) {
                feedbackRequestDTO.setIsAddressed(1);
            }
            if (feedbackRequestDTO.getThreshold() != null && !feedbackRequestDTO.getThreshold().equals("")) {
                if ((feedbackRequestDTO.getQuestionType() == '2' || feedbackRequestDTO.getQuestionType() == '3') && feedbackRequestDTO.getRating() != 0) {
                    int ans = feedbackRequestDTO.getAnsRating() / feedbackRequestDTO.getWeightage();
                    int weightage = feedbackRequestDTO.getRating() / ans;
                    if (weightage <= Integer.parseInt(feedbackRequestDTO.getThreshold())) {
                        feedbackRequestDTO.setIsPoor(1);
                    } else {
                        feedbackRequestDTO.setIsPoor(0);
                    }
                }
                if ((feedbackRequestDTO.getQuestionType() == '1' || feedbackRequestDTO.getQuestionType() == '5' ||
                        feedbackRequestDTO.getQuestionType() == '6')) {
                    if (feedbackRequestDTO.getThreshold().equals("1")) {
                        feedbackRequestDTO.setIsPoor(1);
                    } else {
                        feedbackRequestDTO.setIsPoor(0);
                    }
                }
            } else {
                feedbackRequestDTO.setIsPoor(0);
            }
        }
        return feedbackRequestDTOS;
    }

}
