package com.heednow.dao.template;

import com.heednow.dao.ConnectionHandler;
import com.heednow.dto.request.Outlets;
import com.heednow.dto.request.TempDTO;
import com.heednow.dto.request.TemplateDTO;
import com.heednow.exceptions.TemplateNotFoundException;
import com.heednow.util.DateUtil;

import java.sql.*;
import java.util.*;

/**
 * Created by System1 on 9/9/2016.
 */
public class TemplateDAO {
    public Integer createTemplate(TemplateDTO templateDTO,int clientId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        StringBuilder query = new StringBuilder("INSERT INTO template(template_desc,client_id) values (?,?)");
        Integer id;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setString(parameterIndex++,
                    templateDTO.getTemplateDesc());
            preparedStatement.setInt(parameterIndex++,
                    clientId);

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
                            "Creating template failed, no ID obtained.");
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

    public static TemplateDTO getTemplateById(int templateId) throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        TemplateDTO templateDTO = new TemplateDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT template_id,template_desc FROM template where template_id = ").append(templateId);
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                templateDTO.setId(resultSet.getInt("template_id"));
                templateDTO.setTemplateDesc(resultSet.getString("template_desc"));
            }
            if (templateDTO.getId() == 0) {
                throw new TemplateNotFoundException("Invalid template id");
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
        return templateDTO;
    }

    public Boolean updateTemplate(TemplateDTO templateDTO) throws SQLException, TemplateNotFoundException {
        boolean isCreated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {

            getTemplateById(templateDTO.getId());

            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE template SET template_desc =?, status =? WHERE template_id =?;");

            preparedStatement.setString(parameterIndex++, templateDTO.getTemplateDesc());

            preparedStatement.setString(parameterIndex++, templateDTO.getStatus());

            preparedStatement.setInt(parameterIndex++, templateDTO.getId());

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

    public List<TemplateDTO> getTemplate(int clientId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<TemplateDTO> templateDTOs = new ArrayList<TemplateDTO>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * from template where client_id="+ clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                TemplateDTO templateDTO = new TemplateDTO();
                templateDTO.setId(resultSet.getInt("template_id"));
                templateDTO.setTemplateDesc(resultSet.getString("template_desc"));
                templateDTO.setStatus(resultSet.getString("status"));
                templateDTO.setOutlets(getOutlets(templateDTO.getId()));
                templateDTOs.add(templateDTO);

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
        return templateDTOs;
    }

    public static List<TempDTO> getTemplateByOutletId(int outletId) throws SQLException {
        Statement statement = null;
        List<TempDTO> tempDTOs = new ArrayList<TempDTO>();
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM outlet_template_link where outlet_id = ").append(outletId);
            ResultSet resultSet = statement.executeQuery(query.toString()
                  );
            while (resultSet.next()) {
                TempDTO tempDTO = new TempDTO();
                String toDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("to_date"));
                tempDTO.setToDate(toDate);
                String fromDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("from_date"));
                tempDTO.setFromDate(fromDate);
                tempDTO.setTemplateId(resultSet.getInt("template_id"));
                tempDTOs.add(tempDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tempDTOs;
    }

    public static List<Outlets> getOutlets(int tempId) throws SQLException {
        Statement statement = null;
        List<Outlets> tempDTOs = new ArrayList<Outlets>();
        Connection connection = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT o.id,o.short_desc,o.outlet_desc FROM outlet_template_link m\n" +
                            "join outlet o on o.id=m.outlet_id\n" +
                            "join template t on t.template_id=m.template_id\n" +
                            " where m.template_id =").append(tempId);
            ResultSet resultSet = statement.executeQuery(query.toString()
            );
            while (resultSet.next()) {
                Outlets outlet = new Outlets();
                outlet.setId(resultSet.getInt("id"));
                outlet.setDesc(resultSet.getString("outlet_desc"));
                outlet.setShortDesc(resultSet.getString("short_desc"));
                tempDTOs.add(outlet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tempDTOs;
    }

    public TempDTO getTemplateInfo(int templateId,int outletId) throws SQLException, TemplateNotFoundException {
        Connection connection = null;
        Statement statement = null;
        TempDTO tempDTO = new TempDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select m.outlet_id,m.template_id,m.from_date,m.to_date,t.status,t.template_desc\n" +
                    " from outlet_template_link m\n" +
                    " left join template t\n" +
                    " on t.template_id=m.template_id\n" +
                    " where m.template_id="+ templateId + " and m.outlet_id=" + outletId);
            ResultSet resultSet = statement.executeQuery(query.toString());
            int index = 1;
            while (resultSet.next()) {

                tempDTO.setTemplateId(resultSet.getInt("template_id"));
                tempDTO.setDesc(resultSet.getString("template_desc"));
                tempDTO.setStatus(resultSet.getString("status"));
                tempDTO.setOutletId(resultSet.getInt("outlet_id"));
                String fDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("from_date"));
                tempDTO.setFromDate(fDate);
                String tDate = DateUtil.getDateStringFromTimeStamp(resultSet.getTimestamp("to_date"));
                tempDTO.setToDate(tDate);
                index++;
            }

            if(index == 1){
                throw new TemplateNotFoundException("Invalid id");
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
        return tempDTO;
    }

    public static Boolean getTemplateByName(String name) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        Boolean isExist = Boolean.FALSE;
        try {
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder(
                    "SELECT * FROM template where template_desc =\"").append(name).append("\"");
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
               isExist = Boolean.TRUE;
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
        return isExist;
    }


}
