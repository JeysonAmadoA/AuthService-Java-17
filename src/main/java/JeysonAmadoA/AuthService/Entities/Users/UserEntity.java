package JeysonAmadoA.AuthService.Entities.Users;

import JeysonAmadoA.AuthService.Entities.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted_at is NULL")
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity implements UserDetails {

    @NotNull(message = "Debe ingresar un nombre")
    @NotBlank(message = "El campo nombre no puede estar vacío")
    @Column
    private String name;

    @NotNull(message = "Debe ingresar un apellido")
    @NotBlank(message = "El campo apellido no puede estar vacío")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Debe ingresar una fecha de cumpleaños")
    @Column(name = "birth_day", columnDefinition = "DATE")
    private LocalDate birthDay;

    @NotNull(message = "Debe ingresar una constraseña")
    @NotBlank(message = "El campo contraseña no puede estar vacío")
    @Column
    private String password;

    @NotNull(message = "Debe ingresar un correo")
    @Email(message = "El correo no coincide con el formato esperado")
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_has_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    public int getAge(){
        LocalDateTime actualDateTime = LocalDateTime.now();
        LocalDate actualDate = actualDateTime.toLocalDate();
        return Period.between(this.getBirthDay(), actualDate).getYears();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = getRoles();

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
