package JeysonAmadoA.AuthService.Http.Controllers.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Interfaces.Services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static JeysonAmadoA.AuthService.Helpers.AuthHelper.verifyRegisterPasswords;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceInterface userService;

    @Autowired
    public AuthController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            verifyRegisterPasswords(registerUserDto);
            UserDto userCreated = userService.registerUser(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
