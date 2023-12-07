package JeysonAmadoA.AuthService.Interfaces.Services;

import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;

public interface UserHasRoleServiceInterface {

    void createUserRole(Long userId, Long roleId) throws RegisterUserException;
}
