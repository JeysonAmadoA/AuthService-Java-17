package JeysonAmadoA.AuthService.Services.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.JwtAuthenticationDto;
import JeysonAmadoA.AuthService.Dto.Auth.LoginDto;
import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Interfaces.Auth.AuthServiceInterface;
import JeysonAmadoA.AuthService.Mappers.Auth.RegisterUserMapper;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import JeysonAmadoA.AuthService.Services.Security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService implements AuthServiceInterface {


    private final RegisterUserMapper registerUserMapper;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    private final UserRepository userRepo;

    @Autowired
    public AuthService(RegisterUserMapper registerUserMapper, UserMapper userMapper, AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepo) {
        this.registerUserMapper = registerUserMapper;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    public UserDto registerUser(RegisterUserDto registerUserDto) throws RegisterUserException {
        UserEntity userRegistered;
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encryptedPassword = encoder.encode(registerUserDto.getPassword());

            UserEntity newUser = this.registerUserMapper.toEntity(registerUserDto);
            newUser.setPassword(encryptedPassword);
            newUser.commitCreate();
            userRegistered = this.userRepo.save(newUser);
        } catch (Exception e) {
            throw new RegisterUserException(e.getMessage());
        }
        return this.userMapper.toDto(userRegistered);
    }

    public JwtAuthenticationDto loginUser(LoginDto loginDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        UserEntity user = this.userRepo.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return JwtAuthenticationDto.builder()
                .token(jwt).refreshToken(refreshToken)
                .build();
    }
}
