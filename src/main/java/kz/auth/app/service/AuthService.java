package kz.auth.app.service;

import kz.auth.app.wrapper.AuthWrapper;

public interface AuthService {
    String accessToken(AuthWrapper wrapper);
}
