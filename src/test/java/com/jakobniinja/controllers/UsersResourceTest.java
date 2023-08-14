package com.jakobniinja.controllers;

import com.jakobniinja.dtos.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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

        Map<String, String> user = Map.of("_id", "333", "email", "apple@outlook.com", "password", "small-apple");

        Response response = given().contentType(ContentType.JSON).body(user).when().post("/auth/signup").then().statusCode(200).extract().response();

        List<User> users = response.jsonPath().getList("$");

        assertThat(users, not(empty()));

    }

    @Test
    void findUserTest() {

        Map<String, String> user = Map.of("_id", "3", "email", "apple@outlook.com", "password", "small-apple");

        given().contentType(ContentType.JSON).body(user).when().post("/auth/signup").then().statusCode(200);

        given().when().get("auth/3").then().statusCode(200);
    }


    @Test
    void findAllUsersTest(){
        given().when().get("auth?email=1@outlook.com").then().statusCode(200);
    }

    @Test
    void removeUserTest(){
        Map<String, String> user = Map.of("_id", "122", "email", "android@samsung.com", "password", "Galaxy s30");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);
        given().when().delete("auth/delete/122").then().statusCode(200);

    }

    @Test
    void updateUserTest(){
        Map<String, String> user = Map.of("_id", "5", "email", "android@samsung.com", "password", "Galaxy s30");
        Map<String, String> updatedUser = Map.of("email", "jakob@y.com", "password", "hard password");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);
        given().contentType(MediaType.APPLICATION_JSON).body(updatedUser).patch("auth/5").then().statusCode(200);

    }
}
