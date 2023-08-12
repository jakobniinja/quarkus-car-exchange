package com.jakobniinja.controllers;

import com.jakobniinja.dtos.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class UsersResourceTest {

    @AfterEach
    void tearDown() {
        given().contentType(ContentType.JSON).when().delete("/auth/delete").then().statusCode(200);
    }

    @Test
    void testGetUser() {
        Response response = given().when().get("/auth").then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getList("list").size(), is(greaterThanOrEqualTo(0)));

    }

    @Test
    void testCreateUser() {
        Map<String, String> map = new HashMap<>();

        map.put("_id", "333");
        map.put("email", "apple@outlook.com");
        map.put("password", "small-apple");


        Response response = given().contentType(ContentType.JSON).body(map).when().post("/auth/signup").then()
                .statusCode(200).body(containsString("apple")).extract().response();

        List<User> users = response.jsonPath().getList("$");

        assertThat(users, not(empty()));

    }
}
