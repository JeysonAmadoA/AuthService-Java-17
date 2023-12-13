package JeysonAmadoA.AuthService.Dto.Auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationDto {

    String token;

    String refreshToken;
}
