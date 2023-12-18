package JeysonAmadoA.AuthService.Feature.Controllers.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.JwtAuthenticationDto;
import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Http.Controllers.Auth.AuthController;
import JeysonAmadoA.AuthService.Services.Auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void registerTest() throws RegisterUserException {

        LocalDate date = LocalDate.now();

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName("Jeyson");
        registerUserDto.setLastName("Amado");
        registerUserDto.setEmail("jeyson@example.com");
        registerUserDto.setPassword("ABCD");
        registerUserDto.setConfirmPassword("ABCD");
        registerUserDto.setBirthDay(date);

        UserDto userResult = new UserDto();
        userResult.setName("Jeyson");
        userResult.setLastName("Amado");
        userResult.setEmail("jeyson@example.com");
        userResult.setBirthDay(date);

        when(authService.registerUser(any(RegisterUserDto.class))).thenReturn(userResult);

        ResponseEntity<?> response = authController.register(registerUserDto);

        verify(authService, times(1)).registerUser(registerUserDto);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResult, response.getBody());
    }

    @Test
    public void registerFailTest() {

        LocalDate date = LocalDate.now();

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName("Jeyson");
        registerUserDto.setLastName("Amado");
        registerUserDto.setEmail("jeyson@example.com");
        registerUserDto.setPassword("ABCD");
        registerUserDto.setConfirmPassword("ABCDE");
        registerUserDto.setBirthDay(date);

        UserDto userResult = new UserDto();
        userResult.setName("Jeyson");
        userResult.setLastName("Amado");
        userResult.setEmail("jeyson@example.com");
        userResult.setBirthDay(date);

        ResponseEntity<?> response = authController.register(registerUserDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error al crear usuario. Las contraseñas no coinciden", response.getBody());
    }

    @Test
    public void loginTest() {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jeyson@example.com");
        loginDto.setPassword("ABCD");

        JwtAuthenticationDto authenticationDto = JwtAuthenticationDto
                .builder()
                .token("Token")
                .refreshToken("Refresh token")
                .build();

        when(authService.loginUser(any(LoginDto.class))).thenReturn(authenticationDto);

        ResponseEntity<?> response = authController.login(loginDto);

        verify(authService, times(1)).loginUser(loginDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationDto, response.getBody());
    }

    @Test
    public void loginFailTest() {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jeyson@example.com");
        loginDto.setPassword("ABCD");

        when(authService.loginUser(any(LoginDto.class))).thenThrow(new RuntimeException());

        ResponseEntity<?> response = authController.login(loginDto);

        verify(authService, times(1)).loginUser(loginDto);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
