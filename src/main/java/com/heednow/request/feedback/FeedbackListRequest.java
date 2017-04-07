package com.heednow.request.feedback;

import java.util.List;

/**
 * Created by System-2 on 1/25/2017.
 */
public class FeedbackListRequest {
    private String fromDate;
    private String toDate;
    private List<Integer> outletId;
    private String tableNo;
    private int userId;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<Integer> getOutletId() {
        return outletId;
    }

    public void setOutletId(List<Integer> outletId) {
        this.outletId = outletId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackListRequest that = (FeedbackListRequest) o;

        if (userId != that.userId) return false;
        if (!fromDate.equals(that.fromDate)) return false;
        if (!toDate.equals(that.toDate)) return false;
        if (!outletId.equals(that.outletId)) return false;
        return tableNo.equals(that.tableNo);
    }

    @Override
    public int hashCode() {
        int result = fromDate.hashCode();
        result = 31 * result + toDate.hashCode();
        result = 31 * result + outletId.hashCode();
        result = 31 * result + tableNo.hashCode();
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackListRequest{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", outletId=" + outletId +
                ", tableNo='" + tableNo + '\'' +
                ", userId=" + userId +
                '}';
    }
}
