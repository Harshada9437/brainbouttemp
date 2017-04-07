package com.heednow.service;

import com.heednow.requesthandler.TableRequestHandler;
import com.heednow.response.table.TableResponseList;
import com.heednow.response.util.MessageResponse;
import com.heednow.response.util.ResponseGenerator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by System-3 on 12/10/2016.
 */

@Path("/table")
public class TableService {
    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTables( ) {
        TableRequestHandler statusRequestHandler = new TableRequestHandler();
        TableResponseList tableResponseList = new TableResponseList();
        try {
            tableResponseList.setTableResponse(statusRequestHandler.getTables());
            return ResponseGenerator.generateSuccessResponse(tableResponseList, "tables list.");
        } catch (SQLException e) {
            e.printStackTrace();
            MessageResponse messageResponse = new MessageResponse();
            return ResponseGenerator.generateSuccessResponse(messageResponse, "failed to retrieve tables list.");
        }
    }
}
