package org.example;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

public class UserClient {
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    private static final String COURIER_AUTH_PATH = "/api/auth";

    @Step("Creating correct user")
    public ValidatableResponse createUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(COURIER_AUTH_PATH+"/register")
                .then().log().all();
    }

    @Step("Check of creating of correct user")
    public void checkCreatedSuccessfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
        assertTrue(created);
    }

    @Step("Getting access token")
    public String getAccessToken(ValidatableResponse createResponse) {
        String accessToken;
        return accessToken = createResponse
                .extract()
                .path("accessToken");
    }

    @Step("Deleting user")
    public void deleteUser(String accessToken) {
        given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization",accessToken)
                .baseUri(BASE_URI)
                .when()
                .delete(COURIER_AUTH_PATH+"/user")
                .then().log().all();
    }

    @Step("Logging user")
    public ValidatableResponse loginUser(UserCredentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(creds)
                .when()
                .post(COURIER_AUTH_PATH+"/login")
                .then().log().all();
    }

    @Step("Check logging successfully")
    public void checkLoggedInSuccessfully(ValidatableResponse loginResponse) {
        boolean logged = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
        assertTrue(logged);
    }

    @Step("Check can not create same user")
    public void checkNotCreatedSameUser(ValidatableResponse createSecondUserResponse) {
        boolean created = createSecondUserResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(created);
    }

    @Step("Check of not creating user without required fields")
    public void checkNotCreatedWithoutOneField(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(created);
    }

    @Step("Check can not login with wrong params")
    public void checkNotLoggedInWithWrongParams(ValidatableResponse loginResponse) {
        boolean logged = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(logged);
    }

    @Step("Changing user data")
    public ValidatableResponse changeUserData(String accessToken, User user2) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("authorization", accessToken)
                .baseUri(BASE_URI)
                .body(user2)
                .when()
                .patch(COURIER_AUTH_PATH + "/user")
                .then()
                .log().all();
    }

    @Step("Check of changed user data")
    public void checkChanged(ValidatableResponse changeResponse) {
        boolean changed = changeResponse
                .extract()
                .path("success");
        assertTrue(changed);
    }

    @Step("Check not auth user can not change data")
    public void checkChangedWithoutAuth(ValidatableResponse changeResponse) {
        boolean changed = changeResponse
                .extract()
                .path("success");
        assertFalse(changed);
    }

    @Step("Changing not auth user data")
    public ValidatableResponse changeUserDataWithoutAuth(User user2) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user2)
                .when()
                .patch(COURIER_AUTH_PATH + "/user")
                .then()
                .log().all();
    }
}