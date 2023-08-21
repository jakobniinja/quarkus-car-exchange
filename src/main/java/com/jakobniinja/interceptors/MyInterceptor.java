package com.jakobniinja.interceptors;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MyInterceptor implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext context) {

        if (context.getUriInfo().getAbsolutePath().toString().matches(".*/auth/312")) {
            System.out.println("findUser was called, regex worked");
        }
    }
}
