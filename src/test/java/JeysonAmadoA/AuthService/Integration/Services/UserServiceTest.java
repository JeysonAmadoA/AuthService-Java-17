package JeysonAmadoA.AuthService.Integration.Services;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;
import JeysonAmadoA.AuthService.Mappers.Auth.RegisterUserMapper;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import JeysonAmadoA.AuthService.Services.Users.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private RegisterUserMapper registerUserMapper;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

//    @Test
//    public void registerUserTest() throws RegisterUserException {
//
//        RegisterUserDto registerUserDto = new RegisterUserDto();
//        registerUserDto.setPassword("Password");
//
//        UserDto userDto = new UserDto();
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setPassword("Password");
//
//        when(this.registerUserMapper.toEntity(any(RegisterUserDto.class))).thenReturn(userEntity);
//        when(this.userRepo.save(any(UserEntity.class))).thenReturn(userEntity);
//        when(this.userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);
//
//        UserDto userRegistered = this.userService.registerUser(registerUserDto);
//
//        verify(this.registerUserMapper, times(1)).toEntity(registerUserDto);
//        verify(this.userRepo, times(1)).save(userEntity);
//        verify(this.userMapper, times(1)).toDto(userEntity);
//        assertEquals(userDto, userRegistered);
//    }

    @Test
    public void getUserByIdTest(){

        UserEntity user = new UserEntity();
        UserDto userDto = new UserDto();

        when(this.userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(this.userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto userFound = this.userService.getUserById(1L);

        verify(this.userRepo, times(1)).findById(1L);
        verify(this.userMapper, times(1)).toDto(user);
        assertEquals(userDto, userFound);
    }

    @Test
    public void getUserByIdNotFoundTest(){

        when(this.userRepo.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        UserDto userFound = this.userService.getUserById(1L);

        verify(this.userRepo, times(1)).findById(1L);
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

        when(this.userRepo.findAll()).thenReturn(userEntityList);
        when(this.userMapper.toDto(userOne)).thenReturn(userDtoOne);
        when(this.userMapper.toDto(userTwo)).thenReturn(userDtoTwo);

        List<UserDto> allUsers = this.userService.getAllUsers();

        verify(this.userRepo, times(1)).findAll();
        verify(this.userMapper, times(1)).toDto(userOne);
        verify(this.userMapper, times(1)).toDto(userTwo);
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

        when(this.userRepo.findByEmailLike(anyString())).thenReturn(userEntityList);
        when(this.userMapper.toDto(userOne)).thenReturn(userDtoOne);
        when(this.userMapper.toDto(userTwo)).thenReturn(userDtoTwo);

        List<UserDto> allUsers = this.userService.getUsersByEmail("jeyson.amado@example.com");

        verify(this.userRepo, times(1)).findByEmailLike("jeyson.amado@example.com");
        verify(this.userMapper, times(1)).toDto(userOne);
        verify(this.userMapper, times(1)).toDto(userTwo);
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

        when(this.userRepo.findByNameLikeOrLastNameLike(anyString())).thenReturn(userEntityList);
        when(this.userMapper.toDto(userOne)).thenReturn(userDtoOne);
        when(this.userMapper.toDto(userTwo)).thenReturn(userDtoTwo);

        List<UserDto> allUsers = this.userService.getUsersByNameOrLastname("jeyson.amado@example.com");

        verify(this.userRepo, times(1)).findByNameLikeOrLastNameLike("jeyson.amado@example.com");
        verify(this.userMapper, times(1)).toDto(userOne);
        verify(this.userMapper, times(1)).toDto(userTwo);
        assertEquals(taskDtoList, allUsers);

    }

    @Test
    public void updateUserTest() throws UpdateUserException {

        UserEntity user = new UserEntity();
        UserDto userDto = new UserDto();

        when(this.userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(this.userMapper.update(any(UserEntity.class), any(UserDto.class))).thenReturn(user);
        when(this.userRepo.save(any(UserEntity.class))).thenReturn(user);
        when(this.userMapper.toDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto userUpdated = this.userService.updateUser(userDto, 1L);

        verify(this.userRepo, times(1)).findById(1L);
        verify(this.userMapper, times(1)).update(user, userDto);
        verify(this.userRepo, times(1)).save(user);
        verify(this.userMapper, times(1)).toDto(user);
        assertEquals(userUpdated, userDto);
    }

    @Test
    public void updateUserNotFoundTest() throws UpdateUserException {

        UserDto userDto = new UserDto();

        when(this.userRepo.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        UserDto userUpdated = this.userService.updateUser(userDto, 1L);

        verify(this.userRepo, times(1)).findById(1L);
        assertNull(userUpdated);
    }

    @Test
    public void deleteUserTest() throws DeleteUserException {
        UserEntity user = new UserEntity();

        when(this.userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(this.userRepo.save(any(UserEntity.class))).thenReturn(user);

        boolean isDeleted = this.userService.deleteUser(1L);

        verify(this.userRepo, times(1)).findById(1L);
        verify(this.userRepo, times(1)).save(user);
        assertTrue(isDeleted);
    }

    @Test
    public void deleteUserNotFoundTest() throws DeleteUserException {

        when(this.userRepo.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        boolean userUpdated = this.userService.deleteUser(1L);

        verify(this.userRepo, times(1)).findById(1L);
        assertFalse(userUpdated);
    }
}
