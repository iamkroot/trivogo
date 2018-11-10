package com.trivogo.models;

import java.text.*;

public class User {
    private String fullName,
            address,
            username,
            password,
            email;
    private java.util.Date dob;

    public User(String fullName, String address, String username, String password, String email, String Dob) {
        setFullName(fullName);
        setAddress(address);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setDob(Dob);
    }
    public User(String fullName, String address, String username, String password, String email, java.util.Date Dob) {
        setFullName(fullName);
        setAddress(address);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        this.dob = Dob;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String Dob) {
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
