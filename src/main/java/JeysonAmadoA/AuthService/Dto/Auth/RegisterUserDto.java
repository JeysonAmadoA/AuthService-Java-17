package JeysonAmadoA.AuthService.Dto.Auth;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserDto {

    private String name;

    private String lastName;

    private LocalDate birthDay;

    private String password;

    private String confirmPassword;

    private String email;
}
