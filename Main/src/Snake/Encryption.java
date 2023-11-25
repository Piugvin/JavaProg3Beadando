package Snake;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
public class Encryption {
    public static String encrypt(int score, String plainText, String felh) {
        String chars = " " + "0123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> charList = new ArrayList<>();
        for (char c : chars.toCharArray()) {
            charList.add(c);
        }
        String key="oN2tDPKwLq1VlTGWa3Y5s9yfpeZzvbiXjrM4cuJxh8mQdH6ERSkrnAICoU0gB7FjXqVZLHOB"+" ";
        List<Character> keyList = new ArrayList<>();
        for (char k : key.toCharArray()) {
            keyList.add(k);
        }
        Set<Character> processedChars = new HashSet<>();
        Set<Character> eredetiChars = new HashSet<>();
        plainText=String.join("",plainText);
        plainText=plainText.replace("[", "").replace("]", "").replace(",", "");
        plainText=plainText.replaceAll("[\\[\\],\\s]", "");
        String encryptedText = String.join("", tikositasPontszerint(score, plainText, processedChars, eredetiChars, charList, keyList, felh));
        encryptedText = encryptedText.replace("[", "").replace("]", "").replace(",", "");
        encryptedText = encryptedText.replaceAll("[\\[\\],\\s]", "");
        String decryptedText = String.join("", visszafejtes(encryptedText,  keyList, eredetiChars, charList, plainText));
        return tikositasPontszerint(score, plainText, processedChars, eredetiChars, charList,  keyList,felh);
    }
    public static String szamTitkositas(String plainText, Set<Character> processedChars, Set<Character> eredetiChars, List<Character> chars, List<Character> key) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (Character.isDigit(c) && !processedChars.contains(c)) {
                int index = chars.indexOf(c);
                eredetiChars.add(chars.get(index));
                cipherText.append(key.get(index));
                processedChars.add(key.get(index));
            } else {
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }
    public static String kisbetuTitkositas(String plainText, Set<Character> processedChars, Set<Character> eredetiChars, List<Character> chars, List<Character> key) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (Character.isLowerCase(c) && !processedChars.contains(c)) {
                int index = chars.indexOf(c);
                eredetiChars.add(chars.get(index));
                cipherText.append(key.get(index));
                processedChars.add(key.get(index));
            } else {
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }
    public static String nagybetuTitkositas(String plainText, Set<Character> processedChars, Set<Character> eredetiChars, List<Character> chars, List<Character> key) {
        StringBuilder cipherText = new StringBuilder();
        for (char c : plainText.toCharArray()) {
            if (Character.isUpperCase(c) && !processedChars.contains(c)) {
                int index = chars.indexOf(c);
                eredetiChars.add(chars.get(index));
                cipherText.append(key.get(index));
                processedChars.add(key.get(index));
            } else {
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }
    public static String tikositasPontszerint(int score, String plainText, Set<Character> processedChars, Set<Character> eredetiChars, List<Character> chars, List<Character> key, String felh) {

        if (score >= 2) {
            plainText = szamTitkositas(plainText, processedChars, eredetiChars, chars, key);
        }
        if (score >= 3) {
            plainText = kisbetuTitkositas(plainText, processedChars, eredetiChars, chars, key);
        }
        if (score == 4) {
            plainText = nagybetuTitkositas(plainText, processedChars, eredetiChars, chars, key);
        }
        //DB
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username1 = properties.getProperty("username");
            String password1 = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username1, password1);
            String insert = "UPDATE users SET Encrypted_password = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, plainText);
            preparedStatement.setString(2, felh);
            preparedStatement.executeUpdate();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
        return plainText;
    }
    public static String visszafejtes(String cipherText, List<Character> key, Set<Character> eredetiChars, List<Character> chars, String plainText) {
        StringBuilder original = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char letter = cipherText.charAt(i);
            int index = key.indexOf(letter);
            char eredetiKarakter = plainText.charAt(i);
            if (letter == eredetiKarakter) {
                original.append(eredetiKarakter);
            } else if (eredetiChars.contains(chars.get(index))) {
                original.append(chars.get(index));
            } else {
                original.append(key.get(index));
            }
        }
        return original.toString();
    }
}