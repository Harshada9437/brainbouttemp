package com.heednow.dao.Sync;

import com.heednow.dao.ConnectionHandler;
import com.heednow.dto.request.SettingRequestDTO;
import com.heednow.dto.request.SmsSettingDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 2/7/2017.
 */
public class SmsDAO {
    public static Boolean saveSetting(SettingRequestDTO settingRequestDTO, int clientId) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            int parameterIndex = 1;

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO global_settings (sms_template,archive_time,report_time,client_id,email_template,versionCode,versionName) values(?,?,?,?,?,?,?)");

            statement.setString(parameterIndex++, settingRequestDTO.getSmsTemplate());

            statement.setTime(parameterIndex++, settingRequestDTO.getArchiveTime());

            statement.setTime(parameterIndex++, settingRequestDTO.getReportTime());

            statement.setInt(parameterIndex++, settingRequestDTO.getClientId());

            statement.setString(parameterIndex++, settingRequestDTO.getEmailTemplate());

            statement.setInt(parameterIndex++, settingRequestDTO.getVersionCode());

            statement.setString(parameterIndex++, settingRequestDTO.getVersionName());

            int i = statement.executeUpdate();
            if (i > 0) {
                isProcessed = Boolean.TRUE;
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException sq) {
                sq.printStackTrace();
                throw sq;
            }
        }
        return isProcessed;
    }

    public static Boolean updateSetting(SettingRequestDTO settingRequestDTO, int clientId) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            int parameterIndex = 1;

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE global_settings SET sms_template=?,archive_time=?,report_time=? ,email_template=?,versionCode=?,versionName=? where client_id=?");

            statement.setString(parameterIndex++, settingRequestDTO.getSmsTemplate());

            statement.setTime(parameterIndex++, settingRequestDTO.getArchiveTime());

            statement.setTime(parameterIndex++, settingRequestDTO.getReportTime());

            statement.setInt(parameterIndex++, clientId);

            statement.setString(parameterIndex++, settingRequestDTO.getEmailTemplate());

            statement.setInt(parameterIndex++, settingRequestDTO.getVersionCode());

            statement.setString(parameterIndex++, settingRequestDTO.getVersionName());



            int i = statement.executeUpdate();
            if (i > 0) {
                isProcessed = Boolean.TRUE;
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException sq) {
                sq.printStackTrace();
                throw sq;
            }
        }
        return isProcessed;
    }

    public static SettingRequestDTO fetchSettings(int clientId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        SettingRequestDTO settingRequestDTO = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from global_settings where client_id=" + clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());


            while (resultSet.next()) {
                settingRequestDTO = new SettingRequestDTO();
                settingRequestDTO.setSmsTemplate(resultSet.getString("sms_template"));
                settingRequestDTO.setArchiveTime(resultSet.getTime("archive_time"));
                settingRequestDTO.setReportTime(resultSet.getTime("report_time"));
                settingRequestDTO.setEmailTemplate(resultSet.getString("email_template"));
                settingRequestDTO.setVersionCode(resultSet.getInt("versionCode"));
                settingRequestDTO.setVersionName(resultSet.getString("versionName"));
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
        return settingRequestDTO;
    }


    public static Integer saveSmsSettings(SmsSettingDTO settingRequestDTO) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int id = 0;
        try{
            int parameterIndex = 1;

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO sms_gateway_mstr(api,sender_id,campaign,country_code,name,client_id) VALUES(?,?,?,?,?,?)");

            statement.setString(parameterIndex++,settingRequestDTO.getApi());
            statement.setString(parameterIndex++,settingRequestDTO.getSenderId());
            statement.setString(parameterIndex++,settingRequestDTO.getCampaign());
            statement.setString(parameterIndex++,settingRequestDTO.getCountryCode());
            statement.setString(parameterIndex++,settingRequestDTO.getName());
            statement.setInt(parameterIndex++,settingRequestDTO.getClientId());

            int i = statement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }

            try {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    connection.rollback();
                    throw new SQLException(
                            "Creating gateway failed, no ID obtained.");
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            try {
                statement.close();
                connection.close();
            }catch (SQLException sq){
                sq.printStackTrace();
                throw sq;
            }
        }
        return id;
    }

    public static List<SmsSettingDTO> fetchSmsSettings(int clientId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        List<SmsSettingDTO> smsSettingDTOS = new ArrayList<SmsSettingDTO>();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from sms_gateway_mstr where client_id="+clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                SmsSettingDTO settingRequestDTO = new SmsSettingDTO();
                settingRequestDTO.setId(resultSet.getInt("id"));
                settingRequestDTO.setName(resultSet.getString("name"));
                settingRequestDTO.setApi(resultSet.getString("api"));
                settingRequestDTO.setSenderId(resultSet.getString("sender_id"));
                settingRequestDTO.setCampaign(resultSet.getString("campaign"));
                settingRequestDTO.setCountryCode(resultSet.getString("country_code"));
                smsSettingDTOS.add(settingRequestDTO);
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
        return smsSettingDTOS;
    }

    public static SmsSettingDTO fetchSmsSettingsById(int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        SmsSettingDTO smsSettingDTO = new SmsSettingDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from sms_gateway_mstr where id=" + id);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                smsSettingDTO.setId(resultSet.getInt("id"));
                smsSettingDTO.setName(resultSet.getString("name"));
                smsSettingDTO.setApi(resultSet.getString("api"));
                smsSettingDTO.setSenderId(resultSet.getString("sender_id"));
                smsSettingDTO.setCampaign(resultSet.getString("campaign"));
                smsSettingDTO.setCountryCode(resultSet.getString("country_code"));
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
        return smsSettingDTO;
    }

    public static Boolean getSettingsByName(String name) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from sms_gateway_mstr where name=\"" + name + "\"");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                isProcessed=Boolean.TRUE;
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
        return isProcessed;
    }

    public static void updateSmsSettings(SmsSettingDTO settingRequestDTO) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            int parameterIndex = 1;

            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE sms_gateway_mstr SET  api=?,sender_id=?,campaign=?,country_code=?,name=? WHERE id=?");

            statement.setString(parameterIndex++,settingRequestDTO.getApi());
            statement.setString(parameterIndex++,settingRequestDTO.getSenderId());
            statement.setString(parameterIndex++,settingRequestDTO.getCampaign());
            statement.setString(parameterIndex++,settingRequestDTO.getCountryCode());
            statement.setString(parameterIndex++,settingRequestDTO.getName());
            statement.setInt(parameterIndex++,settingRequestDTO.getId());

            int i = statement.executeUpdate();
            if (i > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }
        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw e;
        }finally {
            try {
                statement.close();
                connection.close();
            }catch (SQLException sq){
                sq.printStackTrace();
                throw sq;
            }
        }
    }

    public static Boolean getSettingsByUpdateName(String name, int id) throws SQLException {
        Boolean isProcessed = Boolean.FALSE;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from sms_gateway_mstr where id<>" + id +" and name=\"" + name + "\"");
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                isProcessed=Boolean.TRUE;
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
        return isProcessed;
    }
}
