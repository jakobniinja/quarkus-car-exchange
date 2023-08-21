package com.jakobniinja.interceptors;

import com.google.gson.Gson;
import com.jakobniinja.dtos.UserDto;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Provider
public class MyInterceptor implements ReaderInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        InputStream is = context.getInputStream();
        String body = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));

        Gson g = new Gson();
        UserDto user = g.fromJson(body, UserDto.class);

        System.out.println(user.get_id());
        System.out.println(user.getEmail());
        return context.proceed();

    }
}
