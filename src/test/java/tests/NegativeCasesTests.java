package tests;

import dataObject.AlertMessages;
import dataObject.StatusCodesData;
import endpoints.Endpoint;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseAuth;

import static dataObject.DataObject.*;

public class NegativeCasesTests extends BaseAuth {

    @DataProvider(name = "invalidOrderData", parallel = true)
    public Object[][] createInvalidOrderData() {
        return new Object[][]{
                {invalidOrderId, petId, quantity},
                {orderId, invalidPetId, quantity},
                {orderId, petId, invalidQuantity}
        };
    }

    @Test(description = "Places An Order For A Pet With Invalid Data", dataProvider = "invalidOrderData")
    public void placeAnOrder(Object orderId, Object petId, Object quantity) {
        JSONObject orderRequestBody = storeResponseBody.
                getOrderRequestBody(orderId, petId, quantity);

        Response responseBody = storeResponseBody.
                placeAnOrder(headerConfig.getHeaders(), orderRequestBody.toString(), Endpoint.Store_Post_Order);

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.Internal_Server_Error_500.getValue(),
                "Incorrect status code!");
        Assert.assertEquals(responseBody.jsonPath().getString("message"), AlertMessages.internalServerErrorMessage,
                "Incorrect message text!");
    }
}
