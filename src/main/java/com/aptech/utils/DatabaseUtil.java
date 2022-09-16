package com.aptech.utils;

import java.sql.*;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private String dbURL;
    private String username;
    private String password;

    private DatabaseUtil() {
        this.dbURL = SystemConfig.getInstance().getProperty("DB_URL");
        this.username = SystemConfig.getInstance().getProperty("Username");
        this.password = SystemConfig.getInstance().getProperty("Password");
    }

    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        Connection conn;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(this.dbURL, this.username, this.password);
        return conn;
    }

    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public void closeObject(CallableStatement obj) throws SQLException {
        if (obj != null) {
            obj.close();
        }
    }

    public void closeObject(ResultSet obj) throws SQLException {
        if (obj != null) {
            obj.close();
        }
    }
}
