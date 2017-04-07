package com.heednow.response.cluster;

import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 4/3/2017.
 */
public class ClusterResponseList implements GenericResponse {
    private List<ClusterResponse> clusters;
    private String messageType;
    private String message;

    public List<ClusterResponse> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterResponse> clusters) {
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
        return "ClusterResponseList{" +
                "clusters=" + clusters +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
