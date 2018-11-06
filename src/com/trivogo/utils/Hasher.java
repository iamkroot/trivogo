package com.trivogo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class Hasher {
    public static String hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (java.security.NoSuchAlgorithmException nsae) {
            System.err.println("NoSuchAlgorithmException error while hashing pwd.");
            System.exit(2);
        }
        return null;
    }
}
