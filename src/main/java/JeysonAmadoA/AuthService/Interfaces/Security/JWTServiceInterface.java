package JeysonAmadoA.AuthService.Interfaces.Security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTServiceInterface {

    String generateToken(UserDetails user);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
