package com.heednow.request.cluster;

/**
 * Created by System-3 on 4/3/2017.
 */
public class ClusterRequest {
    private String clusterDesc;
    private String clustersDesc;
    private int regionId;

    public String getClusterDesc() {
        return clusterDesc;
    }

    public void setClusterDesc(String clusterDesc) {
        this.clusterDesc = clusterDesc;
    }

    public String getClustersDesc() {
        return clustersDesc;
    }

    public void setClustersDesc(String clustersDesc) {
        this.clustersDesc = clustersDesc;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClusterRequest that = (ClusterRequest) o;

        if (regionId != that.regionId) return false;
        if (!clusterDesc.equals(that.clusterDesc)) return false;
        return clustersDesc.equals(that.clustersDesc);
    }

    @Override
    public int hashCode() {
        int result = regionId;
        result = 31 * result + clusterDesc.hashCode();
        result = 31 * result + clustersDesc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ClusterRequest{" +
                "regionId=" + regionId +
                ", clusterDesc='" + clusterDesc + '\'' +
                ", clustersDesc='" + clustersDesc + '\'' +
                '}';
    }
}
