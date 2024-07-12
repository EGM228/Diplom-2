import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.junit.After;
import org.junit.Test;

public class UserCreationTest {
    private String accessToken;
    private final UserClient client = new UserClient();

    @DisplayName("Successfully creation of unique user check")
    @Test
    public void createUniqueUserTest(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        client.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        client.checkLoggedInSuccessfully(loginResponse);

        accessToken = client.getAccessToken(loginResponse);
    }

    @DisplayName("Can not create existing user")
    @Test
    public void createTwoSameUsersTest(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        client.checkCreatedSuccessfully(createResponse);

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        client.checkLoggedInSuccessfully(loginResponse);

        accessToken = client.getAccessToken(loginResponse);

        var user2 = new User(user.getEmail(),user.getPassword(), user.getName());
        ValidatableResponse createSecondUserResponse = client.createUser(user2);
        client.checkNotCreatedSameUser(createSecondUserResponse);
    }

    @After
    public void tearDown(){
        client.deleteUser(accessToken);
    }
}
