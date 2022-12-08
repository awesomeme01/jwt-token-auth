package kz.auth.app.controller;

import kz.auth.app.service.AuthService;
import kz.auth.app.wrapper.AuthWrapper;
import kz.auth.app.wrapper.TokenWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TokenWrapper> auth(
            @RequestBody AuthWrapper wrapper
            ) {
        TokenWrapper tokenWrapper = new TokenWrapper();
        tokenWrapper.setAccessToken(authService.accessToken(wrapper));
        return ResponseEntity.ok(tokenWrapper);
    }
}
