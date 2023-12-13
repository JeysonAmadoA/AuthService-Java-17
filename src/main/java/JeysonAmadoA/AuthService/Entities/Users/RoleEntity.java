package JeysonAmadoA.AuthService.Entities.Users;

import JeysonAmadoA.AuthService.Entities.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity
@Where(clause = "deleted_at is NULL")
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity extends BaseEntity {

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String CUSTOMER_ROLE = "CUSTOMER";

    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long CUSTOMER_ROLE_ID = 2L;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<UserEntity> users;

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
