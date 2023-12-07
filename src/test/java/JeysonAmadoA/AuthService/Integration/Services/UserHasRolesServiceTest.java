package JeysonAmadoA.AuthService.Integration.Services;

import JeysonAmadoA.AuthService.Entities.Users.UserHasRolesEntity;
import JeysonAmadoA.AuthService.Exceptions.RegisterUserException;
import JeysonAmadoA.AuthService.Repositories.Users.UserHasRolesRepository;
import JeysonAmadoA.AuthService.Services.Users.UserHasRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserHasRolesServiceTest {

    @Mock
    private UserHasRolesRepository userHasRolesRepo;

    @InjectMocks
    private UserHasRoleService userHasRoleService;

    @Test
    public void createUserRoleTest() throws RegisterUserException {

        Long userId = 1L;
        Long roleId = 2L;
        UserHasRolesEntity userHasRoles = UserHasRolesEntity.
                builder().userId(userId)
                .roleId(roleId).build();

        when(this.userHasRolesRepo.save(any(UserHasRolesEntity.class))).thenReturn(userHasRoles);

        this.userHasRoleService.createUserRole(userId, roleId);

        verify(this.userHasRolesRepo, times(1)).save(any(UserHasRolesEntity.class));
    }

}
