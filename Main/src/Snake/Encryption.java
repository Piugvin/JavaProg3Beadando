package Snake;

import LoginSignUp.SignUpApp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Encryption {
    public static String encrypt(int score, String plainText) {
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

        String encryptedText = tikositasPontszerint(score, plainText, processedChars, eredetiChars, charList,  keyList);
        String felh = "Felhasznalo";

        System.out.println("original message : " + plainText);
        System.out.println("encrypted message: " + encryptedText);


        String decryptedText = visszafejtes(encryptedText,  keyList, eredetiChars, charList, processedChars, plainText);

        System.out.println("decrypted message: " + decryptedText);

        return tikositasPontszerint(score, plainText, processedChars, eredetiChars, charList,  keyList);
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
}