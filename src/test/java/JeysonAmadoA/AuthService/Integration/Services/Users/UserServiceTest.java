package JeysonAmadoA.AuthService.Integration.Services.Users;

import JeysonAmadoA.AuthService.Dto.Users.UpdatePasswordDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import JeysonAmadoA.AuthService.Services.Users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void registerUserTest(){

        UserEntity user = new UserEntity();
        UserDto userDto = new UserDto();

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto userFound = userService.getUserById(1L);

        verify(userRepo, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
        assertEquals(userDto, userFound);
    }

    @Test
    public void getUserByIdNotFoundTest(){

        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        UserDto userFound = userService.getUserById(1L);

        verify(userRepo, times(1)).findById(1L);
        assertNull(userFound);
    }

    @Test
    public void getAllUsersTest(){

        UserEntity userOne = new UserEntity();
        UserEntity userTwo = new UserEntity();

        UserDto userDtoOne = new UserDto();
        UserDto userDtoTwo = new UserDto();

        List<UserEntity> userEntityList = Arrays.asList(userOne, userTwo);
        List<UserDto> taskDtoList = Arrays.asList(userDtoOne, userDtoTwo);

        when(userRepo.findAll()).thenReturn(userEntityList);
        when(userMapper.toDto(userOne)).thenReturn(userDtoOne);
        when(userMapper.toDto(userTwo)).thenReturn(userDtoTwo);

        List<UserDto> allUsers = userService.getAllUsers();

        verify(userRepo, times(1)).findAll();
        verify(userMapper, times(1)).toDto(userOne);
        verify(userMapper, times(1)).toDto(userTwo);
        assertEquals(taskDtoList, allUsers);
    }

    @Test
    public void getUsersByEmailTest(){

        UserEntity userOne = new UserEntity();
        UserEntity userTwo = new UserEntity();

        UserDto userDtoOne = new UserDto();
        UserDto userDtoTwo = new UserDto();

        List<UserEntity> userEntityList = Arrays.asList(userOne, userTwo);
        List<UserDto> taskDtoList = Arrays.asList(userDtoOne, userDtoTwo);

        when(userRepo.findByEmailLike(anyString())).thenReturn(userEntityList);
        when(userMapper.toDto(userOne)).thenReturn(userDtoOne);
        when(userMapper.toDto(userTwo)).thenReturn(userDtoTwo);

        List<UserDto> allUsers = userService.filterUsersByEmail("jeyson.amado@example.com");

        verify(userRepo, times(1)).findByEmailLike("jeyson.amado@example.com");
        verify(userMapper, times(1)).toDto(userOne);
        verify(userMapper, times(1)).toDto(userTwo);
        assertEquals(taskDtoList, allUsers);
    }

    @Test
    public void getUsersByNameOrLastnameTest(){

        UserEntity userOne = new UserEntity();
        UserEntity userTwo = new UserEntity();

        UserDto userDtoOne = new UserDto();
        UserDto userDtoTwo = new UserDto();

        List<UserEntity> userEntityList = Arrays.asList(userOne, userTwo);
        List<UserDto> taskDtoList = Arrays.asList(userDtoOne, userDtoTwo);

        when(userRepo.findByNameLikeOrLastNameLike(anyString())).thenReturn(userEntityList);
        when(userMapper.toDto(userOne)).thenReturn(userDtoOne);
        when(userMapper.toDto(userTwo)).thenReturn(userDtoTwo);

        List<UserDto> allUsers = userService.filterUsersByNameOrLastname("jeyson.amado@example.com");

        verify(userRepo, times(1)).findByNameLikeOrLastNameLike("jeyson.amado@example.com");
        verify(userMapper, times(1)).toDto(userOne);
        verify(userMapper, times(1)).toDto(userTwo);
        assertEquals(taskDtoList, allUsers);

    }

    @Test
    public void updateUserTest() throws UpdateUserException {

        UserEntity user = new UserEntity();
        UserDto userDto = new UserDto();

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.update(any(UserEntity.class), any(UserDto.class))).thenReturn(user);
        when(userRepo.save(any(UserEntity.class))).thenReturn(user);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto userUpdated = userService.updateUser(userDto, 1L);

        verify(userRepo, times(1)).findById(1L);
        verify(userMapper, times(1)).update(user, userDto);
        verify(userRepo, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
        assertEquals(userUpdated, userDto);
    }

    @Test
    public void updateUserNotFoundTest() throws UpdateUserException {

        UserDto userDto = new UserDto();

        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        UserDto userUpdated = userService.updateUser(userDto, 1L);

        verify(userRepo, times(1)).findById(1L);
        assertNull(userUpdated);
    }

    @Test
    public void updatePasswordTest() throws UpdateUserException {

        UserEntity user = new UserEntity();
        user.setPassword((new BCryptPasswordEncoder()).encode("password123"));

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("password123");
        updatePasswordDto.setNewPassword("password321");
        updatePasswordDto.setConfirmPassword("password321");

        UserDto userDto = new UserDto();

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.save(any(UserEntity.class))).thenReturn(user);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto userUpdated = userService.updatePassword(updatePasswordDto, 1L);

        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).save(user);
        verify(userMapper, times(1)).toDto(user);
        assertEquals(userUpdated, userDto);
    }

    @Test
    public void updatePasswordOldPasswordDontMatchTest() {

        UserEntity user = new UserEntity();
        user.setPassword((new BCryptPasswordEncoder()).encode("password122"));

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("password123");
        updatePasswordDto.setNewPassword("password321");
        updatePasswordDto.setConfirmPassword("password321");

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        UpdateUserException exception = assertThrows(UpdateUserException.class, ()
                -> userService.updatePassword(updatePasswordDto, 1L));

        verify(userRepo, times(1)).findById(1L);
        assertEquals("Error al actualizar usuario. No coincide con la contraseña actual", exception.getMessage());
    }

    @Test
    public void updatePasswordNewPasswordDontMatchTest() {

        UserEntity user = new UserEntity();
        user.setPassword((new BCryptPasswordEncoder()).encode("password123"));

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("password123");
        updatePasswordDto.setNewPassword("password321");
        updatePasswordDto.setConfirmPassword("password322");

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        UpdateUserException exception = assertThrows(UpdateUserException.class, ()
                -> userService.updatePassword(updatePasswordDto, 1L));

        verify(userRepo, times(1)).findById(1L);
        assertEquals("Error al actualizar usuario. La contraseña nueva no coincide", exception.getMessage());
    }

    @Test
    public void deleteUserTest() throws DeleteUserException {
        UserEntity user = new UserEntity();

        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.save(any(UserEntity.class))).thenReturn(user);

        boolean isDeleted = userService.deleteUser(1L);

        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).save(user);
        assertTrue(isDeleted);
    }

    @Test
    public void deleteUserNotFoundTest() throws DeleteUserException {

        when(userRepo.findById(anyLong())).thenReturn(Optional.empty());

        boolean userUpdated = userService.deleteUser(1L);

        verify(userRepo, times(1)).findById(1L);
        assertFalse(userUpdated);
    }
}
