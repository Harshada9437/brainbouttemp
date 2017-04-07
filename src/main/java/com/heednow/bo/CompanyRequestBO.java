package com.heednow.bo;

/**
 * Created by System-3 on 4/5/2017.
 */
public class CompanyRequestBO {
    private int id;
    private String companyDesc;
    private String companyShortDesc;
    private int groupId;
    private int clientId;

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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyRequestBO that = (CompanyRequestBO) o;

        if (id != that.id) return false;
        if (groupId != that.groupId) return false;
        if (clientId != that.clientId) return false;
        if (!companyDesc.equals(that.companyDesc)) return false;
        return companyShortDesc.equals(that.companyShortDesc);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + companyDesc.hashCode();
        result = 31 * result + companyShortDesc.hashCode();
        result = 31 * result + groupId;
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "CompanyRequestBO{" +
                "id=" + id +
                ", companyDesc='" + companyDesc + '\'' +
                ", companyShortDesc='" + companyShortDesc + '\'' +
                ", groupId=" + groupId +
                ", clientId=" + clientId +
                '}';
    }
}
