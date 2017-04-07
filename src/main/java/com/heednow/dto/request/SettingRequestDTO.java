package com.heednow.dto.request;

import java.sql.Time;

/**
 * Created by System-2 on 2/6/2017.
 */
public class SettingRequestDTO {
    private int id;
    private String smsTemplate;
    private Time archiveTime;
    private Time reportTime;
    private int clientId;
    private String emailTemplate;
    private int versionCode;
    private String versionName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public Time getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(Time archiveTime) {
        this.archiveTime = archiveTime;
    }

    public Time getReportTime() {
        return reportTime;
    }

    public void setReportTime(Time reportTime) {
        this.reportTime = reportTime;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingRequestDTO that = (SettingRequestDTO) o;

        if (id != that.id) return false;
        if (clientId != that.clientId) return false;
        if (versionCode != that.versionCode) return false;
        if (!smsTemplate.equals(that.smsTemplate)) return false;
        if (!archiveTime.equals(that.archiveTime)) return false;
        if (!reportTime.equals(that.reportTime)) return false;
        if (!emailTemplate.equals(that.emailTemplate)) return false;
        return versionName.equals(that.versionName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + smsTemplate.hashCode();
        result = 31 * result + archiveTime.hashCode();
        result = 31 * result + reportTime.hashCode();
        result = 31 * result + clientId;
        result = 31 * result + emailTemplate.hashCode();
        result = 31 * result + versionCode;
        result = 31 * result + versionName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SettingRequestDTO{" +
                "id=" + id +
                ", smsTemplate='" + smsTemplate + '\'' +
                ", archiveTime=" + archiveTime +
                ", reportTime=" + reportTime +
                ", clientId=" + clientId +
                ", emailTemplate='" + emailTemplate + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}
