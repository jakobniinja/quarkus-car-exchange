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
    void findAllUsersTest() {
        given().when().get("auth?email=1@outlook.com").then().statusCode(200);
    }

    @Test
    void removeUserTest() {
        Map<String, String> user = Map.of("_id", "122", "email", "android@samsung.com", "password", "Galaxy s30");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);
        given().when().delete("auth/delete/122").then().statusCode(200);

    }

    @Test
    void updatePasswordTest() {
        Map<String, String> user = Map.of("_id", "312", "email", "hej@test.com", "password", "hj");

        Map<String, String> updatedUser = Map.of("_id", "312", "email", "hej@test.com", "password", "hej");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(MediaType.APPLICATION_JSON).body(updatedUser).patch("auth/312").then().statusCode(200);

        given().get("auth/all").then().statusCode(200).body("email", hasItem("hej@test.com")).body("password", hasItem("hej"));
    }

    @Test
    void updateEmailTest() {
        Map<String, String> user = Map.of("_id", "312", "email", "hej@test.com", "password", "hj");

        Map<String, String> updatedUser = Map.of("_id", "312", "email", "hej@hej.com", "password", "hj");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(MediaType.APPLICATION_JSON).body(updatedUser).patch("auth/312").then().statusCode(200);


        given().get("auth/all").then().statusCode(200).body("email", hasItem("hej@hej.com")).body("password", hasItem("hj"));


    }

    @Test
    void updateEmailNoPassword() {
        Map<String, String> user = Map.of("_id", "312", "email", "hej@hej.com", "password", "hej");

        Map<String, String> updatedUser = Map.of("_id", "312", "email", "2@hej.com");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(MediaType.APPLICATION_JSON).body(updatedUser).patch("auth/312").then().statusCode(200);

        given().get("auth/all").then().statusCode(200).body("email", hasItem("2@hej.com")).body("password", hasItem("hej"));
    }

    @Test
    void updatePasswordNoEmail() {
        Map<String, String> user = Map.of("_id", "312", "email", "2@hej.com", "password", "hej");

        Map<String, String> updatedUser = Map.of("_id", "312", "password", "not so strong password");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(MediaType.APPLICATION_JSON).body(updatedUser).patch("auth/312").then().statusCode(200);

        given().get("auth/all").then().statusCode(200).body("email", hasItem("2@hej.com")).body("password", hasItem("not so strong password"));
    }


    @Test
    void updateJustId() {
        Map<String, String> user = Map.of("_id", "312", "email", "2@hej.com", "password", "hej");

        Map<String, String> updatedUser = Map.of("_id", "312");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(MediaType.APPLICATION_JSON).body(updatedUser).patch("auth/312").then().statusCode(200);

        given().get("auth/all").then().statusCode(200).body("email", hasItem("2@hej.com")).body("password", hasItem("hej"));
    }

    @Test
    void findOneTestNotFound() {
        given().when().get("auth/122").then().statusCode(404);
    }

    @Test
    void deleteNotExists() {
        given().when().delete("auth/delete/122").then().statusCode(404);
    }

    @Test
    void updateNotFound() {
        Map<String, String> user = Map.of("_id", "122", "email", "2@hej.com", "password", "hej");
        given().contentType(ContentType.JSON).body(user).when().patch("auth/122").then().statusCode(404);
    }


    @Test
    void signInTest() {

        Map<String, String> user = Map.of("_id", "999", "email", "jakob@telia.com", "password", "password");

        given().contentType(MediaType.APPLICATION_JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(ContentType.JSON).body(user).post("auth/signin").then().statusCode(204);

    }


    @Test
    void signInEmailNotFoundTest() {

        Map<String, String> user = Map.of("_id", "999", "email", "jakob@telia.com", "password", "password");

        given().contentType(ContentType.JSON).body(user).post("auth/signin").then().statusCode(404);

    }

    @Test
    void signInWithBadPassword(){

        Map<String, String> user = Map.of("_id", "999", "email", "jakob2@telia.com", "password", "password");

        Map<String, String> loginAttempt = Map.of("_id", "999", "email", "jakob2@telia.com", "password", "cat");

        given().contentType(ContentType.JSON).body(user).post("auth/signup").then().statusCode(200);

        given().contentType(ContentType.JSON).body(loginAttempt).post("auth/signin").then().statusCode(400);
    }
}
