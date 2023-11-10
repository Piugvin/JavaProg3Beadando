package com.JavaBe.DBconn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLConnection {

    public static void main(String[] args) throws RuntimeException {
        final Properties properties = new Properties();
        Connection connection = null;

        try (FileInputStream input = new FileInputStream("src/main/resources/db.properties")) {

            String url = properties.getProperty("dburl");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            try {
                connection = DriverManager.getConnection(url, username, password);
                Statement myStatement = connection.createStatement();
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}