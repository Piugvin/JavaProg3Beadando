package Snake;

import LoginSignUp.SignUpApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Encryption {
    public static String encrypt(int score) {
        String chars = " " + "0123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> charList = new ArrayList<>();
        for (char c : chars.toCharArray()) {
            charList.add(c);
        }
        String key="gkZHXi7D28mBpU9e0Lt54McR3OWq6dEfKwFhsvNnAJPYrQuICjSx1yVbzLopTaGyjXq"+" ";
        List<Character> keyList = new ArrayList<>();
        for (char k : key.toCharArray()) {
            keyList.add(k);
        }
        //Collections.shuffle(key);
        //System.out.println(key);
        //String plainText ="Valami123";
        //int score =apple.applesEaten;
        Set<Character> processedChars = new HashSet<>();
        Set<Character> eredetiChars = new HashSet<>();

        String encryptedText = tikositasPontszerint(score, SignUpApp.epwd, processedChars, eredetiChars, charList,  keyList);
        String felh = (SignUpApp.usn);
        System.out.println("original message : " + SignUpApp.epwd);
        System.out.println("encrypted message: " + encryptedText);
        String decryptedText = visszafejtes(encryptedText,  keyList, eredetiChars, charList, processedChars, SignUpApp.epwd);
        System.out.println("decrypted message: " + decryptedText);
        //DB
        Connection connection = null;
        PreparedStatement preparedStatement;
        final Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username1 = properties.getProperty("username");
            String password1 = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username1, password1);
            String insert =("INSERT INTO users (Encrypted_Password=?, Normal_Password=?) SELECT username FROM users WHERE username=?");
            preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, encryptedText);
            preparedStatement.setString(2, decryptedText);
            preparedStatement.setString(3, felh);
            preparedStatement.executeUpdate();
            } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return tikositasPontszerint(score, SignUpApp.epwd, processedChars, eredetiChars, charList,  keyList);
    }
    //DB
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

    public static String tikositasPontszerint(int score, String plainText, Set<Character> processedChars, Set<Character> eredetiChars, List<Character> chars, List<Character> key) {

        if (score >= 2) {
            plainText = szamTitkositas(plainText, processedChars, eredetiChars, chars, key);
        }
        if (score >= 3) {
            plainText = kisbetuTitkositas(plainText, processedChars, eredetiChars, chars, key);
        }
        if (score == 4) {
            plainText = nagybetuTitkositas(plainText, processedChars, eredetiChars, chars, key);
        }

        return plainText;
    }


    public static String visszafejtes(String cipherText, List<Character> key, Set<Character> eredetiChars, List<Character> chars, Set<Character> processedChars, String plainText) {
        //System.out.println(plainText);
        StringBuilder original = new StringBuilder();
        int indexPosition = 0;

        for (int i = 0; i < cipherText.length(); i++) {
            char letter = cipherText.charAt(i);
            int index = key.indexOf(letter);
            char eredetiKarakter = plainText.charAt(i);

            if (letter == eredetiKarakter) {
                //original.append("Index " + indexPosition + ": " + eredetiKarakter + " (CipherText: " + letter + ", PlainText: " + eredetiKarakter + ")\n");
                original.append(eredetiKarakter);
            } else if (eredetiChars.contains(chars.get(index))) {
                //original.append("Index " + indexPosition + ": " + chars.get(index) + " (CipherText: " + letter + ", PlainText: " + eredetiKarakter + ")\n");
                original.append(chars.get(index));
            } else {
                //original.append("Index " + indexPosition + ": " + key.get(index) + " (CipherText: " + letter + ", PlainText: " + eredetiKarakter + ")\n");
                original.append(key.get(index));
            }

            indexPosition++;
        }

        return original.toString();
    }

    public static String encrypt(int applesEaten, String epwd) {
        return epwd;
    }
}