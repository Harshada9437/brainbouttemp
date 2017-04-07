package com.heednow.requesthandler;

import com.heednow.bo.ClientRequestBO;
import com.heednow.bo.UpdateClientBO;
import com.heednow.dao.ClientDAO;
import com.heednow.dto.client.ClientDTO;
import com.heednow.response.client.ClientDetail;
import com.heednow.response.client.GetClientResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by System-2 on 3/9/2017.
 */
public class ClientRequestHandler {
    public List<ClientDetail> getClients() throws SQLException {
        ClientDAO clientDAO = new ClientDAO();
        List<ClientDetail> clients = getResponseFromDto(clientDAO.getClients());
        return clients;
    }

    public List<ClientDetail> getResponseFromDto(List<ClientDTO> clientDTOS) throws SQLException {
        List<ClientDetail> clientDetails = new ArrayList<ClientDetail>();
        Iterator<ClientDTO> iterator = clientDTOS.iterator();
        while (iterator.hasNext()) {
            ClientDTO clientDTO = iterator.next();
            ClientDetail answerResponseList = new ClientDetail(clientDTO.getId(),
                    clientDTO.getName(),
                    clientDTO.getEmail(),
                    clientDTO.getMobile(),
                    clientDTO.getLocation(),
                    clientDTO.getClientId(),
                    clientDTO.getNoOfOutlets(),
                    clientDTO.getNoOfDevices(),
                    clientDTO.getStatus());
            clientDetails.add(answerResponseList);
        }
        return clientDetails;
    }

    public GetClientResponse getClient(int id) throws SQLException {
        ClientDAO clientDAO = new ClientDAO();
        ClientDTO clientDTO = clientDAO.getClientById(id);
        GetClientResponse clientResponse = new GetClientResponse(clientDTO.getId(),
                clientDTO.getNoOfOutlets(),
                clientDTO.getNoOfDevices(),
                clientDTO.getName(),
                clientDTO.getEmail(),
                clientDTO.getMobile(),
                clientDTO.getLocation(),
                clientDTO.getClientId(),
                clientDTO.getStatus());
        return clientResponse;
    }

    public int addClient(ClientRequestBO clientRequesttBO) throws SQLException {
        ClientDTO clientDTO = new ClientDTO();
        ClientDAO clientDAO = new ClientDAO();

        clientDTO.setName(clientRequesttBO.getName());
        clientDTO.setEmail(clientRequesttBO.getEmail());
        clientDTO.setMobile(clientRequesttBO.getMobile());
        clientDTO.setLocation(clientRequesttBO.getLocation());
        clientDTO.setNoOfOutlets(clientRequesttBO.getNoOfOutlets());
        clientDTO.setNoOfDevices(clientRequesttBO.getNoOfDevices());
        clientDTO.setClientId(clientRequesttBO.getClientId());
        int id = clientDAO.createClient(clientDTO);
        return id;
    }

    public Boolean updateClient(UpdateClientBO clientRequesttBO) throws SQLException {
        ClientDTO clientDTO = new ClientDTO();
        ClientDAO clientDAO = new ClientDAO();

        clientDTO.setName(clientRequesttBO.getName());
        clientDTO.setStatus(clientRequesttBO.getStatus());
        clientDTO.setEmail(clientRequesttBO.getEmail());
        clientDTO.setMobile(clientRequesttBO.getMobile());
        clientDTO.setLocation(clientRequesttBO.getLocation());
        clientDTO.setId(clientRequesttBO.getId());
        clientDTO.setNoOfOutlets(clientRequesttBO.getNoOfOutlets());
        clientDTO.setNoOfDevices(clientRequesttBO.getNoOfDevices());

        Boolean isProcessed = clientDAO.updateClient(clientDTO);
        return isProcessed;
    }
}
