package com.heednow.dto;

/**
 * Created by System-3 on 4/3/2017.
 */
public class ClusterDTO {
    private int id;
    private String clusterDesc;
    private String clustersDesc;
    private int regionId;
    private int clientId;


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

        ClusterDTO that = (ClusterDTO) o;

        if (id != that.id) return false;
        if (regionId != that.regionId) return false;
        if (clientId != that.clientId) return false;
        if (!clusterDesc.equals(that.clusterDesc)) return false;
        return clustersDesc.equals(that.clustersDesc);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + clusterDesc.hashCode();
        result = 31 * result + clustersDesc.hashCode();
        result = 31 * result + regionId;
        result = 31 * result + clientId;
        return result;
    }

    @Override
    public String toString() {
        return "ClusterDTO{" +
                "id=" + id +
                ", clusterDesc='" + clusterDesc + '\'' +
                ", clustersDesc='" + clustersDesc + '\'' +
                ", regionId=" + regionId +
                ", clientId=" + clientId +
                '}';
    }
}
