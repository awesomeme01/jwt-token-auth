package kz.auth.app.config.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.auth.app.helper.JwtUtils;
import kz.auth.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtTokenAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    public JwtTokenAuthFilter(
            @Qualifier("authManager") AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            UserService userService
    ) {
        super(request -> request.getHeader(HttpHeaders.AUTHORIZATION) != null, authenticationManager);
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        final String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        jwtUtils.validateToken(token);

        final Jws<Claims> claims = Jwts.parser().setSigningKey(JwtUtils.signKey)
                .parseClaimsJws(token);
        final Collection<String> authorities = (Collection<String>) claims.getBody().get("authentication");

        final Collection<MyAuthorities> authGrants = authorities.stream()
                .map(MyAuthorities::new)
                .toList();

        final AbstractAuthenticationToken abstractAuthenticationToken = new MyAuthenticationToken(claims.getBody().getSubject(), List.of(new MyAuthorities("USER")));

        return getAuthenticationManager().authenticate(abstractAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authResult);
        SecurityContextHolder.setContext(securityContext);

        chain.doFilter(request, response);
    }

    public static class MyAuthenticationToken extends AbstractAuthenticationToken {

        private String username;

        public MyAuthenticationToken(String username, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.username = username;
        }

        @Override
        public Object getCredentials() {
            return username;
        }

        @Override
        public Object getPrincipal() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }

    @AllArgsConstructor
    public static class MyAuthorities implements GrantedAuthority {

        private String role;

        @Override
        public String getAuthority() {
            return "ROLE_" + role;
        }
    }


}
