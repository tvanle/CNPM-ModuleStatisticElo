package org.example;

public class Manager {
    private String id, username, password, position;
    private float salary;

    public Manager(String id, String username, String password, String position, float salary) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.position = position;
        this.salary = salary;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}