package com.Models;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toCsvString() {
        return String.format("%s,%s", username, password);
    }

    public static User fromCsvString(String csvString) {
        String[] parts = csvString.split(",");
        String username = parts[0];
        String password = parts[1];
        return new User(username, password);
    }
}
