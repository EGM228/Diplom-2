import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.example.UserCredentials;
import org.junit.Test;

public class UserLoginWrongParamsTest {
    private final UserClient client = new UserClient();

    @DisplayName("User can logg in with incorrect params")
    @Test
    public void canNotLoggInWithWrongParams(){
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        client.checkCreatedSuccessfully(createResponse);

        user.setPassword("0978");

        var creds = UserCredentials.from(user);
        ValidatableResponse loginResponse = client.loginUser((UserCredentials) creds);
        client.checkNotLoggedInWithWrongParams(loginResponse);
    }
}
