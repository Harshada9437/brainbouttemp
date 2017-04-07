package com.heednow.response.client;

import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 3/9/2017.
 */
public class ClientResponse implements GenericResponse{
    private List<ClientDetail> clients;
    private String message;
    private String messageType;

    public List<ClientDetail> getClients() {
        return clients;
    }

    public void setClients(List<ClientDetail> clients) {
        this.clients = clients;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "clients=" + clients +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
