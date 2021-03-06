package com.trivogo.models;

import java.text.*;

public class User {
    private String fullName,
            address,
            username,
            password,
            email;
    private java.util.Date dob;

    public User(String username, String fullName, String email, String address, String Dob, String password) {
        setFullName(fullName);
        setAddress(address);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setDob(Dob);
    }
    public User(String username, String fullName, String email, String address, java.util.Date Dob, String password) {
        setFullName(fullName);
        setAddress(address);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        this.dob = Dob;
    }
    public User(String username, String fullName, String email, String address, java.time.LocalDate Dob, String password) {
        setFullName(fullName);
        setAddress(address);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        this.dob = java.sql.Date.valueOf(Dob);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
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
