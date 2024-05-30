import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.User;
import org.example.UserClient;
import org.junit.Test;

public class UserCreationWithoutOneFieldTest {

    private final UserClient client = new UserClient();

    @DisplayName("Can not create user without one required field")
    @Test
    public void createUserWithoutOneRequiredField(){
        var user = new User("","1234", "Jack");
        ValidatableResponse createResponse = client.createUser(user);
        client.checkNotCreatedWithoutOneField(createResponse);
    }
}
