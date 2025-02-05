package utils;

import org.testng.annotations.DataProvider;

import static dataObject.DataObject.*;

public class TestDataProvider {
    @DataProvider(name = "invalidDataProvider", parallel = true)
    public Object[][] createInvalidOrderData() {
        return new Object[][]{
                {invalidOrderId, petId, quantity},
                {orderId, invalidPetId, quantity},
                {orderId, petId, invalidQuantity}
        };
    }
}
