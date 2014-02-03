package com.company.net.util;

import java.sql.*;

public class DBConnector {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void openConnection(String driver, String server, String port, String db, String user, String password) {
        String connectionString = "jdbc:mysql://" + server + ":" + port + "/" + db;
        try {
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(connectionString, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(Connection connection) {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[] executeSelect(Connection connection, String fields, String table) {
        String queue = "SELECT " + fields + " FROM " + table;
        String[] headers = null;
        Object[][] data = null;
        Object[] result = new Object[2];

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queue);

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.first();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            data = new Object[rowCount][columnCount];

            headers = new String[columnCount];
            for (int indexJ = 0; indexJ < columnCount; indexJ++) {
                headers[indexJ] = resultSetMetaData.getColumnName(indexJ + 1);
            }

            for (int indexI = 0; indexI < rowCount; indexI++) {
                for (int indexJ = 0; indexJ < columnCount; indexJ++) {
                    data[indexI][indexJ] = resultSet.getObject(headers[indexJ].toString());
                    System.out.print(data[indexI][indexJ]);
                    System.out.print("\t");
                }
                System.out.println();
                resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        result[0] = headers;
        result[1] = data;
        return result;
    }

    public void executeInsert(Connection connection, String table, String data) {
        String queue = "INSERT INTO " + table + " VALUES(" + data + ")";
        try {
            Statement statement = connection.createStatement();
            int val = statement.executeUpdate(queue);
            if (val == 1)
                System.out.println("Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(Connection connection, String table, String set, String condition) {
        //UPDATE tbl_books SET price = 0 WHERE quantity = 0;
        String queue = "UPDATE " + table + " SET " + set + " WHERE " + condition;
        try {
            Statement statement = connection.createStatement();
            int val = statement.executeUpdate(queue);
            if (val == 1)
                System.out.println("Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeDelete(Connection connection, String table, String condition) {
        String queue = "DELETE FROM " + table + " WHERE " + condition;
        try {
            Statement statement = connection.createStatement();
            int val = statement.executeUpdate(queue);
            if (val == 1)
                System.out.println("Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
