package JeysonAmadoA.AuthService.Utilities.DefaultData;

import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import JeysonAmadoA.AuthService.Utilities.Security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsersDefaultData implements CommandLineRunner {

    private final UserRepository userRepo;


    @Autowired
    public UsersDefaultData(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) {

        UserEntity userAdmin = UserEntity.builder()
                .name("admin").lastName("administrador")
                .email("admin@example.com").birthDay(LocalDate.of(2003,10,12))
                .password((new BCryptPasswordEncoder()).encode("admin123"))
                .role(Role.ADMIN)
                .build();

        userAdmin.commitCreate();

        userRepo.save(userAdmin);
    }
}
