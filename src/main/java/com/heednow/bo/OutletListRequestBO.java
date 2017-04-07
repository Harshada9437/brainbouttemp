package com.heednow.bo;

import java.util.List;

/**
 * Created by System-2 on 2/9/2017.
 */
public class OutletListRequestBO {
    private int userId;
    private String outletId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutletListRequestBO that = (OutletListRequestBO) o;

        if (userId != that.userId) return false;
        return outletId != null ? outletId.equals(that.outletId) : that.outletId == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (outletId != null ? outletId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OutletListRequestBO{" +
                "userId=" + userId +
                ", outletId='" + outletId + "\'"+
                '}';
    }
}
