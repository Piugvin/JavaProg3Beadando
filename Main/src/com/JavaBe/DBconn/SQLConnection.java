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

    public static void main(String[] args) throws RuntimeException, FileNotFoundException {
        final Properties properties = new Properties();
        Connection connection = null;

        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            try {
                connection = DriverManager.getConnection(url, username, password);
                Statement myStatement = connection.createStatement();
                System.out.println("Sikeres kapcsolodás! ");

            } catch (SQLException e) {
                System.out.println("Sikeretelen kapcsolat!");
                System.err.println("JDBC Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}