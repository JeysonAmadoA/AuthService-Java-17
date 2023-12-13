package JeysonAmadoA.AuthService.Entities.Users;

import JeysonAmadoA.AuthService.Entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted_at is NULL")
@Table(name = "user_has_roles")
@Data
public class UserHasRolesEntity extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

}
