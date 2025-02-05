package stepObject;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.OrderDataBuilder;

import java.util.Date;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class StoreResponseBody {
    public OrderDataBuilder getOrderDetails(int orderId, int petId, int quantity, Date orderDate, String orderStatus, Boolean complete) {
        return OrderDataBuilder.builder()
                .id(orderId)
                .petId(petId)
                .quantity(quantity)
                .shipDate(orderDate)
                .status(orderStatus)
                .complete(complete)
                .build();
    }

    public Response placeAnOrder(Map<String, String> headers, Object orderDetails, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .body(orderDetails)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public Response getOrderById(Map<String, String> headers, int orderId, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .pathParam("orderId", orderId)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public int getResponseIntElement(Response responseBody, String element) {
        return responseBody.getBody().jsonPath().getInt(element);
    }

    public String getResponseStringElement(Response responseBody, String element) {
        return responseBody.getBody().jsonPath().getString(element);
    }

    public Response deleteOrder(Map<String, String> headers, int orderId, String endpoint) {
        return given()
                .filter(new AllureRestAssured())
                .headers(headers)
                .pathParam("orderId", orderId)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    public JSONObject getOrderRequestBody(Object orderId, Object petId, Object quantity) {
        JSONObject orderRequestBody = new JSONObject();
        orderRequestBody.put("id", orderId);
        orderRequestBody.put("petId", petId);
        orderRequestBody.put("quantity", quantity);
        return orderRequestBody;
    }
}
