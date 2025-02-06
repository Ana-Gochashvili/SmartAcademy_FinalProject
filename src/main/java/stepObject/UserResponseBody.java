package stepObject;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import utils.UserDataBuilder;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserResponseBody {

    @Step("Returns object with user data")
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

    @Step("Sends POST request to create user")
    public Response createUser(Map<String, String> headers, UserDataBuilder userData, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .body(userData)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Sends GET request to fetch user data by username: {username}")
    public Response getUser(Map<String, String> headers, String userName, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .pathParam("username", userName)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Returns integer value of response body element")
    public int getResponseIntElement(Response responseBody, String element) {
        return responseBody.getBody().jsonPath().getInt(element);
    }

    @Step("Returns string value of response body element")
    public String getResponseStringElement(Response responseBody, String element) {
        return responseBody.getBody().jsonPath().getString(element);
    }

    @Step("Sends PUT request to update user data for username: {username}")
    public Response updateUser(Map<String, String> headers, String userName, UserDataBuilder userData, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
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

    @Step("Sends GET request to login the user using username and password")
    public Response logUser(Map<String, String> headers, String username, String password, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .queryParams("username", username,
                        "password", password)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Sends GET request to logout the user")
    public Response loggingUserOut(Map<String, String> headers, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Sends DELETE request to delete the user")
    public Response deleteUser(String userName, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .pathParam("username", userName)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }
}