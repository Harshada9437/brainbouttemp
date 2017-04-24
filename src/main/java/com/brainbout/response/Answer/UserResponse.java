package com.brainbout.response.Answer;

import com.brainbout.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/19/2016.
 */
public class UserResponse implements GenericResponse
{
    List<UserResponseList> users;
    private String message;
    private String messageType;


    public List<UserResponseList> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseList> users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType=messageType;
    }

    @Override
    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "users=" + users +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}

