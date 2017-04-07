package com.heednow.request.company;

/**
 * Created by System-3 on 4/5/2017.
 */
public class CompanyRequest {
    private String companyDesc;
    private String companyShortDesc;
    private int groupId;

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

        CompanyRequest that = (CompanyRequest) o;

        if (groupId != that.groupId) return false;
        if (!companyDesc.equals(that.companyDesc)) return false;
        return companyShortDesc.equals(that.companyShortDesc);
    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + companyDesc.hashCode();
        result = 31 * result + companyShortDesc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CompanyRequest{" +
                "groupId=" + groupId +
                ", companyDesc='" + companyDesc + '\'' +
                ", companyShortDesc='" + companyShortDesc + '\'' +
                '}';
    }
}
