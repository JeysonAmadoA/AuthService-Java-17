package JeysonAmadoA.AuthService.Service.Users;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;
import JeysonAmadoA.AuthService.Interfaces.Services.UserServiceInterface;
import JeysonAmadoA.AuthService.Mappers.Auth.RegisterUserMapper;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private final UserMapper userMapper;

    private final RegisterUserMapper registerUserMapper;

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserMapper userMapper, RegisterUserMapper registerUserMapper, UserRepository userRepo) {
        this.userMapper = userMapper;
        this.registerUserMapper = registerUserMapper;
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

    @Override
    public UserDto getUserById(Long userId) {
        UserEntity userFound = this.userRepo.findById(userId).orElse(null);
        return userFound != null ? userMapper.toDto(userFound) : null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> allUsers = this.userRepo.findAll();
        return allUsers.stream()
                .map(this.userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByEmail(String email) {
        List<UserEntity> usersByEmail = this.userRepo.findByEmailLike(email);
        return usersByEmail.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByNameOrLastname(String entrySearch) {
        List<UserEntity> usersByName = this.userRepo.findByNameLikeOrLastNameLike(entrySearch);
        return usersByName.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) throws UpdateUserException {
        UserEntity userUpdated;
        try {
            UserEntity userFound = this.userRepo.findById(userId).orElse(null);
            if (userFound != null){
                userUpdated = this.userMapper.update(userFound, userDto);
                userUpdated.commitUpdate();
                this.userRepo.save(userUpdated);
            }
            else return null;
        } catch (Exception e){
            throw new UpdateUserException(e.getMessage());
        }
        return userMapper.toDto(userUpdated);
    }

    @Override
    public boolean deleteUser(Long userId) throws DeleteUserException {
        try {
            UserEntity userFound = this.userRepo.findById(userId).orElse(null);
            if (userFound != null){
                userFound.commitDelete();
                this.userRepo.save(userFound);
                return true;
            }
            else return false;

        } catch (Exception e){
            throw new DeleteUserException(e.getMessage());
        }
    }


}
