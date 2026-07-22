package com.darkzero.models;

public class BugModel {
    private String action;
    private String target;
    private String message;
    private String status;

    public BugModel(String action, String target, String message, String status) {
        this.action = action;
        this.target = target;
        this.message = message;
        this.status = status;
    }

    // Getters
    public String getAction() { return action; }
    public String getTarget() { return target; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
}