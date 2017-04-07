package com.heednow.requesthandler;

import com.heednow.bo.ClusterRequestBO;
import com.heednow.dao.Sync.ClusterDAO;
import com.heednow.response.cluster.ClusterResponse;
import com.heednow.sync.Cluster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-3 on 4/3/2017.
 */
public class ClusterRequestHandler {
    public int createClusters(ClusterRequestBO clusterRequestBO) throws SQLException {
        ClusterDAO clusterDAO = new ClusterDAO();
        int id = clusterDAO.createCluster(buildClusterDTOFromBO(clusterRequestBO));
        return id;
    }

    private Cluster buildClusterDTOFromBO(ClusterRequestBO clusterRequestBO) {
        Cluster clusterDTO = new Cluster();
        clusterDTO.setDesc(clusterRequestBO.getClusterDesc());
        clusterDTO.setShortDesc(clusterRequestBO.getClustersDesc());
        clusterDTO.setRegionId(clusterRequestBO.getRegionId());
        clusterDTO.setId(clusterRequestBO.getClientId());
        return clusterDTO;
    }


    public List<ClusterResponse> getClustersList(int regionId,int clientId) throws SQLException {
        ClusterDAO clusterDAO = new ClusterDAO();
        List<ClusterResponse> clusterList = new ArrayList<ClusterResponse>();

        List<Cluster> clusterRequestDTOList = clusterDAO.getClusters(regionId,clientId);

        for (Cluster clusterDTO : clusterRequestDTOList) {
            ClusterResponse clusterResponse = new ClusterResponse();
            clusterResponse.setId(clusterDTO.getId());
            clusterResponse.setClusterDesc(clusterDTO.getDesc());
            clusterResponse.setClustersDesc(clusterDTO.getShortDesc());
            clusterResponse.setRegionId(clusterDTO.getRegionId());
            clusterResponse.setClientId(clusterDTO.getClientId());
            clusterList.add(clusterResponse);
        }

        return clusterList;
    }


    public boolean updateClusters(ClusterRequestBO clusterRequestBO) throws SQLException {

        ClusterDAO clusterDAO = new ClusterDAO();
        Boolean isProcessed = clusterDAO.updateCluster(buildDTOFromBO(clusterRequestBO));
        return isProcessed;
    }

    private Cluster buildDTOFromBO(ClusterRequestBO clusterRequestBO) {
        Cluster clusterDTO = new Cluster();
        clusterDTO.setId(clusterRequestBO.getId());
        clusterDTO.setDesc(clusterRequestBO.getClusterDesc());
        clusterDTO.setShortDesc(clusterRequestBO.getClustersDesc());
        clusterDTO.setRegionId(clusterRequestBO.getRegionId());
        return clusterDTO;
    }

}
