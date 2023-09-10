package com.jakobniinja.service;

import com.jakobniinja.dtos.User;
import com.lambdaworks.crypto.SCryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.net.HttpCookie;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
public class AuthService {

    @Inject
    UserService userService;

    public String setUserId(User user, HttpCookie httpCookie) {
        httpCookie.setValue(user.get_id());
        return "ey" + Base64.getEncoder().encodeToString(user.get_id().getBytes());
    }

    public void signUp(User user) {
        List<User> users = userService.find(user.getEmail());

        if (!users.isEmpty()) {
            throw new BadRequestException("email in use!");
        }

        String hashPassword = SCryptUtil.scrypt(user.getPassword(), 16, 16, 16);
        user.setPassword(hashPassword);

        userService.create(user);
    }

    public void signIn(User user, HttpCookie httpCookie) {
        List<User> users = userService.find(user.getEmail());

        if (users.isEmpty()) {
            httpCookie.setValue("");
            throw new NotFoundException("user not found!");
        }

        users.stream().findFirst().ifPresent(storedUser -> {

            if (!SCryptUtil.check(user.getPassword(), storedUser.getPassword())) {
                httpCookie.setValue("");
                throw new BadRequestException("bad password");
            }

            setUserId(user, httpCookie);

            System.out.println(user.getEmail() + ", is now logged in!");
        });
    }
}
