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
import utils.OrderDataBuilder;

import static dataObject.DataObject.*;

public class StoreTests extends BaseAuth {
    private OrderDataBuilder orderDetails;

    @BeforeMethod
    public void buildOrderData() {
        orderDetails = storeResponseBody.
                getOrderDetails(orderId, petId, quantity, orderDate, orderStatus, true);
    }

    @Test(description = "Places An Order For A Pet")
    public void placeAnOrder() {
        Response responseBody = storeResponseBody.placeAnOrder(headerConfig.getHeaders(), orderDetails, Endpoint.Store_Post_Order);
        int actualOrderId = storeResponseBody.getResponseBodyIntElement(responseBody, "id");
        int actualPetId = storeResponseBody.getResponseBodyIntElement(responseBody, "petId");
        int actualQuantity = storeResponseBody.getResponseBodyIntElement(responseBody, "quantity");
        String actualOrderStatus = storeResponseBody.getResponseBodyStringElement(responseBody, "status");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertEquals(actualOrderId, orderDetails.getId(),
                "Incorrect order id!");
        softAssert.assertEquals(actualPetId, orderDetails.getPetId(),
                "Incorrect pet id!");
        softAssert.assertEquals(actualQuantity, orderDetails.getQuantity(),
                "Incorrect quantity!");
        softAssert.assertEquals(actualOrderStatus, orderDetails.getStatus(),
                "Incorrect order status!");
        softAssert.assertAll();
    }


    @Test(description = "Finds purchase order by ID", priority = 1)
    public void findPurchaseOrderByID() {
        Response responseBody = storeResponseBody.getOrderById(headerConfig.getHeaders(), orderDetails.getId(), Endpoint.Store_Get_Order);
        int actualOrderId = storeResponseBody.getResponseBodyIntElement(responseBody, "id");
        int actualPetId = storeResponseBody.getResponseBodyIntElement(responseBody, "petId");
        int actualQuantity = storeResponseBody.getResponseBodyIntElement(responseBody, "quantity");
        String actualOrderStatus = storeResponseBody.getResponseBodyStringElement(responseBody, "status");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.getStatusCode(), StatusCodesData.SUCCESS_200.getValue(),
                "Incorrect status code!");
        softAssert.assertEquals(actualOrderId, orderDetails.getId(),
                "Incorrect order id!");
        softAssert.assertEquals(actualPetId, orderDetails.getPetId(),
                "Incorrect pet id!");
        softAssert.assertEquals(actualQuantity, orderDetails.getQuantity(),
                "Incorrect quantity!");
        softAssert.assertEquals(actualOrderStatus, orderDetails.getStatus(),
                "Incorrect order status!");
        softAssert.assertAll();
    }


    @Test(description = "Deletes purchase order by ID", priority = 2)
    public void deletePurchaseOrder() {
        Response responseBody = storeResponseBody.deleteOrder(headerConfig.getHeaders(), orderDetails.getId(), Endpoint.Store_Delete_Order);
        String responseMessage = storeResponseBody.getResponseBodyStringElement(responseBody, "message");

        Assert.assertEquals(responseMessage, String.valueOf(orderDetails.getId()),
                "Incorrect message text!");
    }


    @Test(description = "Checks for deletion of non-existent purchase order by ID", priority = 3)
    public void checkForDeletionNonExistentPurchaseOrder() {
        Response responseBody = storeResponseBody.deleteOrder(headerConfig.getHeaders(), orderDetails.getId(), Endpoint.Store_Delete_Order);
        String responseMessage = storeResponseBody.getResponseBodyStringElement(responseBody, "message");

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.Order_Not_Found_404.getValue(),
                "Incorrect status code!");
        Assert.assertEquals(responseMessage, AlertMessages.incorrectRequestErrorMessage,
                "Incorrect message text!");
    }


    @Test(description = "Checks search for deleted purchase order by ID", priority = 4)
    public void checkSearchForDeletedPurchaseOrder() {
        Response responseBody = storeResponseBody.getOrderById(headerConfig.getHeaders(), orderDetails.getId(), Endpoint.Store_Get_Order);
        String responseMessage = storeResponseBody.getResponseBodyStringElement(responseBody, "message");

        Assert.assertEquals(responseBody.getStatusCode(), StatusCodesData.Order_Not_Found_404.getValue(),
                "Incorrect status code!");
        Assert.assertTrue(responseMessage.equalsIgnoreCase(AlertMessages.incorrectRequestErrorMessage),
                "Incorrect message text!");
    }
}
