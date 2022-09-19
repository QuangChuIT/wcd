package com.aptech.utils;

import java.sql.*;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private String dbURL = "jdbc:mysql://localhost:3306/shopping?useSSL=false";
    private String username = "root";
    private String password = "123abc@A";

    private DatabaseUtil() {
        /*this.dbURL = SystemConfig.getInstance().getProperty("DB_URL");
        this.username = SystemConfig.getInstance().getProperty("Username");
        this.password = SystemConfig.getInstance().getProperty("Password");*/
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

    public void closeObject(Statement obj) throws SQLException {
        if (obj != null) {
            obj.close();
        }
    }

    public void closeObject(ResultSet obj) throws SQLException {
        if (obj != null) {
            obj.close();
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseUtil.getInstance().getConnection();
            if (connection != null) {
                System.out.println("Ket noi thanh cong");
            } else {
                System.out.println("Ket noi that bai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
