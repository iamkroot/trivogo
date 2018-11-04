package com.trivogo.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.*;
import java.util.Base64;

public class UserModel {
    private String fullName,
            address,
            username,
            password,
            email;
    private java.util.Date dob;

    public UserModel (String fullName, String address, String username, String password, String email, String Dob) {
        setFullName(fullName);
        setAddress(address);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setDob(Dob);
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            this.password = Base64.getEncoder().encodeToString(hash);
        } catch (java.security.NoSuchAlgorithmException nsae) {
            System.err.println("NoSuchAlgorithmException error while hashing pwd.");
            System.exit(2);
        }
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setDob(String Dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dob = sdf.parse(Dob);
        } catch (java.text.ParseException pe){
            System.err.println("ParseException encountered trying to parse DOB");
            System.exit(2);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public java.util.Date getDob() {
        return dob;
    }
}
