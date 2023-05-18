package com.example.personalitytestapp;

public class DataModalUser {
    private String name;
    private String email;
    private String password;
    private String role;
    private String google_id;

//    public DataModalUser(String name) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//        this.google_id = google_id;
//    }

//    For Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//  For Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    For Passwor
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    For ROle
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    FOR GOOGLE ID
    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }
}
