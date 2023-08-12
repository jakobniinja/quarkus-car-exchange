package com.jakobniinja.controllers;

import com.jakobniinja.dtos.CreateUserDto;
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
    public List<CreateUserDto> createUser(@Valid CreateUserDto createUserDto) {

        userService.create(createUserDto.getEmail(), createUserDto.getPassword());

        return userService.find();
    }

    @GET
    public List<CreateUserDto> getAll() {
        return userService.find();
    }


    @DELETE
    @Path("delete")
    public List<CreateUserDto> removeAll() {
        userService.deleteAll();

        return getAll();
    }
}
