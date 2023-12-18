package JeysonAmadoA.AuthService.Http.Controllers.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.JwtAuthenticationDto;
import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Interfaces.Services.Auth.AuthServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static JeysonAmadoA.AuthService.Helpers.AuthHelper.verifyRegisterPasswords;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceInterface authService;

    @Autowired
    public AuthController(AuthServiceInterface authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            verifyRegisterPasswords(registerUserDto);
            UserDto userCreated = authService.registerUser(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            JwtAuthenticationDto jwtAuthentication = authService.loginUser(loginDto);
            return ResponseEntity.status(HttpStatus.OK).body(jwtAuthentication);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
