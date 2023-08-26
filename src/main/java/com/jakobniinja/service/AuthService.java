package com.jakobniinja.service;

import com.jakobniinja.dtos.User;
import com.lambdaworks.crypto.SCryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import java.util.List;

@ApplicationScoped
public class AuthService {

    @Inject
    UserService userService;

    public void signUp(User user) {
        List<User> users = userService.find(user.getEmail());

        if (!users.isEmpty()) {
            throw new BadRequestException("email in use!");

        }

        String hashPassword = SCryptUtil.scrypt(user.getPassword(), 16,16,16);
        user.setPassword(hashPassword);

        userService.create(user);
    }

    void signIn() {

    }

}
