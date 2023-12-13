package JeysonAmadoA.AuthService.Interfaces.Services;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface {

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    List<UserDto> getUsersByEmail(String email);

    List<UserDto> getUsersByNameOrLastname(String entrySearch);

    UserDto updateUser(UserDto userDto, Long userId) throws UpdateUserException;

    boolean deleteUser(Long userId) throws DeleteUserException;

    UserDetailsService getUserDetailsService();

}
