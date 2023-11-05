import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Encryption {
    public static void main(String[] args) {
        String chars = " " + "0123456789" + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> charList = new ArrayList<>();
        for (char c : chars.toCharArray()) {
            charList.add(c);
        }
        List<Character> key = new ArrayList<>(charList);
        Collections.shuffle(key);

        String plainText ="Valami123";
        int score = 4;
        Set<Character> processedChars = new HashSet<>();
        Set<Character> eredetiChars = new HashSet<>();

        String encryptedText = tikositasPontszerint(score, plainText);
        String felh ="Felhasznalo";

        System.out.println("original message : " + plainText);
        System.out.println("encrypted message: " + encryptedText);



        String decryptedText = visszafejtes(encryptedText, key, eredetiChars, charList);

        System.out.println("decrypted message: " + decryptedText);
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

    public static String tikositasPontszerint(int score, String plainText) {
        Set<Character> processedChars = new HashSet<>();
        Set<Character> eredetiChars = new HashSet<>();
        List<Character> chars = new ArrayList<>();
        for (char c : " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            chars.add(c);
        }
        List<Character> key = new ArrayList<>(chars);
        Collections.shuffle(key);

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



    public static String visszafejtes(String cipherText, List<Character> key, Set<Character> eredetiChars, List<Character> chars) {
        StringBuilder plainText = new StringBuilder();
        for (char letter : cipherText.toCharArray()) {
            int index = key.indexOf(letter);
            if (eredetiChars.contains(chars.get(index))) {
                plainText.append(chars.get(index));
            } else {
                plainText.append(key.get(index));
            }
        }
        return plainText.toString();
    }
}
