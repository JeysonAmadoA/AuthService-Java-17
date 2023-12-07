package JeysonAmadoA.AuthService.Entities.Users;

import JeysonAmadoA.AuthService.Entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted_at is NULL")
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity {

    @Column
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_day", columnDefinition = "DATE")
    private LocalDate birthDay;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    public int getAge(){
        LocalDateTime actualDateTime = LocalDateTime.now();
        LocalDate actualDate = actualDateTime.toLocalDate();

        return Period.between(this.getBirthDay(), actualDate).getYears();
    }

}
