package JeysonAmadoA.AuthService.Mappers.Auth;

import JeysonAmadoA.AuthService.Dto.Auth.RegisterUserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Mappers.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserMapper extends BaseMapper<RegisterUserDto, UserEntity> {

    public RegisterUserMapper() {
        super(RegisterUserDto.class, UserEntity.class);
    }
}
