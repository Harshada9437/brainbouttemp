package com.heednow.request.outlet;

/**
 * Created by System-2 on 2/9/2017.
 */
public class OutletListRequest {
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

        OutletListRequest that = (OutletListRequest) o;

        if (userId != that.userId) return false;
        return outletId.equals(that.outletId);
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + outletId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OutletListRequest{" +
                "userId=" + userId +
                ", outletId='" + outletId + '\'' +
                '}';
    }
}
