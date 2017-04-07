package com.heednow.bo;

/**
 * Created by System-3 on 4/3/2017.
 */
public class RegionRequestBO {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionRequestBO that = (RegionRequestBO) o;

        if (id != that.id) return false;
        if (companyId != that.companyId) return false;
        if (clientId != that.clientId) return false;
        if (!regionDesc.equals(that.regionDesc)) return false;
        return regionsDesc.equals(that.regionsDesc);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + regionDesc.hashCode();
        result = 31 * result + regionsDesc.hashCode();
        result = 31 * result + companyId;
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "RegionRequestBO{" +
                "id=" + id +
                ", regionDesc='" + regionDesc + '\'' +
                ", regionsDesc='" + regionsDesc + '\'' +
                ", companyId=" + companyId +
                ", clientId=" + clientId +
                '}';
    }
}
