import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCreationWithoutAuthTest {
    private final OrderClient orderClient = new OrderClient();

    @DisplayName("Creating order with correct ingredients auth-")
    @Test
    public void createOrderCorrectHash(){
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClient.CreateOrder(order);
        orderClient.checkCreatedOrder(orderResponse);
    }

    @DisplayName("Creating order with incorrect ingredients auth-")
    @Test
    public void createOrderIncorrectHash(){
        final String[] ingredients = {"61c0c5a71d1f820daaa6d", "61c0c5a71d1f81bdaaa6f", "61c0c5a71d1f81bdaaa72"};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClient.CreateOrder(order);
        orderClient.checkCreatedOrderBadHash(orderResponse);
    }

    @DisplayName("Creating order with no ingredients auth-")
    @Test
    public void createOrderNoHash(){
        final String[] ingredients = {};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClient.CreateOrder(order);
        orderClient.checkCreatedOrderNoHash(orderResponse);
    }
}