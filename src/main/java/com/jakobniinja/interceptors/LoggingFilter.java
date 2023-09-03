package com.jakobniinja.interceptors;

import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LoggingFilter implements ContainerRequestFilter {


    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {

        //Do whatever you want
    }
}
