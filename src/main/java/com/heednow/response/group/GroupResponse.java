package com.heednow.response.group;

import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 4/4/2017.
 */
public class GroupResponse implements GenericResponse {
    private List<GroupDetail> groups;
    private String message;
    private String messageType;

    public List<GroupDetail> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDetail> groups) {
        this.groups = groups;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "GroupResponse{" +
                "groups=" + groups +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
