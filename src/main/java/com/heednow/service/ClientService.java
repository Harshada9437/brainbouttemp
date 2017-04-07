package com.heednow.service;

import com.heednow.bo.ClientRequestBO;
import com.heednow.bo.UpdateClientBO;
import com.heednow.request.client.ClientRequest;
import com.heednow.request.client.UpdateClientRequest;
import com.heednow.requesthandler.ClientRequestHandler;
import com.heednow.response.client.ClientResponse;
import com.heednow.response.client.GetClientResponse;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-2 on 3/9/2017.
 */
@Path("/client")
public class ClientService {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response getClientList() {
        ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
        ClientResponse clientResponse = new ClientResponse();
        MessageResponse messageResponse = new MessageResponse();
        try {
            clientResponse.setClients(clientRequestHandler.getClients());
            return ResponseGenerator.generateSuccessResponse(clientResponse, "clients are available");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve the list. ");
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/clientInfo/{id}")
    public Response getClient(@PathParam("id") int id) {
        ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
        MessageResponse messageResponse = new MessageResponse();
        try {
            GetClientResponse client = clientRequestHandler.getClient(id);
            return ResponseGenerator.generateSuccessResponse(client, "Client details.");
        }catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Failed to retrieve client details. ");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addClient(ClientRequest clientRequest) {
        ClientRequestBO clientRequesttBO = new ClientRequestBO();
        clientRequesttBO.setName(clientRequest.getName());
        clientRequesttBO.setEmail(clientRequest.getEmail());
        clientRequesttBO.setMobile(clientRequest.getMobile());
        clientRequesttBO.setLocation(clientRequest.getLocation());
        clientRequesttBO.setNoOfOutlets(clientRequest.getNoOfOutlets());
        clientRequesttBO.setNoOfDevices(clientRequest.getNoOfDevices());
        clientRequesttBO.setClientId(clientRequest.getClientId());

        MessageResponse messageResponse = new MessageResponse();
        ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
        try {
            int clientId = clientRequestHandler.addClient(clientRequesttBO);
            if (clientId > 0) {

                return ResponseGenerator.generateSuccessResponse(messageResponse, String.valueOf(clientId));
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "client creation failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "client creation failed.");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateClient(UpdateClientRequest clientRequest) {
        UpdateClientBO clientRequesttBO = new UpdateClientBO();
        clientRequesttBO.setId(clientRequest.getId());
        clientRequesttBO.setName(clientRequest.getName());
        clientRequesttBO.setStatus(clientRequest.getStatus());
        clientRequesttBO.setEmail(clientRequest.getEmail());
        clientRequesttBO.setMobile(clientRequest.getMobile());
        clientRequesttBO.setLocation(clientRequest.getLocation());
        clientRequesttBO.setNoOfOutlets(clientRequest.getNoOfOutlets());
        clientRequesttBO.setNoOfDevices(clientRequest.getNoOfDevices());

        MessageResponse messageResponse = new MessageResponse();
        ClientRequestHandler clientRequestHandler = new ClientRequestHandler();
        try {
            Boolean isProcessed = clientRequestHandler.updateClient(clientRequesttBO);
            if (isProcessed) {
                return ResponseGenerator.generateSuccessResponse(messageResponse, "Client updated successfully.");
            } else {
                return ResponseGenerator.generateFailureResponse(messageResponse, "Client updation failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseGenerator.generateFailureResponse(messageResponse, "Client updation failed.");
        }
    }


}
