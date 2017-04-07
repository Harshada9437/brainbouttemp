package com.heednow.dao;

import com.heednow.dto.client.ClientDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 3/10/2017.
 */
public class ClientDAO {
    public Integer createClient(ClientDTO clientDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO heednow_clients(name , email, mobile, location,no_of_outlets,no_of_devices,client_id) values (?,?,?,?,?,?,?)");
        Integer id = 0;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++, clientDTO.getName());

            preparedStatement.setString(parameterIndex++, clientDTO.getEmail());

            preparedStatement.setString(parameterIndex++, clientDTO.getMobile());

            preparedStatement.setString(parameterIndex++, clientDTO.getLocation());

            preparedStatement.setInt(parameterIndex++, clientDTO.getNoOfOutlets());

            preparedStatement.setInt(parameterIndex++, clientDTO.getNoOfDevices());

            preparedStatement.setString(parameterIndex++, clientDTO.getClientId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }

            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException(
                            "Creating answer failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                throw e;
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public List<ClientDTO> getClients() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<ClientDTO> clients = new ArrayList<ClientDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            String query = "SELECT * from heednow_clients";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ClientDTO clientDTO = new ClientDTO();
                clientDTO.setName(resultSet.getString("name"));
                clientDTO.setId(resultSet.getInt("id"));
                clientDTO.setEmail(resultSet.getString("email"));
                clientDTO.setMobile(resultSet.getString("mobile"));
                clientDTO.setStatus(resultSet.getString("status"));
                clientDTO.setLocation(resultSet.getString("location"));
                clientDTO.setClientId(resultSet.getString("client_id"));
                clientDTO.setNoOfOutlets(resultSet.getInt("no_of_outlets"));
                clientDTO.setNoOfDevices(resultSet.getInt("no_of_devices"));
                clients.add(clientDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return clients;
    }

    public ClientDTO getClientById(int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ClientDTO clientDTO = new ClientDTO();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * from heednow_clients where id="+id);
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                clientDTO.setName(resultSet.getString("name"));
                clientDTO.setId(resultSet.getInt("id"));
                clientDTO.setEmail(resultSet.getString("email"));
                clientDTO.setMobile(resultSet.getString("mobile"));
                clientDTO.setStatus(resultSet.getString("status"));
                clientDTO.setLocation(resultSet.getString("location"));
                clientDTO.setClientId(resultSet.getString("client_id"));
                clientDTO.setNoOfOutlets(resultSet.getInt("no_of_outlets"));
                clientDTO.setNoOfDevices(resultSet.getInt("no_of_devices"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return clientDTO;
    }

    public static ClientDTO getClient(String id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ClientDTO clientDTO = new ClientDTO();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * from heednow_clients where client_id='"+id+"'");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                clientDTO.setName(resultSet.getString("name"));
                clientDTO.setId(resultSet.getInt("id"));
                clientDTO.setEmail(resultSet.getString("email"));
                clientDTO.setMobile(resultSet.getString("mobile"));
                clientDTO.setStatus(resultSet.getString("status"));
                clientDTO.setLocation(resultSet.getString("location"));
                clientDTO.setClientId(resultSet.getString("client_id"));
                clientDTO.setNoOfOutlets(resultSet.getInt("no_of_outlets"));
                clientDTO.setNoOfDevices(resultSet.getInt("no_of_devices"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return clientDTO;
    }


    public Boolean updateClient(ClientDTO clientDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE heednow_clients SET name=?, email =?, mobile =?, status=?, location=?, no_of_outlets=?,no_of_devices=? WHERE id =?");

            preparedStatement.setString(parameterIndex++, clientDTO.getName());

            preparedStatement.setString(parameterIndex++, clientDTO.getEmail());

            preparedStatement.setString(parameterIndex++, clientDTO.getMobile());

            preparedStatement.setString(parameterIndex++, clientDTO.getStatus());

            preparedStatement.setString(parameterIndex++, clientDTO.getLocation());

            preparedStatement.setInt(parameterIndex++, clientDTO.getNoOfOutlets());

            preparedStatement.setInt(parameterIndex++, clientDTO.getNoOfDevices());

            preparedStatement.setInt(parameterIndex++, clientDTO.getId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isCreated = Boolean.TRUE;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isCreated;
    }


}
