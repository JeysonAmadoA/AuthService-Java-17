package JeysonAmadoA.AuthService.Interfaces.Services.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.JwtAuthenticationDto;
import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;

public interface AuthServiceInterface {

    UserDto registerUser(RegisterUserDto registerUserDto) throws RegisterUserException;

    JwtAuthenticationDto loginUser(LoginDto loginDto);
}
