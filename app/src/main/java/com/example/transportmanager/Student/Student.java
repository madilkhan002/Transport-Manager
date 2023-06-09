package com.example.transportmanager.Student;

public class Student {
    String username,password,route;
    boolean isEligilble;

    Student()
    {
        // Default Constructor
        this.username = "";
        this.password = "";
        this.route = "";
        this.isEligilble = false;
    }
    public Student(String username, String password, String route, boolean isEligilble) {
        this.username = username;
        this.password = password;
        this.route = route;
        this.isEligilble = isEligilble;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRoute() {
        return route;
    }

    public boolean isEligilble() {
        return isEligilble;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setEligilble(boolean eligilble) {
        isEligilble = eligilble;
    }
}
