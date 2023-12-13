package JeysonAmadoA.AuthService.Utilities.DefaultData;

import JeysonAmadoA.AuthService.Entities.Users.RoleEntity;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Repositories.Users.RoleRepository;
import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class UsersDefaultData implements CommandLineRunner {

    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    @Autowired
    public UsersDefaultData(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {

        RoleEntity adminRol = roleRepo.findById(1L).orElse(null);
        RoleEntity customerRol = roleRepo.findById(2L).orElse(null);

        Set<RoleEntity> userHasRoles = new HashSet<>();
        userHasRoles.add(adminRol);
        userHasRoles.add(customerRol);

        UserEntity userAdmin = UserEntity.builder()
                .name("admin").lastName("administrador")
                .email("admin@example.com").birthDay(LocalDate.of(2003,10,12))
                .password((new BCryptPasswordEncoder()).encode("admin123"))
                .roles(userHasRoles)
                .build();

        userAdmin.commitCreate();

        userRepo.save(userAdmin);
    }
}
