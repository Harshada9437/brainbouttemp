package com.heednow.response.user;

import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-3 on 2/8/2017.
 */

    public class MenuResponseList implements GenericResponse{
        private List<MenuResponse> menus;
        private String messageType;
        private String message;

    public List<MenuResponse> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuResponse> menus) {
        this.menus = menus;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MenuResponseList{" +
                "menus=" + menus +
                ", messageType='" + messageType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
