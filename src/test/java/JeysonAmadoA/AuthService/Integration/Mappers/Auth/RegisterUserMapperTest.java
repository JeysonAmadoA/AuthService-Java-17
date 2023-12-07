package JeysonAmadoA.AuthService.Integration.Mappers.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Mappers.Auth.RegisterUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegisterUserMapperTest {

    private final RegisterUserMapper registerUserMapper;

    @Autowired
    public RegisterUserMapperTest(RegisterUserMapper registerUserMapper) {
        this.registerUserMapper = registerUserMapper;
    }


    @Test
    public void RegisterUserDtoToEntityTest()
    {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName("Jeyson");
        registerUserDto.setLastName("Amado");
        LocalDate date = LocalDate.now();
        registerUserDto.setBirthDay(date);
        registerUserDto.setPassword("password");
        registerUserDto.setConfirmPassword("password");
        registerUserDto.setEmail("jeyson@example.com");

        UserEntity user = this.registerUserMapper.toEntity(registerUserDto);
        assertEquals(registerUserDto.getName(), user.getName());
        assertEquals(registerUserDto.getLastName(), user.getLastName());
        assertEquals(registerUserDto.getBirthDay(), user.getBirthDay());
        assertEquals(registerUserDto.getPassword(), user.getPassword());
        assertEquals(registerUserDto.getEmail(), user.getEmail());
    }

}
