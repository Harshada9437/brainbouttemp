package com.heednow.response.user;

/**
 * Created by System-2 on 2/7/2017.
 */
public class SmsSettingResponse {
    private int id;
    private String api;
    private String senderId;
    private String name;
    private String campaign;
    private String countryCode;

    public SmsSettingResponse(int id, String api,String name, String senderId, String campaign, String countryCode) {
        this.id = id;
        this.api = api;
        this.name = name;
        this.senderId = senderId;
        this.campaign = campaign;
        this.countryCode = countryCode;
    }

    public int getId() {
        return id;
    }

    public String getApi() {
        return api;
    }

    public String getName() {
        return name;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getCampaign() {
        return campaign;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "SmsSettingResponse{" +
                "id=" + id +
                ", api='" + api + '\'' +
                ", name='" + name + '\'' +
                ", senderId='" + senderId + '\'' +
                ", campaign='" + campaign + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
