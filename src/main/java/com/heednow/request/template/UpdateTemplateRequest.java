package com.heednow.request.template;

/**
 * Created by System1 on 10/4/2016.
 */
public class UpdateTemplateRequest {
    private int id;
    private String templateDesc;
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateTemplateRequest that = (UpdateTemplateRequest) o;

        if (id != that.id) return false;
        if (!templateDesc.equals(that.templateDesc)) return false;
        return status.equals(that.status);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + templateDesc.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UpdateTemplateRequest{" +
                "id=" + id +
                ", templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
