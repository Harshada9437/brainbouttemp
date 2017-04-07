package com.heednow.dao.Sync;

import com.heednow.dao.ConnectionHandler;
import com.heednow.sync.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class GroupDAO {
    public static int createGroup(Group group,int clientId) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int id;
        StringBuilder query = new StringBuilder("INSERT INTO groups(id , group_desc, groups_desc,client_id) values (?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    group.getId());
            preparedStatement.setString(parameterIndex++,
                    group.getDesc());
            preparedStatement.setString(parameterIndex++,
                    group.getShortDesc());
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

    public static List<Group> getGroups(int clientId) {
        Connection connection = null;
        Statement statement = null;
        List<Group> groups = new ArrayList<Group>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM groups where client_id=" + clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setDesc(resultSet.getString("group_desc"));
                group.setShortDesc(resultSet.getString("groups_desc"));
                group.setClientId(resultSet.getInt("client_id"));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }

    public static Boolean updateGroup(Group group) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Boolean isProcessed = Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE groups SET  group_desc=?, groups_desc=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    group.getDesc());
            preparedStatement.setString(parameterIndex++,
                    group.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    group.getId());


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isProcessed = Boolean.TRUE;
            } else {
                connection.rollback();
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
        return isProcessed;
    }

    public Group getGroupById(int id) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        Group group = new Group();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * from groups where id=" + id);
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                group.setDesc(resultSet.getString("group_desc"));
                group.setId(resultSet.getInt("id"));
                group.setShortDesc(resultSet.getString("groups_desc"));
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
        return group;
    }
}
