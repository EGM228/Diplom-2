import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.junit.After;
import org.junit.Test;

public class UserLoginTest {
    private String accessToken;
    private final UserClient client = new UserClient();

    @DisplayName("User can logg in with correct params")
    @Test
    public void canLoggInWithCorrectParams(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        client.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        client.checkLoggedInSuccessfully(loginResponse);

        accessToken = client.getAccessToken(loginResponse);
    }

    @After
    public void tearDown(){
        client.deleteUser(accessToken);
    }
}
