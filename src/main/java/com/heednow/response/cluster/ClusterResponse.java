package com.heednow.response.cluster;

/**
 * Created by System-3 on 4/3/2017.
 */
public class ClusterResponse {
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

    public void setRegionId(int region_id) {
        this.regionId = region_id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int client_id) {
        this.clientId = client_id;
    }

    @Override
    public String toString() {
        return "ClusterResponse{" +
                "id=" + id +
                ", clusterDesc='" + clusterDesc + '\'' +
                ", clustersDesc='" + clustersDesc + '\'' +
                ", regionId=" + regionId +
                ", clientId=" + clientId +
                '}';
    }
}
