package com.jakobniinja.dtos;


import jakarta.validation.constraints.Email;

public class CreateUserDto {
    String _id;

    @Email
    String email;

    String password;


    public CreateUserDto() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
