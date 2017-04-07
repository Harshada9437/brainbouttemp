package com.heednow.request.cluster;

/**
 * Created by System-2 on 4/6/2017.
 */
public class UpdateClusterRequest {
    private int id;
    private String clusterDesc;
    private String clustersDesc;
    private int regionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

        UpdateClusterRequest that = (UpdateClusterRequest) o;

        if (id != that.id) return false;
        if (regionId != that.regionId) return false;
        if (clusterDesc != null ? !clusterDesc.equals(that.clusterDesc) : that.clusterDesc != null) return false;
        return clustersDesc != null ? clustersDesc.equals(that.clustersDesc) : that.clustersDesc == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (clusterDesc != null ? clusterDesc.hashCode() : 0);
        result = 31 * result + (clustersDesc != null ? clustersDesc.hashCode() : 0);
        result = 31 * result + regionId;
        return result;
    }

    @Override
    public String toString() {
        return "UpdateClusterRequest{" +
                "id=" + id +
                ", clusterDesc='" + clusterDesc + '\'' +
                ", clustersDesc='" + clustersDesc + '\'' +
                ", regionId=" + regionId +
                '}';
    }
}
