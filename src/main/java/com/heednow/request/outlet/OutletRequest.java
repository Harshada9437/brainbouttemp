package com.heednow.request.outlet;

/**
 * Created by System-3 on 4/3/2017.
 */
public class OutletRequest {
    private int id;
    private String outletDesc;
    private String shortDesc;
    private int clusterId;
    private int regionId;
    private int companyId;
    private int groupId;
    private String posStoreId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletDesc() {
        return outletDesc;
    }

    public void setOutletDesc(String outletDesc) {
        this.outletDesc = outletDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPosStoreId() {
        return posStoreId;
    }

    public void setPosStoreId(String posStoreId) {
        this.posStoreId = posStoreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutletRequest that = (OutletRequest) o;

        if (id != that.id) return false;
        if (clusterId != that.clusterId) return false;
        if (regionId != that.regionId) return false;
        if (companyId != that.companyId) return false;
        if (groupId != that.groupId) return false;
        if (!outletDesc.equals(that.outletDesc)) return false;
        if (!shortDesc.equals(that.shortDesc)) return false;
        return posStoreId.equals(that.posStoreId);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + outletDesc.hashCode();
        result = 31 * result + shortDesc.hashCode();
        result = 31 * result + clusterId;
        result = 31 * result + regionId;
        result = 31 * result + companyId;
        result = 31 * result + groupId;
        result = 31 * result + posStoreId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OutletRequest{" +
                "id=" + id +
                ", outletDesc='" + outletDesc + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", clusterId=" + clusterId +
                ", regionId=" + regionId +
                ", companyId=" + companyId +
                ", groupId=" + groupId +
                ", posStoreId='" + posStoreId + '\'' +
                '}';
    }
}
