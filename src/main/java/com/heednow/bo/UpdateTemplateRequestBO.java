package com.heednow.bo;

/**
 * Created by System1 on 10/4/2016.
 */
public class UpdateTemplateRequestBO {
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

        UpdateTemplateRequestBO that = (UpdateTemplateRequestBO) o;

        if (id != that.id) return false;
        if (templateDesc != null ? !templateDesc.equals(that.templateDesc) : that.templateDesc != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (templateDesc != null ? templateDesc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateTemplateRequestBO{" +
                "id=" + id +
                ", templateDesc='" + templateDesc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
