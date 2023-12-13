package JeysonAmadoA.AuthService.Utilities.DefaultData;

import JeysonAmadoA.AuthService.Entities.Users.RoleEntity;
import JeysonAmadoA.AuthService.Repositories.Users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static JeysonAmadoA.AuthService.Entities.Users.RoleEntity.*;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RolesDefaultData implements CommandLineRunner {

    private final RoleRepository roleRepo;

    @Autowired
    public RolesDefaultData(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {

        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setName(ADMIN_ROLE);
        roleAdmin.commitCreate();
        roleRepo.save(roleAdmin);

        RoleEntity roleCustomer = new RoleEntity();
        roleCustomer.setName(CUSTOMER_ROLE);
        roleCustomer.commitCreate();
        roleRepo.save(roleCustomer);

    }
}
