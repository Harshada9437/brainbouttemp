package com.heednow.service;

import com.heednow.bo.RegionRequestBO;
import com.heednow.request.region.RegionRequest;
import com.heednow.request.region.UpdateRegionRequest;
import com.heednow.requesthandler.RegionRequestHandler;
import com.heednow.response.region.RegionResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by System-3 on 4/3/2017.
 */
@Path("/region")
public class RegionService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createRegions(RegionRequest regionRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                RegionRequestBO regionRequestBO = new RegionRequestBO();
                regionRequestBO.setRegionDesc(regionRequest.getRegionDesc());
                regionRequestBO.setRegionsDesc(regionRequest.getRegionsDesc());
                regionRequestBO.setCompanyId(regionRequest.getCompanyId());
                regionRequestBO.setClientId(UserRequestValidation.getClient(auth));

                MessageResponse messageResponse = new MessageResponse();
                RegionRequestHandler regionRequestHandler = new RegionRequestHandler();

                Boolean isCreated = regionRequestHandler.createRegions(regionRequestBO);
                if (isCreated) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Region created");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Region creation failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Region creation failed");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{company_id}")
    public Response getRegionsList(@PathParam("company_id") int companyId,@HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                RegionRequestHandler regionRequestHandler = new RegionRequestHandler();
                RegionResponseList regionResponseList = new RegionResponseList();
                int clientId = UserRequestValidation.getClient(auth);
                regionResponseList.setRegions(regionRequestHandler.getRegionsList(companyId,clientId));
                return ResponseGenerator.generateSuccessResponse(regionResponseList, "List of regions.");
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
    public Response updateRegions(UpdateRegionRequest regionRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && UserRequestValidation.isRequestValid(auth)) {
                    RegionRequestBO regionRequestBO = new RegionRequestBO();
                    regionRequestBO.setId(regionRequest.getId());
                    regionRequestBO.setRegionDesc(regionRequest.getRegionDesc());
                    regionRequestBO.setRegionsDesc(regionRequest.getRegionsDesc());
                    regionRequestBO.setCompanyId(regionRequest.getCompanyId());

                    RegionRequestHandler regionRequestHandler = new RegionRequestHandler();
                    MessageResponse messageResponse = new MessageResponse();

                    if (regionRequestHandler.updateRegions(regionRequestBO)) {
                        return ResponseGenerator.generateSuccessResponse(messageResponse, "Region updated successfully");
                    } else {
                        return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the region.");
                    }
                } else{
                    return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
                }
            } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the region.");
        }
    }
}
