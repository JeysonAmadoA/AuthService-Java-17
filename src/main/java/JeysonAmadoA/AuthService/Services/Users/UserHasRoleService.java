package JeysonAmadoA.AuthService.Services.Users;

import JeysonAmadoA.AuthService.Entities.Users.UserHasRolesEntity;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Interfaces.Services.UserHasRoleServiceInterface;
import JeysonAmadoA.AuthService.Repositories.Users.UserHasRolesRepository;

public class UserHasRoleService implements UserHasRoleServiceInterface {

    private final UserHasRolesRepository userHasRolesRepo;

    public UserHasRoleService(UserHasRolesRepository userHasRolesRepo) {
        this.userHasRolesRepo = userHasRolesRepo;
    }

    @Override
    public void createUserRole(Long userId, Long roleId) throws RegisterUserException {
        try {
            UserHasRolesEntity userRoleEntity = UserHasRolesEntity
                    .builder().userId(userId)
                    .roleId(roleId).build();

            userRoleEntity.commitCreate();
            this.userHasRolesRepo.save(userRoleEntity);
        } catch (Exception e) {
            throw new RegisterUserException(e.getMessage());
        }
    }
}
