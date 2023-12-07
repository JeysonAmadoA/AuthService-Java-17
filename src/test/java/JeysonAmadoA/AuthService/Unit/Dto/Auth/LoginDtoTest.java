package JeysonAmadoA.AuthService.Unit.Dto.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginDtoTest {

    @Test
    public void settersAndGettersTest(){

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jeyson@example.com");
        loginDto.setPassword("password");

        assertEquals("jeyson@example.com", loginDto.getEmail());
        assertEquals("password", loginDto.getPassword());
    }
}
