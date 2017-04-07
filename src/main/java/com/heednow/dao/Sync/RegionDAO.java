package com.heednow.dao.Sync;

import com.heednow.dao.ConnectionHandler;
import com.heednow.dto.request.RegionRequestDTO;
import com.heednow.sync.Regions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class RegionDAO {
    public static void createRegion(Regions region) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO region(id , region_desc, regions_desc, company_id) values (?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    region.getId());
            preparedStatement.setString(parameterIndex++,
                    region.getDesc());
            preparedStatement.setString(parameterIndex++,
                    region.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    region.getCompanyId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            connection.rollback();
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Integer> getRegions() {
        Connection connection = null;
        Statement statement = null;
        List<Integer> regions = new ArrayList<Integer>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT id FROM region ");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                regions.add(id);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return regions;
    }

    public static void updateRegions(Regions region) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE region SET  region_desc=?, regions_desc=?, company_id=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    region.getDesc());
            preparedStatement.setString(parameterIndex++,
                    region.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    region.getCompanyId());
            preparedStatement.setInt(parameterIndex++,
                    region.getId());


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            connection.rollback();
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean createRegions(RegionRequestDTO regionRequestDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Boolean isProcessed=true;
        StringBuilder query = new StringBuilder("INSERT INTO region(id , region_desc, regions_desc, company_id, client_id) values (?,?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    regionRequestDTO.getId());
            preparedStatement.setString(parameterIndex++,
                    regionRequestDTO.getRegionDesc());
            preparedStatement.setString(parameterIndex++,
                    regionRequestDTO.getRegionsDesc());
            preparedStatement.setInt(parameterIndex++,
                    regionRequestDTO.getCompanyId());
            preparedStatement.setInt(parameterIndex++,
                    regionRequestDTO.getClientId());



            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            connection.rollback();
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isProcessed;
    }

    public static List<RegionRequestDTO> getRegionsDetail(int companyId,int clientId) {
        Connection connection = null;
        Statement statement = null;
        List<RegionRequestDTO> regions = new ArrayList<RegionRequestDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM region where company_id="+companyId+" and client_id="+clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                RegionRequestDTO regionRequestDTO = new RegionRequestDTO();
                regionRequestDTO.setId(resultSet.getInt("id"));
                regionRequestDTO.setRegionDesc(resultSet.getString("region_desc"));
                regionRequestDTO.setRegionDesc(resultSet.getString("regions_desc"));
                regionRequestDTO.setCompanyId(resultSet.getInt("company_id"));
                regionRequestDTO.setId(resultSet.getInt("client_id"));
                regions.add(regionRequestDTO);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return regions;
    }

    public Boolean updateRegions(RegionRequestDTO regionRequestDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE region SET  region_desc=?, regions_desc=?, company_id=?WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    regionRequestDTO.getRegionDesc());
            preparedStatement.setString(parameterIndex++,
                    regionRequestDTO.getRegionsDesc());
            preparedStatement.setInt(parameterIndex++,
                    regionRequestDTO.getCompanyId());
            preparedStatement.setInt(parameterIndex++,
                    regionRequestDTO.getId());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isCreated = Boolean.TRUE;
            } else {
                connection.rollback();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
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
