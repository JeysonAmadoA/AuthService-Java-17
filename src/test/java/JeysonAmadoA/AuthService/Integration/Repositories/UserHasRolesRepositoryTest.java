package JeysonAmadoA.AuthService.Integration.Repositories;

import JeysonAmadoA.AuthService.Entities.Users.UserHasRolesEntity;
import JeysonAmadoA.AuthService.Repositories.Users.UserHasRolesRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserHasRolesRepositoryTest {

    private final UserHasRolesRepository userHasRolesRepo;

    @Autowired
    public UserHasRolesRepositoryTest(UserHasRolesRepository userHasRolesRepo) {
        this.userHasRolesRepo = userHasRolesRepo;
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest() {
        UserHasRolesEntity userHasRoles = UserHasRolesEntity
                .builder().userId(1L)
                .roleId(2L).build();

        UserHasRolesEntity userRoleCreated = this.userHasRolesRepo.save(userHasRoles);
        userRoleCreated.commitCreate();

        assertNotNull(userRoleCreated);
        assertThat(userRoleCreated.getId()).isGreaterThanOrEqualTo(1L);
    }


}
