package JeysonAmadoA.AuthService.Http.Config;

import JeysonAmadoA.AuthService.Interfaces.Security.JWTServiceInterface;
import JeysonAmadoA.AuthService.Interfaces.Services.UserServiceInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
        final String jwt;
        final String userEmail;

        if (authHeader == null ||
            !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer")){

            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if ((!StringUtils.isEmpty(userEmail)) || SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.getUserDetailsService().loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }

        }

        filterChain.doFilter(request, response);

    }
}
