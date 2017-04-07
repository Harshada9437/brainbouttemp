package com.heednow.dao.Sync;

import com.heednow.dao.ConnectionHandler;
import com.heednow.dto.request.VersionInfoDTO;

import java.sql.*;

/**
 * Created by Sandeep on 1/20/2017.
 */
public class VersionDAO {
    public VersionInfoDTO getAndroidVersion(int clientId) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        VersionInfoDTO versionInfoDTO = new VersionInfoDTO();
        try {
            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("select * from android_version where client_id="+clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                versionInfoDTO.setVersionCode(resultSet.getInt("versionCode"));
                versionInfoDTO.setVersionNumber(resultSet.getString("versionName"));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return versionInfoDTO;
    }
}
