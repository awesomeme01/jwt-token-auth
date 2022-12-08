package kz.auth.app.service.impl;

import kz.auth.app.domain.entity.User;
import kz.auth.app.ex.AuthException;
import kz.auth.app.helper.JwtUtils;
import kz.auth.app.service.AuthService;
import kz.auth.app.service.UserService;
import kz.auth.app.wrapper.AuthWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String accessToken(AuthWrapper wrapper) {
        final User user = userService.getUserByUsername(wrapper.getUsername());

        if (!passwordEncoder.matches(wrapper.getRawPassword(), user.getPassword())) {
            throw new AuthException("Password didn't match!");
        }

        return jwtUtils.generateAccessToken(user.getUsername(), Collections.emptyList());
    }

}
