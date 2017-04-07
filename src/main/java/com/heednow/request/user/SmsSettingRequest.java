package com.heednow.request.user;

/**
 * Created by System-2 on 2/7/2017.
 */
public class SmsSettingRequest {
    private String api;
    private String name;
    private String senderId;
    private String campaign;
    private String countryCode;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmsSettingRequest that = (SmsSettingRequest) o;

        if (!api.equals(that.api)) return false;
        if (!name.equals(that.name)) return false;
        if (!senderId.equals(that.senderId)) return false;
        if (!campaign.equals(that.campaign)) return false;
        return countryCode.equals(that.countryCode);
    }

    @Override
    public int hashCode() {
        int result = api.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + senderId.hashCode();
        result = 31 * result + campaign.hashCode();
        result = 31 * result + countryCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SmsSettingRequest{" +
                "api='" + api + '\'' +
                ", name='" + name + '\'' +
                ", senderId='" + senderId + '\'' +
                ", campaign='" + campaign + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
