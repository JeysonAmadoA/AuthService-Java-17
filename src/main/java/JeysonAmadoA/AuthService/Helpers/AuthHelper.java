package JeysonAmadoA.AuthService.Helpers;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;

public class AuthHelper {

    public static boolean verifyRegisterPasswords(RegisterUserDto registerUserDto) throws RegisterUserException {
        if (registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return true;
        } else {
            throw new RegisterUserException("Las contraseñas no coinciden");
        }
    }

}
