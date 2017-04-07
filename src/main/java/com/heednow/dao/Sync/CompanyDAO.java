package com.heednow.dao.Sync;

import com.heednow.dao.ConnectionHandler;
import com.heednow.dto.request.CompanyDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class CompanyDAO {
    public static CompanyDTO getCompany(int id) {
        Connection connection = null;
        Statement statement = null;
        CompanyDTO companyDTO = new CompanyDTO();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT id FROM company where id="+id);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                companyDTO.setId(resultSet.getInt("id"));
                companyDTO.setCompanyDesc(resultSet.getString("company_desc"));
                companyDTO.setCompanyShortDesc(resultSet.getString("company_short_desc"));
                companyDTO.setGroupId(resultSet.getInt("group_id"));
                companyDTO.setClientId(resultSet.getInt("client_id"));
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
        return companyDTO;
    }

    public Boolean createCompanies(CompanyDTO companyDTO) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Boolean isProcessed= true;
        StringBuilder query = new StringBuilder("INSERT INTO company(company_desc,company_short_desc,group_id,client_id) values (?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    companyDTO.getCompanyDesc());
            preparedStatement.setString(parameterIndex++,
                    companyDTO.getCompanyShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    companyDTO.getGroupId());
            preparedStatement.setInt(parameterIndex++,
                    companyDTO.getClientId());



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

    public static List<CompanyDTO> getCompaniesDetail(int groupId,int clientId) {
        Connection connection = null;
        Statement statement = null;
        List<CompanyDTO> companies = new ArrayList<CompanyDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM company where group_id="+groupId+" and client_id="+clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                CompanyDTO companyDTO = new CompanyDTO();
                companyDTO.setId(resultSet.getInt("id"));
                companyDTO.setCompanyDesc(resultSet.getString("company_desc"));
                companyDTO.setCompanyShortDesc(resultSet.getString("company_short_desc"));
                companyDTO.setGroupId(resultSet.getInt("group_id"));
                companyDTO.setClientId(resultSet.getInt("client_id"));
                companies.add(companyDTO);
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
        return companies;
    }

    public Boolean updateCompanies(CompanyDTO companyDTO) throws SQLException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE company SET  company_desc=?, company_short_desc=?, group_id=? ,client_id=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    companyDTO.getCompanyDesc());
            preparedStatement.setString(parameterIndex++,
                    companyDTO.getCompanyShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    companyDTO.getGroupId());
            preparedStatement.setInt(parameterIndex++,
                    companyDTO.getClientId());
            preparedStatement.setInt(parameterIndex++,
                    companyDTO.getId());

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
