package com.heednow.response.group;

import com.heednow.response.util.GenericResponse;

/**
 * Created by System-2 on 4/4/2017.
 */
public class GetGroupResponse implements GenericResponse {
    private int id;
    private String desc;
    private String shortDesc;
    private String message;
    private String messageType;

    public GetGroupResponse(int id, String desc, String shortDesc) {
        this.id = id;
        this.desc = desc;
        this.shortDesc = shortDesc;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setId(int id) {
        this.id = id;
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
        return "GetGroupResponse{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
