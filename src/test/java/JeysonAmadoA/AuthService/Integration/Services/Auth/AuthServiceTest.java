package JeysonAmadoA.AuthService.Integration.Services.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.JwtAuthenticationDto;
import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Mappers.Auth.RegisterUserMapper;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import JeysonAmadoA.AuthService.Services.Auth.AuthService;
import JeysonAmadoA.AuthService.Services.Security.JWTService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private RegisterUserMapper registerUserMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private AuthService authService;

    @Test
    public void getRegisterUserTest() throws RegisterUserException {

        UserEntity userRegistered = UserEntity.builder()
                .name("Jeyson").lastName("Amado")
                .birthDay(LocalDate.of(1997,11,23))
                .email("jeyson@example.com").password("pass123").build();



        UserDto userResult = new UserDto();
        userResult.setName(userRegistered.getName());
        userResult.setLastName(userRegistered.getLastName());
        userResult.setBirthDay(userRegistered.getBirthDay());
        userResult.setEmail(userRegistered.getEmail());

        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setPassword("pass123");

        when(registerUserMapper.toEntity(any(RegisterUserDto.class))).thenReturn(userRegistered);
        when(userRepo.save(any(UserEntity.class))).thenReturn(userRegistered);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userResult);

        UserDto userRegisteredDto = authService.registerUser(registerUserDto);

        verify(registerUserMapper, times(1)).toEntity(registerUserDto);
        verify(userRepo, times(1)).save(userRegistered);
        verify(userMapper, times(1)).toDto(userRegistered);
        assertEquals(userResult, userRegisteredDto);
    }

    @Test
    public void loginUserTest(){

        UserEntity userFound = UserEntity.builder()
                .name("Jeyson").lastName("Amado")
                .birthDay(LocalDate.of(1997,11,23))
                .email("jeyson@example.com").password("pass123").build();

        JwtAuthenticationDto authenticationDto = JwtAuthenticationDto.builder()
                .token("JWT")
                .refreshToken("RefreshToken")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.ofNullable(userFound));
        when(jwtService.generateToken(any(UserEntity.class))).thenReturn("JWT");
        when(jwtService.generateRefreshToken(anyMap(), any(UserEntity.class))).thenReturn("RefreshToken");

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jeyson@example.com");
        loginDto.setPassword("pass123");

        JwtAuthenticationDto jwtAuthenticationResult = authService.loginUser(loginDto);

        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        verify(userRepo, times(1)).findByEmail(loginDto.getEmail());
        assertNotNull(userFound);
        verify(jwtService, times(1)).generateToken(userFound);
        verify(jwtService, times(1)).generateRefreshToken(new HashMap<>(), userFound);
        assertEquals(authenticationDto, jwtAuthenticationResult);
    }
}
