package JeysonAmadoA.AuthService.Http.Controllers.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.JwtAuthenticationDto;
import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Interfaces.Auth.AuthServiceInterface;
import JeysonAmadoA.AuthService.Interfaces.Services.UserHasRoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static JeysonAmadoA.AuthService.Helpers.AuthHelper.verifyRegisterPasswords;
import static JeysonAmadoA.AuthService.Entities.Users.RoleEntity.CUSTOMER_ROLE_ID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceInterface authService;

    private final UserHasRoleServiceInterface userHasRoleService;

    @Autowired
    public AuthController(AuthServiceInterface authService, UserHasRoleServiceInterface userHasRoleService) {
        this.authService = authService;
        this.userHasRoleService = userHasRoleService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            verifyRegisterPasswords(registerUserDto);
            UserDto userCreated = authService.registerUser(registerUserDto);
            userHasRoleService.createUserRole(userCreated.getId(), CUSTOMER_ROLE_ID);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            JwtAuthenticationDto jwtAuthentication = authService.loginUser(loginDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(jwtAuthentication);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
