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
}
