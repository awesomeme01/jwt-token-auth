package kz.auth.app.wrapper;

import lombok.Data;

@Data
public class AuthWrapper {
    private String username;
    private String rawPassword;
}
//FRONT -> username, password
//BACK -> data(username, roles, accessTokenExpireTime, ...) + secret = token
