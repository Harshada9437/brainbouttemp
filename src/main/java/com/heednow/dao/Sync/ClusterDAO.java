package com.heednow.dao.Sync;

import com.heednow.dao.ConnectionHandler;
import com.heednow.sync.Cluster;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by System-2 on 1/9/2017.
 */
public class ClusterDAO {
    public static int createCluster(Cluster cluster) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        int id=0;
        StringBuilder query = new StringBuilder("INSERT INTO cluster(client_id , cluster_desc, clusters_desc, region_id) values (?,?,?,?)");
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement(query.toString());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getId());
            preparedStatement.setString(parameterIndex++,
                    cluster.getDesc());
            preparedStatement.setString(parameterIndex++,
                    cluster.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getRegionId());

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
        return id;
    }

    public static List<Cluster> getClusters(int regionId,int clientId) {
        Connection connection = null;
        Statement statement = null;
        List<Cluster> clusters = new ArrayList<Cluster>();
        try {

            connection = new ConnectionHandler().getConnection();
            statement = connection.createStatement();
            StringBuilder query = new StringBuilder("SELECT * FROM cluster where region_id ="+regionId+" and client_id="+clientId);
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                Cluster cluster = new Cluster();
                cluster.setId(resultSet.getInt("id"));
                cluster.setDesc(resultSet.getString("cluster_desc"));
                cluster.setShortDesc(resultSet.getString("clusters_desc"));
                cluster.setRegionId(resultSet.getInt("region_id"));
                cluster.setClientId(resultSet.getInt("client_id"));
                clusters.add(cluster);
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
        return clusters;
    }

    public static Boolean updateCluster(Cluster cluster) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Boolean isProcessed=Boolean.FALSE;
        try {
            int parameterIndex = 1;
            connection = new ConnectionHandler().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE cluster SET  cluster_desc=?, clusters_desc=?, region_id=? WHERE id=?");

            preparedStatement.setString(parameterIndex++,
                    cluster.getDesc());
            preparedStatement.setString(parameterIndex++,
                    cluster.getShortDesc());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getRegionId());
            preparedStatement.setInt(parameterIndex++,
                    cluster.getId());


            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                isProcessed=Boolean.TRUE;
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
}
