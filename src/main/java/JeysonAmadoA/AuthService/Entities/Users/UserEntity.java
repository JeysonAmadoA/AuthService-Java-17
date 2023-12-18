package JeysonAmadoA.AuthService.Entities.Users;

import JeysonAmadoA.AuthService.Entities.BaseEntity;
import JeysonAmadoA.AuthService.Utilities.Security.Role;
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
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted_at is NULL")
@Table(name = "users")
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private Role role;

    public int getAge(){
        LocalDateTime actualDateTime = LocalDateTime.now();
        LocalDate actualDate = actualDateTime.toLocalDate();
        return Period.between(this.getBirthDay(), actualDate).getYears();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
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

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + role +
                '}';
    }

}
