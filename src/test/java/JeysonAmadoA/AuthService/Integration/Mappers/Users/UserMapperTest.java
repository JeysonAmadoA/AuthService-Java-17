package JeysonAmadoA.AuthService.Integration.Mappers.Users;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

@SpringBootTest
public class UserMapperTest {

    private final UserMapper userMapper;

    @Autowired
    public UserMapperTest(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void UserDtoToEntityTest() {
        UserDto userDto = new UserDto();
        userDto.setName("Jeyson");
        userDto.setLastName("Amado");
        userDto.setBirthDay(LocalDate.now());
        userDto.setEmail("jeyson@example.com");

        UserEntity user = this.userMapper.toEntity(userDto);
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getBirthDay(), user.getBirthDay());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    public void UserEntityToDtoTest() {
        UserEntity user = new UserEntity();
        user.setName("Jeyson");
        user.setLastName("Amado");
        LocalDate date = LocalDate.now();
        user.setBirthDay(date);
        user.setEmail("jeyson@example.com");

        UserDto userDto = this.userMapper.toDto(user);
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getBirthDay(), userDto.getBirthDay());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    public void updateEntityFromDtoTest() {
        LocalDate date = LocalDate.now();

        UserEntity user = new UserEntity();
        user.setName("Mauro");
        user.setLastName("Lombardo");
        user.setBirthDay(date);
        user.setEmail("mauro@example.com");

        UserDto userDto = new UserDto();
        userDto.setName("Jeyson");
        userDto.setLastName("Amado");
        userDto.setBirthDay(LocalDate.now());
        userDto.setEmail("jeyson@example.com");

        UserEntity userUpdated = this.userMapper.update(user, userDto);
        assertEquals("Jeyson", userUpdated.getName());
        assertEquals("Amado", userUpdated.getLastName());
        assertEquals("jeyson@example.com", userUpdated.getEmail());
    }
}
