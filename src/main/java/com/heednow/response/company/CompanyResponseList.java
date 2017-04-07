package com.heednow.response.company;

import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 4/5/2017.
 */
public class CompanyResponseList implements GenericResponse{
    private List<CompanyResponse> clusters;
    private String messageType;
    private String message;

    public List<CompanyResponse> getClusters() {
        return clusters;
    }

    public void setClusters(List<CompanyResponse> clusters) {
        this.clusters = clusters;
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
        return "CompanyResponseList{" +
                "clusters=" + clusters +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
