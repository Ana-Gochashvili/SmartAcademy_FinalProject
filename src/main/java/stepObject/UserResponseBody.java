package stepObject;

import io.restassured.response.Response;
import utils.UserDataBuilder;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserResponseBody {
    public UserDataBuilder getUserData(int userId, String userName, String firstName, String lastName, String email, String password, String phone, int userStatus) {
        return UserDataBuilder.builder()
                .id(userId)
                .username(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(userStatus)
                .build();
    }

    public Response createUser(Map<String, String> headers, UserDataBuilder userData, String endpoint) {
        return given()
                .headers(headers)
                .body(userData)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response getUser(Map<String, String> headers, String userName, String endpoint) {
        return given()
                .headers(headers)
                .pathParam("username", userName)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public int getResponseBodyIntElement(Response responseBody, String element) {
        return responseBody.getBody().jsonPath().getInt(element);
    }

    public String getResponseBodyStringElement(Response responseBody, String element) {
        return responseBody.getBody().jsonPath().getString(element);
    }

    public Response updateUser(Map<String, String> headers, String userName, UserDataBuilder userData, String endpoint) {
        return given()
                .headers(headers)
                .pathParam("username", userName)
                .body(userData)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response logUser(Map<String, String> headers, String username, String password, String endpoint) {
        return given()
                .headers(headers)
                .queryParams("username", username,
                        "password", password)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response loggingUserOut(Map<String, String> headers, String endpoint) {
        return given()
                .headers(headers)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response deleteUser(Map<String, String> headers, String userName, String endpoint) {
        return given()
                .headers(headers)
                .pathParam("username", userName)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }
}