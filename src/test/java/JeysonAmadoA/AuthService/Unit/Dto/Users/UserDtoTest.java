package JeysonAmadoA.AuthService.Unit.Dto.Users;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserDtoTest {

    @Test
    public void settersAndGettersTest(){

        UserDto user = new UserDto();
        user.setName("Jeyson");
        user.setLastName("Amado");
        LocalDateTime date = LocalDateTime.now();
        user.setBirthDay(date);
        user.setPassword("password");
        user.setEmail("jeyson@example.com");

        assertEquals("Jeyson", user.getName());
        assertEquals("Amado", user.getLastName());
        assertEquals(date, user.getBirthDay());
        assertEquals("password", user.getPassword());
        assertEquals("jeyson@example.com", user.getEmail());
    }
}
