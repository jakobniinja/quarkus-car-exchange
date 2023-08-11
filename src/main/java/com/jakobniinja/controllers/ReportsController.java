package com.jakobniinja.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/reports")
public class ReportsController {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getReports() {
        return "Here comes all reports.";
    }
}
