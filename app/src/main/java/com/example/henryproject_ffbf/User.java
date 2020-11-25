package com.example.henryproject_ffbf;

public class User {
    public String first_name, surname, email, password, userRole;

    // empty constructor
    public User() {}

    public User(String firstName, String Surname, String Email, String Password) {
        this.first_name = firstName;
        this.surname = Surname;
        this.email = Email;
        this.password = Password;
        this.userRole = "user";
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getFullName() {
        return first_name + " " + surname;
    }
}
