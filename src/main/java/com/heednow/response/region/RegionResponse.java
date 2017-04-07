package com.heednow.response.region;

/**
 * Created by System-3 on 4/3/2017.
 */
public class RegionResponse {
    private int id;
    private String regionDesc;
    private String regionsDesc;
    private int companyId;
    private int clientId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    public String getRegionsDesc() {
        return regionsDesc;
    }

    public void setRegionsDesc(String regionsDesc) {
        this.regionsDesc = regionsDesc;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "RegionResponse{" +
                "id=" + id +
                ", regionDesc='" + regionDesc + '\'' +
                ", regionsDesc='" + regionsDesc + '\'' +
                ", companyId=" + companyId +
                ", clientId=" + clientId +
                '}';
    }
}

