package JeysonAmadoA.AuthService.Unit.Dto.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterUserDtoTest {

    @Test
    public void settersAndGettersTest(){

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName("Jeyson");
        registerUserDto.setLastName("Amado");
        LocalDate date = LocalDate.now();
        registerUserDto.setBirthDay(date);
        registerUserDto.setPassword("password");
        registerUserDto.setConfirmPassword("password");
        registerUserDto.setEmail("jeyson@example.com");

        assertEquals("Jeyson", registerUserDto.getName());
        assertEquals("Amado", registerUserDto.getLastName());
        assertEquals(date, registerUserDto.getBirthDay());
        assertEquals("password", registerUserDto.getPassword());
        assertEquals("password", registerUserDto.getConfirmPassword());
        assertEquals("jeyson@example.com", registerUserDto.getEmail());
    }
}
