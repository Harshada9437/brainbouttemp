package com.heednow.request.region;

/**
 * Created by System-3 on 4/3/2017.
 */
public class RegionRequest {
    private String regionDesc;
    private String regionsDesc;
    private int companyId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionRequest that = (RegionRequest) o;

        if (companyId != that.companyId) return false;
        if (!regionDesc.equals(that.regionDesc)) return false;
        return regionsDesc.equals(that.regionsDesc);
    }

    @Override
    public int hashCode() {
        int result = companyId;
        result = 31 * result + regionDesc.hashCode();
        result = 31 * result + regionsDesc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RegionRequest{" +
                "companyId=" + companyId +
                ", regionDesc='" + regionDesc + '\'' +
                ", regionsDesc='" + regionsDesc + '\'' +
                '}';
    }
}
