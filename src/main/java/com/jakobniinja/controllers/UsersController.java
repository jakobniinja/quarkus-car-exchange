package com.jakobniinja.controllers;

import com.jakobniinja.dtos.User;
import com.jakobniinja.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UsersController {

    @Inject
    UserService userService;

    @POST
    @Path("signup")
    public List<User> createUser(@Valid User user) {

        userService.create(user);

        return userService.find();
    }


    @GET
    @Path("{id}")
    public User findUser(@PathParam("id") String id) {
        return userService.findOne(id);
    }

    @GET
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
