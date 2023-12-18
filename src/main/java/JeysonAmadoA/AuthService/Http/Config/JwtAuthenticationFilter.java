package JeysonAmadoA.AuthService.Http.Config;

import JeysonAmadoA.AuthService.Dto.Users.UserDto;
import JeysonAmadoA.AuthService.Entities.Users.UserEntity;
import JeysonAmadoA.AuthService.Interfaces.Services.Security.JWTServiceInterface;
import JeysonAmadoA.AuthService.Interfaces.Services.Users.UserServiceInterface;
import JeysonAmadoA.AuthService.Mappers.Users.UserMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTServiceInterface jwtService;

    private final UserServiceInterface userService;

    @Autowired
    public JwtAuthenticationFilter(JWTServiceInterface jwtService, UserServiceInterface userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];

        String userEmail = jwtService.extractUsername(jwt);
        UserEntity user = userService.getUserByEmail(userEmail);

        if (user != null && jwtService.isTokenValid(jwt, user)){
            UserDto userData = (new UserMapper()).toDto(user);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userEmail, userData, user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);

    }
}
