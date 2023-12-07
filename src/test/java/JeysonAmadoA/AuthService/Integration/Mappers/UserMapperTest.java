package JeysonAmadoA.AuthService.Integration.Mappers;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

@SpringBootTest
public class UserMapperTest {

    private final UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void UserDtoToEntityTest()
    {
        UserDto userDto = new UserDto();
        userDto.setName("Jeyson");
        userDto.setLastName("Amado");
        LocalDateTime date = LocalDateTime.now();
        userDto.setBirthDay(date);
        userDto.setPassword("password");
        userDto.setEmail("jeyson@example.com");

        UserEntity user = this.userMapper.toEntity(userDto);
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getBirthDay(), user.getBirthDay());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    public void UserEntityToDtoTest()
    {
        UserEntity user = new UserEntity();
        user.setName("Jeyson");
        user.setLastName("Amado");
        LocalDateTime date = LocalDateTime.now();
        user.setBirthDay(date);
        user.setPassword("password");
        user.setEmail("jeyson@example.com");

        UserDto userDto = this.userMapper.toDto(user);
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getBirthDay(), userDto.getBirthDay());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getEmail(), userDto.getEmail());
    }
}
