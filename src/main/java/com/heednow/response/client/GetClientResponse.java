package com.heednow.response.client;

import com.heednow.response.util.GenericResponse;

/**
 * Created by System-2 on 3/9/2017.
 */
public class GetClientResponse implements GenericResponse{
    private int id;
    private int noOfOutlets;
    private int noOfDevices;
    private String name;
    private String email;
    private String mobile;
    private String location;
    private String clientId;
    private String status;
    private String message;
    private String messageType;

    public GetClientResponse(int id, int noOfOutlets, int noOfDevices, String name, String email, String mobile, String location, String clientId, String status) {
        this.id = id;
        this.noOfOutlets = noOfOutlets;
        this.noOfDevices = noOfDevices;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.location = location;
        this.clientId = clientId;
        this.status = status;
    }

    public int getNoOfOutlets() {
        return noOfOutlets;
    }

    public int getNoOfDevices() {
        return noOfDevices;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLocation() {
        return location;
    }

    public String getClientId() {
        return clientId;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "GetClientResponse{" +
                "id=" + id +
                ", noOfOutlets=" + noOfOutlets +
                ", noOfDevices=" + noOfDevices +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", location='" + location + '\'' +
                ", clientId='" + clientId + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
