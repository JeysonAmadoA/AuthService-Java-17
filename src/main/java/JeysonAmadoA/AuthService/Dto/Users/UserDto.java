package JeysonAmadoA.AuthService.Dto.Users;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private String name;

    private String lastName;

    private LocalDateTime birthDay;

    private String password;

    private String email;
}
