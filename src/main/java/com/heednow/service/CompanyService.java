package com.heednow.service;


import com.heednow.bo.CompanyRequestBO;
import com.heednow.request.company.CompanyRequest;
import com.heednow.request.company.UpdateCompanyRequest;
import com.heednow.requesthandler.CompanyRequestHandler;
import com.heednow.response.company.CompanyResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;
import com.heednow.util.UserRequestValidation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-3 on 4/5/2017.
 */
@Path("/company")
public class CompanyService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createCompanies(CompanyRequest companyRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && (UserRequestValidation.isRequestValid(auth))) {
                CompanyRequestBO companyRequestBO = new CompanyRequestBO();
                companyRequestBO.setCompanyDesc(companyRequest.getCompanyDesc());
                companyRequestBO.setCompanyShortDesc(companyRequest.getCompanyShortDesc());
                companyRequestBO.setGroupId(companyRequest.getGroupId());
                companyRequestBO.setClientId(UserRequestValidation.getClient(auth));

                MessageResponse messageResponse = new MessageResponse();
                CompanyRequestHandler companyRequestHandler = new CompanyRequestHandler();

                Boolean isCreated = companyRequestHandler.createCompanies(companyRequestBO);
                if (isCreated) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Company created");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Company creation failed");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Company creation failed");
        }

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{group_id}")
    public Response getCompaniesList(@PathParam("group_id") int groupId,@HeaderParam("Auth") String auth) {
        try {
            if (auth != null && (UserRequestValidation.isRequestValid(auth))) {
                CompanyRequestHandler companyRequestHandler = new CompanyRequestHandler();
                CompanyResponseList clusterResponseList = new CompanyResponseList();
                clusterResponseList.setClusters(companyRequestHandler.getCompaniesList(groupId,UserRequestValidation.getClient(auth)));
                return ResponseGenerator.generateSuccessResponse(clusterResponseList, "List of Companies.");
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
    public Response updateCompanies(UpdateCompanyRequest companyRequest, @HeaderParam("Auth") String auth) {
        try {
            if (auth != null && (UserRequestValidation.isRequestValid(auth))) {
                CompanyRequestBO companyRequestBO = new CompanyRequestBO();
                companyRequestBO.setId(companyRequest.getId());
                companyRequestBO.setCompanyDesc(companyRequest.getCompanyDesc());
                companyRequestBO.setCompanyShortDesc(companyRequest.getCompanyShortDesc());
                companyRequestBO.setGroupId(companyRequest.getGroupId());

                CompanyRequestHandler companyRequestHandler = new CompanyRequestHandler();
                MessageResponse messageResponse = new MessageResponse();

                if (companyRequestHandler.updateCompanies(companyRequestBO)) {
                    return ResponseGenerator.generateSuccessResponse(messageResponse, "Company updated successfully");
                } else {
                    return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the company.");
                }
            } else {
                return ResponseGenerator.generateResponse(UserRequestValidation.getUnautheticatedResponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Unable to update the compnay.");
        }


    }
}
