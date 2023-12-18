package JeysonAmadoA.AuthService.Config.Security;

import JeysonAmadoA.AuthService.Repositories.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeanInjector {

    private final UserRepository userRepository;

    @Autowired
    public SecurityBeanInjector(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username).orElseThrow(()
                -> new RuntimeException("No se encontró usuario"));
    }

}
