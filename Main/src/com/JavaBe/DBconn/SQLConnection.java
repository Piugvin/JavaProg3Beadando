package com.JavaBe.DBconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    public static void main(String[] args) {
        Connection connection = null;

        try {

            String url = "jdbc:mysql://80.211.205.244:3306/JavaDB";
            String username = "root";
            String password = "B@wy5PQy";


            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("Connected to the database!");
                           }

        } catch (SQLException e) {
            System.out.println("Failed to connect to the database!");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}