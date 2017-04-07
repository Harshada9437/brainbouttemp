package com.heednow.response.user;

import com.heednow.response.util.GenericResponse;

/**
 * Created by System-3 on 2/8/2017.
 */
public class RoleByIdResponse implements GenericResponse {
    private int roleId;
    private int isAll;
    private String name;
    private String menuAccess;
    private String outletAccess;
    private String message;
    private String messageType;

    public int getIsAll() {
        return isAll;
    }

    public void setIsAll(int isAll) {
        this.isAll = isAll;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuAccess() {
        return menuAccess;
    }

    public void setMenuAccess(String menuAccess) {
        this.menuAccess = menuAccess;
    }

    public String getOutletAccess() {
        return outletAccess;
    }

    public void setOutletAccess(String outletAccess) {
        this.outletAccess = outletAccess;
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
        return "RoleByIdResponse{" +
                "roleId=" + roleId +
                ", isAll=" + isAll +
                ", name='" + name + '\'' +
                ", menuAccess='" + menuAccess + '\'' +
                ", outletAccess='" + outletAccess + '\'' +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }


}
