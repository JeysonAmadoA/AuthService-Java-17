package JeysonAmadoA.AuthService.Helpers;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthHelper {

    public static boolean verifyRegisterPasswords(RegisterUserDto registerUserDto) throws RegisterUserException {
        if (registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            return true;
        } else {
            throw new RegisterUserException("Las contraseñas no coinciden");
        }
    }

    public static Long getUserWhoActingId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            UserDto userData = (UserDto) authentication.getCredentials();
            return userData.getId();
        }
        else return null;
    }

}
