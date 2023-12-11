package JeysonAmadoA.AuthService.Interfaces.Security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTServiceInterface {

    String generateToken(UserDetails user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
