package com.heednow.response.outlet;

import com.heednow.response.util.GenericResponse;

import java.util.List;

/**
 * Created by System-2 on 12/20/2016.
 */
public class OutletResponse implements GenericResponse
{
    List<OutletResponseL> outletResponseList;
    private String message;
    private String messageType;

    public List<OutletResponseL> getOutletResponseList() {
        return outletResponseList;
    }

    public void setOutletResponseList(List<OutletResponseL> outletResponseList) {
        this.outletResponseList = outletResponseList;
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
        return "OutletResponse{" +
                "outletResponseList=" + outletResponseList +
                ", message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
