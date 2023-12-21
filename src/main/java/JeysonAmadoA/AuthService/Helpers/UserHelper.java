package JeysonAmadoA.AuthService.Helpers;

import JeysonAmadoA.AuthService.Dto.Users.UpdatePasswordDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserHelper {

    public static boolean verifyOldPassword(String newPassword, String oldPassword,
                                            BCryptPasswordEncoder encoder) throws Exception {
        if (encoder.matches(newPassword, oldPassword)) {
            return true;
        } else {
            throw new Exception("No coincide con la contraseña actual");
        }
    }

    public static void verifyNewPassword(UpdatePasswordDto updatePasswordDto) throws Exception {
        if (!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getConfirmPassword())) {
            throw new Exception("La contraseña nueva no coincide");
        }
    }
}
