package com.heednow.service;

import com.heednow.bo.ClusterRequestBO;
import com.heednow.request.cluster.ClusterRequest;
import com.heednow.request.cluster.UpdateClusterRequest;
import com.heednow.requesthandler.ClusterRequestHandler;
import com.heednow.response.cluster.ClusterResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by System-3 on 4/3/2017.
 */
@Path("/cluster")
public class ClusterService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createClusters(ClusterRequest clusterRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ClusterRequestBO clusterRequestBO = new ClusterRequestBO();
                clusterRequestBO.setClusterDesc(clusterRequest.getClusterDesc());
                clusterRequestBO.setClustersDesc(clusterRequest.getClustersDesc());
                clusterRequestBO.setRegionId(clusterRequest.getRegionId());
                clusterRequestBO.setClientId(UserRequestValidation.getClient(auth));
                MessageResponse messageResponse = new MessageResponse();
                ClusterRequestHandler clusterRequestHandler = new ClusterRequestHandler();
                int id = clusterRequestHandler.createClusters(clusterRequestBO);
                if (id > 0) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(id));
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Cluster creation failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Cluster creation failed");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{region_id}")
    public Response getClustersList(@PathParam("region_id") int regionId,@HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ClusterRequestHandler clusterRequestHandler = new ClusterRequestHandler();
                ClusterResponseList clusterResponseList = new ClusterResponseList();
                int clientId = UserRequestValidation.getClient(auth);
                clusterResponseList.setClusters(clusterRequestHandler.getClustersList(regionId,clientId));
                return ResponseGenerator.generateSuccessResponse(clusterResponseList, "List of clusters.");
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve.");
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateClusters(UpdateClusterRequest clusterRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                ClusterRequestBO clusterRequestBO = new ClusterRequestBO();
                clusterRequestBO.setId(clusterRequest.getId());
                clusterRequestBO.setClusterDesc(clusterRequest.getClusterDesc());
                clusterRequestBO.setClustersDesc(clusterRequest.getClustersDesc());
                clusterRequestBO.setRegionId(clusterRequest.getRegionId());

                ClusterRequestHandler clusterRequestHandler = new ClusterRequestHandler();
                MessageResponse messageResponse = new MessageResponse();
                if (clusterRequestHandler.updateClusters(clusterRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Cluster updated successfully");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the cluster.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the cluster.");
        }
    }
}



