package JeysonAmadoA.AuthService.Interfaces.Services.Users;

import JeysonAmadoA.AuthService.Dto.Users.UpdatePasswordDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;

import java.util.List;

public interface UserServiceInterface {

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    List<UserDto> filterUsersByEmail(String email);

    List<UserDto> filterUsersByNameOrLastname(String entrySearch);

    UserDto updateUser(UserDto userDto, Long userId) throws UpdateUserException;

    UserDto updatePassword(UpdatePasswordDto updatePasswordDto, Long userId) throws UpdateUserException;

    boolean deleteUser(Long userId) throws DeleteUserException;

    UserEntity getUserByEmail(String userEmail);

}
