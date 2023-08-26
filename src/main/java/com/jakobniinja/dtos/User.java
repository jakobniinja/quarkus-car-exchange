package com.jakobniinja.dtos;


import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.Email;

public class User {
    String _id;
    @Email(message = "invalid email")
    String email;
    String password;

    public User() {
        //
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonbTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
