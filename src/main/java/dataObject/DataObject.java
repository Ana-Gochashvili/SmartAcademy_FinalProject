package dataObject;

import com.github.javafaker.Faker;

import java.util.Date;

public interface DataObject {
    Faker faker = new Faker();
    Date orderDate = new Date();
    String orderStatus = "placed";
    int
            orderId = faker.number().numberBetween(1, 100),
            petId = faker.number().numberBetween(1, 100),
            quantity = faker.number().numberBetween(1, 10);

    String
            invalidOrderId = faker.bothify("??##").toUpperCase(),
            invalidPetId = faker.bothify("???##").toUpperCase(),
            invalidQuantity = faker.bothify("???##").toUpperCase();

    int userId = faker.number().numberBetween(1, 100);
    int userStatus = 1;
    String userName = faker.name().username().toUpperCase(),
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            password = faker.internet().password(8, 15, true, true),
            phone = faker.numerify("5## ## ## ##"),
            newPhone = faker.numerify("5## ## ## ##"),
            newPassword = faker.internet().password(8, 15, true, true);
}
