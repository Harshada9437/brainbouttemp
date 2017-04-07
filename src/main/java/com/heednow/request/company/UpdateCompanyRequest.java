package com.heednow.request.company;

/**
 * Created by System-2 on 4/6/2017.
 */
public class UpdateCompanyRequest {
    private int id;
    private String companyDesc;
    private String companyShortDesc;
    private int groupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getCompanyShortDesc() {
        return companyShortDesc;
    }

    public void setCompanyShortDesc(String companyShortDesc) {
        this.companyShortDesc = companyShortDesc;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateCompanyRequest that = (UpdateCompanyRequest) o;

        if (id != that.id) return false;
        if (groupId != that.groupId) return false;
        if (companyDesc != null ? !companyDesc.equals(that.companyDesc) : that.companyDesc != null) return false;
        return companyShortDesc != null ? companyShortDesc.equals(that.companyShortDesc) : that.companyShortDesc == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (companyDesc != null ? companyDesc.hashCode() : 0);
        result = 31 * result + (companyShortDesc != null ? companyShortDesc.hashCode() : 0);
        result = 31 * result + groupId;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateCompanyRequest{" +
                "id=" + id +
                ", companyDesc='" + companyDesc + '\'' +
                ", companyShortDesc='" + companyShortDesc + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
