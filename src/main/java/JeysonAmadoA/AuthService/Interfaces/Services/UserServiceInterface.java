package JeysonAmadoA.AuthService.Interfaces.Services;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;

import java.util.List;

public interface UserServiceInterface {

    UserDto registerUser(RegisterUserDto registerUserDto) throws RegisterUserException;

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    List<UserDto> getUsersByEmail(String email);

    List<UserDto> getUsersByNameOrLastname(String entrySearch);

    UserDto updateUser(UserDto userDto, Long userId) throws UpdateUserException;

    boolean deleteUser(Long userId) throws DeleteUserException;

}
