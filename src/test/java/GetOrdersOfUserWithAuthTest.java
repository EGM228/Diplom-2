import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.*;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetOrdersOfUserWithAuthTest {
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private String accessToken;

    @DisplayName("getting auth user orders")
    @Test
    public void getAuthUserOrders(){
        final String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"};
        var user = User.random();
        ValidatableResponse createResponse = userClient.createUser(user);
        userClient.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = userClient.loginUser((UserCredentials) creds);
        userClient.checkLoggedInSuccessfully(loginResponse);

        accessToken = userClient.getAccessToken(loginResponse);

        List<String> list = new ArrayList<>(Arrays.asList(ingredients));
        var order = new Order(list);
        ValidatableResponse orderResponse = orderClient.CreateOrder(order);
        orderClient.checkCreatedOrder(orderResponse);

        ValidatableResponse listResponse = orderClient.getOrdersAuth(accessToken);
        orderClient.checkCorrectOrders(listResponse);
    }

    @After
    public void tearDown(){
        userClient.deleteUser(accessToken);
    }
}