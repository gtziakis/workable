package com.workable.config;

public class User {
    private String description;
    private String fullName;
    private String email;
    private String password;
    private String company;
    private String address;
    private String projectName;
    private String projectDescription;
    private String taskSummary;
    private String taskDescription;
    private String taskStatus;
    private String taskLabel;

    public User(String description, String fullName, String email, String password, String company, String address) {
        this.description = description;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.company = company;
        this.address = address;
    }

    public User(String description, String fullName, String email, String password, String company, String address, String projectName, String projectDescription, String  taskSummary, String taskDescription, String taskStatus, String taskLabel) {
        this.description = description;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.company = company;
        this.address = address;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.taskSummary = taskSummary;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskLabel = taskLabel;
    }

    public String getDescription() {
        return description;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getTaskSummary() {
        return taskSummary;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public String getTaskLabel() {
        return taskLabel;
    }


    public void setNewEmail(String newEmail) {
        this.password = newEmail;
    }
}
