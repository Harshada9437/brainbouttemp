package com.heednow.request.outlet;

/**
 * Created by System-2 on 12/20/2016.
 */
public class AssignTemplateRequest
{
    private int templateId;
    private String fromDate;
    private String toDate;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssignTemplateRequest that = (AssignTemplateRequest) o;

        if (templateId != that.templateId) return false;
        if (!fromDate.equals(that.fromDate)) return false;
        return toDate.equals(that.toDate);
    }

    @Override
    public int hashCode() {
        int result = templateId;
        result = 31 * result + fromDate.hashCode();
        result = 31 * result + toDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AssignTemplateRequest{" +
                "templateId=" + templateId +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
