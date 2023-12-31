package com.jakobniinja.controllers;

import com.jakobniinja.dtos.UpdateUser;
import com.jakobniinja.dtos.User;
import com.jakobniinja.service.AuthService;
import com.jakobniinja.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.net.HttpCookie;
import java.util.List;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UsersController {
    private static final HttpCookie httpCookie = new HttpCookie("Set-Cookie", "");
    @Inject
    UserService userService;
    @Inject
    AuthService authService;

    public String getCurrentUserId() {
        return httpCookie.getValue();
    }

    @GET
    @Path("whoami")
    public User whoAmI() {
        return userService.findOne(httpCookie.getValue());
    }

    @POST
    @Path("signout")
    public void signOut() {
        httpCookie.setValue("");
    }

    @POST
    @Path("signup")
    public List<User> createUser(@Valid User user) {
        authService.signUp(user);
        return userService.find(user.getEmail());
    }

    @POST
    @Path("signin")
    public String signIn(@Valid User user) {
        authService.signIn(user, httpCookie);
        return getCurrentUserId();
    }


    @GET
    @Path("{id}")
    public User findUser(@PathParam("id") String id) {
        User user = userService.findOne(id);

        if (user == null) {
            throw new NotFoundException("user not found!");
        }
        return user;
    }


    @DELETE
    @Path("delete/{id}")
    public List<User> removeUser(@PathParam("id") String id) throws Exception {
        userService.remove(id);
        return getAll();
    }

    @PATCH
    @Path("{id}")
    public List<User> updateUser(@PathParam("id") String id, UpdateUser user) throws Exception {
        userService.update(id, user);
        return userService.find(id);
    }

    @GET
    public List<User> findAllUsers(@QueryParam("email") String email) {
        return userService.find(email);
    }

    @GET
    @Path("/all")
    public List<User> getAll() {
        return userService.find();
    }


    @DELETE
    @Path("delete")
    public List<User> removeAll() {
        userService.deleteAll();
        return getAll();
    }
}
