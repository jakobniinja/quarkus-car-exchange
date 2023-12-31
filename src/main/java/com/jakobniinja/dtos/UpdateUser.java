package com.jakobniinja.dtos;

import jakarta.validation.constraints.Email;

public class UpdateUser {

    @Email
    String email;
    String password;

    public UpdateUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
