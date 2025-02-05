package utils;

import dataObject.HeadersConfiguration;
import endpoints.Endpoint;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import stepObject.StoreResponseBody;
import stepObject.UserResponseBody;

public class BaseAuth {
    protected HeadersConfiguration headerConfig;
    protected StoreResponseBody storeResponseBody;
    protected UserResponseBody userResponseBody;

    @BeforeMethod
    public void setBaseURL() {
        RestAssured.baseURI = Endpoint.Base_URL;
        headerConfig = new HeadersConfiguration();
        storeResponseBody = new StoreResponseBody();
        userResponseBody = new UserResponseBody();
    }
}
