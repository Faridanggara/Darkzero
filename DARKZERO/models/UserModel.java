package com.darkzero.models;

public class UserModel {
    private String username;
    private String password;
    private String role;
    private String expired;
    private String status;
    private String licenseKey;

    public UserModel(String username, String password, String role, String expired, String status, String licenseKey) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.expired = expired;
        this.status = status;
        this.licenseKey = licenseKey;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getExpired() { return expired; }
    public String getStatus() { return status; }
    public String getLicenseKey() { return licenseKey; }
}