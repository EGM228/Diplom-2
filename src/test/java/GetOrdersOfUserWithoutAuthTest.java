import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetOrdersOfUserWithoutAuthTest {
    private final OrderClient orderClient = new OrderClient();

    @DisplayName("getting auth user orders")
    @Test
    public void getAuthUserOrders(){
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClient.CreateOrder(order);
        orderClient.checkCreatedOrder(orderResponse);

        ValidatableResponse listResponse = orderClient.getOrdersNoAuth();
        orderClient.checkIncorrectOrders(listResponse);
    }
}
