package com.heednow.response.region;



import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 4/3/2017.
 */
public class RegionResponseList implements GenericResponse {
    private List<RegionResponse> regions;
    private String messageType;
    private String message;

    public List<RegionResponse> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionResponse> regions) {
        this.regions = regions;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegionResponseList{" +
                "clusters=" + regions +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
