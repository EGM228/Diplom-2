import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.junit.After;
import org.junit.Test;

public class ChangeUserDataWithAuthTest {
    private String accessToken;
    private final UserClient client = new UserClient();

    @DisplayName("Changing data with authorization")
    @Test
    public void changeUserDataTest(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        client.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        client.checkLoggedInSuccessfully(loginResponse);

        accessToken = client.getAccessToken(loginResponse);

        var user2 = new User(RandomStringUtils.randomAlphabetic(7) + "@gmail.com", "9873", "Jack");
        ValidatableResponse changeResponse =  client.changeUserData(accessToken, user2);
        client.checkChanged(changeResponse);
    }

    @After
    public void tearDown(){
        client.deleteUser(accessToken);
    }
}
