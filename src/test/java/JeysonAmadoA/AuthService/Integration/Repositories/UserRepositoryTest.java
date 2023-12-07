package JeysonAmadoA.AuthService.Integration.Repositories;

import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    private final UserRepository userRepo;

    @Autowired
    public UserRepositoryTest(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest() {

        LocalDate date = LocalDate.now();

        UserEntity user = UserEntity.builder()
                .name("Jeyson").lastName("Amado")
                .birthDay(date).password("password")
                .email("jeyson@example.com").build();

        UserEntity userCreated = this.userRepo.save(user);
        assertNotNull(userCreated);
        assertThat(userCreated.getId()).isGreaterThanOrEqualTo(1L);

    }

    @Test
    @Order(2)
    public void findByIdUserTest() {

        UserEntity userFound = this.userRepo.findById(1L).orElse(null);
        assertNotNull(userFound);
        assertEquals(1L ,userFound.getId());
    }

    @Test
    @Order(3)
    public void findAllUsersTest() {

        List<UserEntity> users = this.userRepo.findAll();
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest() {

        UserEntity userFound = this.userRepo.findById(1L).orElse(null);
        assertNotNull(userFound);
        userFound.setPassword("password123");

        UserEntity userUpdated = this.userRepo.save(userFound);
        assertNotNull(userUpdated);
        assertEquals(1L ,userUpdated.getId());
        assertEquals("password123", userUpdated.getPassword());
    }

    @Test
    @Order(5)
    public void findByEmailLikeUserTest(){

        List<UserEntity> usersFound = this.userRepo.findByEmailLike("%jeyson%");
        UserEntity userFound = usersFound.get(0);

        assertNotNull(usersFound);
        assertThat(usersFound.size()).isGreaterThan(0);
        assertEquals(1L, userFound.getId());
        assertEquals("jeyson@example.com", userFound.getEmail());
    }

    @Test
    @Order(5)
    public void findByNameOrLastNameLikeUserTest(){

        List<UserEntity> usersFoundByName = this.userRepo.findByNameLikeOrLastNameLike("son");
        UserEntity userFoundByName = usersFoundByName.get(0);

        assertNotNull(userFoundByName);
        assertThat(usersFoundByName.size()).isGreaterThan(0);
        assertEquals(1L, userFoundByName.getId());
        assertEquals("Jeyson", userFoundByName.getName());


        List<UserEntity> usersFoundByLastname = this.userRepo.findByNameLikeOrLastNameLike("Am");
        UserEntity userFoundByLastname = usersFoundByName.get(0);

        assertNotNull(userFoundByLastname);
        assertThat(usersFoundByLastname.size()).isGreaterThan(0);
        assertEquals(1L, userFoundByLastname.getId());
        assertEquals("Amado", userFoundByLastname.getLastName());
    }
}
