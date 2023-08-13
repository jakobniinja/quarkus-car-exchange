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
        Response response = given().when().get("/auth/all").then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getList("list").size(), is(greaterThanOrEqualTo(0)));

    }

    @Test
    void testCreateUser() {

        Map<String, String> map = Map.of("_id", "333", "email", "apple@outlook.com", "password", "small-apple");


        Response response = given().contentType(ContentType.JSON).body(map).when().post("/auth/signup").then().statusCode(200).extract().response();

        List<User> users = response.jsonPath().getList("$");

        assertThat(users, not(empty()));

    }

    @Test
    void findUserTest() {

        Map<String, String> map = Map.of("_id", "3", "email", "apple@outlook.com", "password", "small-apple");

        given().contentType(ContentType.JSON).body(map).when().post("/auth/signup").then().statusCode(200);

        given().when().get("auth/3").then().statusCode(200);
    }


    @Test
    void findAllUsersTest(){
        given().when().get("auth?email=1@outlook.com").then().statusCode(200);
    }
}
