package org.example;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


public class OrderClient {
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";

    @Step("Creating order")
    public ValidatableResponse CreateOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post("api/orders")
                .then().log().all();
    }

    @Step("Check order created successfully")
    public void checkCreatedOrder(ValidatableResponse orderResponse) {
        boolean created = orderResponse
                .extract()
                .path("success");
        assertTrue(created);
    }

    @Step("Check order not created with no ingredients")
    public void checkCreatedOrderNoHash(ValidatableResponse orderResponse) {
        boolean created = orderResponse
                .extract()
                .path("success");
        assertFalse(created);
    }

    @Step("Check order not created with bad hash ingredients")
    public void checkCreatedOrderBadHash(ValidatableResponse orderResponse) {
        orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Getting orders os auth user")
    public ValidatableResponse getOrdersAuth(String accessToken) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", accessToken)
                .baseUri(BASE_URI)
                .when()
                .get("api/orders")
                .then()
                .log().all();
    }

    @Step("Check of body of orders request")
    public void checkCorrectOrders(ValidatableResponse listResponse) {
        String listed = listResponse
                .extract().response().getBody().asString();
        assertNotNull(listed);
    }

    @Step("Check of no body of orders request")
    public void checkIncorrectOrders(ValidatableResponse listResponse) {
        listResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Getting orders of not auth user")
    public ValidatableResponse getOrdersNoAuth() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get("api/orders")
                .then()
                .log().all();
    }
}
