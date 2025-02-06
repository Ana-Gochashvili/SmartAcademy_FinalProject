package tests;

import dataObject.AlertMessages;
import dataObject.StatusCodesData;
import endpoints.Endpoint;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAuth;
import utils.UserDataBuilder;

import static dataObject.DataObject.*;

public class UserTests extends BaseAuth {
    private UserDataBuilder userData;

    @BeforeMethod
    public void buildUserData() {
        userData = userResponseBody.
                getUserData(userId, userName, firstName, lastName, email, password, phone, userStatus);
    }

    @Test(description = "Creates user")
    public void createUser() {
        Response responseBody = userResponseBody.
                createUser(headerConfig.getHeaders(), userData, Endpoint.User_Post);

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue());
    }

    @Test(description = "Get user by user name", priority = 1)
    public void getUserByUserName() {
        Response responseBody = userResponseBody.
                getUser(headerConfig.getHeaders(), userData.getUsername(), Endpoint.User_Get);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "username"), userData.getUsername(),
                "Incorrect username!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "firstName"), userData.getFirstName(),
                "Incorrect firstname!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "lastName"), userData.getLastName(),
                "Incorrect lastname!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "email"), userData.getEmail(),
                "Incorrect email!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "password"), userData.getPassword(),
                "Incorrect password!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "phone"), userData.getPhone(),
                "Incorrect phone number!");
        softAssert.assertEquals(userResponseBody.getResponseIntElement(responseBody, "userStatus"), userData.getUserStatus(),
                "Incorrect user status!");
        softAssert.assertAll();
    }

    @Test(description = "Updates user", priority = 2)
    public void updateUser() {
        userData.setPassword(newPassword);
        userData.setPhone(newPhone);

        Response responseBody = userResponseBody.
                updateUser(headerConfig.getHeaders(), userData.getUsername(), userData, Endpoint.User_Put);

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
    }

    @Test(description = "Searches updated user", priority = 3)
    public void searchesUpdatedUser() {
        Response responseBody = userResponseBody.
                getUser(headerConfig.getHeaders(), userData.getUsername(), Endpoint.User_Get);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "password"), newPassword,
                "Incorrect password!");
        softAssert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "phone"), newPhone,
                "Incorrect phone number!");
        softAssert.assertAll();
    }

    @Test(description = "Logs user into the System", priority = 4)
    public void logUserIntoTheSystem() {
        Response responseBody = userResponseBody.
                logUser(headerConfig.getHeaders(), userData.getUsername(), userData.getPassword(), Endpoint.User_Get_Login);

        String responseMessage = userResponseBody.getResponseStringElement(responseBody, "message");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertNotNull(responseMessage,
                "Message body should not be null");
        softAssert.assertTrue(responseMessage.contains("logged in user session"),
                "Incorrect login success text!");
        softAssert.assertAll();
    }

    @Test(description = "Logs out current logged in user session", priority = 5)
    public void loggingUserOutOfTheSystem() {
        Response responseBody = userResponseBody.
                loggingUserOut(headerConfig.getHeaders(), Endpoint.User_Get_Logout);

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        Assert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "message"), AlertMessages.logoutSuccessMessage,
                "Incorrect message text!");
    }

    @Test(description = "Delete user", priority = 6)
    public void DeleteUser() {
        Response responseBody = userResponseBody.
                deleteUser(userData.getUsername(), Endpoint.User_Delete);

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        Assert.assertEquals(userResponseBody.getResponseStringElement(responseBody, "message"), userData.getUsername(),
                "Incorrect message!");
    }
}
