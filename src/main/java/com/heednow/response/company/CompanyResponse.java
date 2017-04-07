package com.heednow.response.company;

/**
 * Created by System-3 on 4/5/2017.
 */
public class CompanyResponse {
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
    public String toString() {
        return "CompanyResponse{" +
                "id=" + id +
                ", companyDesc='" + companyDesc + '\'' +
                ", companyShortDesc='" + companyShortDesc + '\'' +
                ", groupId=" + groupId +
                ", clientId=" + clientId +
                '}';
    }
}
