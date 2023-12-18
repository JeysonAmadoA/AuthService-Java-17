package JeysonAmadoA.AuthService.Services.Users;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Exceptions.DeleteUserException;
import JeysonAmadoA.AuthService.Exceptions.UpdateUserException;
import JeysonAmadoA.AuthService.Interfaces.Services.Users.UserServiceInterface;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static JeysonAmadoA.AuthService.Helpers.AuthHelper.getUserWhoActingId;

@Service
public class UserService implements UserServiceInterface {

    private final UserMapper userMapper;

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepo) {
        this.userMapper = userMapper;
        this.userRepo = userRepo;
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
    public List<UserDto> filterUsersByEmail(String email) {
        List<UserEntity> usersByEmail = this.userRepo.findByEmailLike(email);
        return usersByEmail.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> filterUsersByNameOrLastname(String entrySearch) {
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
                userUpdated.commitUpdate(getUserWhoActingId());
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
                userFound.commitDelete(getUserWhoActingId());
                this.userRepo.save(userFound);
                return true;
            }
            else return false;

        } catch (Exception e){
            throw new DeleteUserException(e.getMessage());
        }
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        return this.userRepo.findByEmail(userEmail).orElse(null);
    }


}
