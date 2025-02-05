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
        userData = userResponseBody.getUserData(userId, userName, firstName, lastName, email, password, phone, userStatus);
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

        String actualUsername = userResponseBody.getResponseBodyStringElement(responseBody, "username");
        String actualFirstName = userResponseBody.getResponseBodyStringElement(responseBody, "firstName");
        String actualLastName = userResponseBody.getResponseBodyStringElement(responseBody, "lastName");
        String actualEmail = userResponseBody.getResponseBodyStringElement(responseBody, "email");
        String actualPassword = userResponseBody.getResponseBodyStringElement(responseBody, "password");
        String actualPhone = userResponseBody.getResponseBodyStringElement(responseBody, "phone");
        int actualUserStatus = userResponseBody.getResponseBodyIntElement(responseBody, "userStatus");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertEquals(actualUsername, userData.getUsername(),
                "Incorrect username!");
        softAssert.assertEquals(actualFirstName, userData.getFirstName(),
                "Incorrect firstname!");
        softAssert.assertEquals(actualLastName, userData.getLastName(),
                "Incorrect lastname!");
        softAssert.assertEquals(actualEmail, userData.getEmail(),
                "Incorrect email!");
        softAssert.assertEquals(actualPassword, userData.getPassword(),
                "Incorrect password!");
        softAssert.assertEquals(actualPhone, userData.getPhone(),
                "Incorrect phone number!");
        softAssert.assertEquals(actualUserStatus, userData.getUserStatus(),
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

        String actualPassword = userResponseBody.getResponseBodyStringElement(responseBody, "password");
        String actualPhone = userResponseBody.getResponseBodyStringElement(responseBody, "phone");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertEquals(actualPassword, newPassword,
                "Incorrect password!");
        softAssert.assertEquals(actualPhone, newPhone,
                "Incorrect phone number!");
        softAssert.assertAll();
    }

    @Test(description = "Logs user into the System", priority = 4)
    public void logUserIntoTheSystem() {
        Response responseBody = userResponseBody.
                logUser(headerConfig.getHeaders(), userData.getUsername(), userData.getPassword(), Endpoint.User_Get_Login);

        String responseMessage = userResponseBody.getResponseBodyStringElement(responseBody, "message");

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

        String responseMessage = userResponseBody.getResponseBodyStringElement(responseBody, "message");

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        Assert.assertEquals(responseMessage, AlertMessages.logoutSuccessMessage,
                "Incorrect message text!");
    }

    @Test(description = "Delete user", priority = 6)
    public void DeleteUser() {
        Response responseBody = userResponseBody.
                deleteUser(headerConfig.getHeaders(), userData.getUsername(), Endpoint.User_Delete);

        String responseMessage = userResponseBody.getResponseBodyStringElement(responseBody, "message");

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        Assert.assertEquals(responseMessage, userData.getUsername(),
                "Incorrect message!");
    }
}
