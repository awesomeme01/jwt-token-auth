package kz.auth.app.config;

import kz.auth.app.domain.entity.User;
import kz.auth.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthManagerConfig {

    private final UserService userService;

    @Bean("authManager")
    public AuthenticationManager authenticationManager() {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                User user = userService.getUserByUsername((String)authentication.getPrincipal());
                if (user != null ){
                    authentication.setAuthenticated(Boolean.TRUE);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                return authentication;
            }
        };
    }

}
