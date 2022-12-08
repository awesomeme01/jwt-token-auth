package kz.auth.app.helper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("#{T(java.time.Duration).parse('PT30M')}")
    private Duration expirationTime;

    public static String signKey;

    @PostConstruct
    public void init() {
        JwtUtils.getSignKey();
    }

    public String generateAccessToken(String username, Collection<String> authorities) {
        final Date date = new Date();
        var accessInstant = date.toInstant().plusMillis(expirationTime.toMillis());

        var expireDate = new Date(accessInstant.toEpochMilli());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, JwtUtils.getSignKey())
                .addClaims(Collections.singletonMap("authentication", authorities))
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(JwtUtils.getSignKey())
                .parseClaimsJws(token);
    }

    @SneakyThrows
    private static String getSignKey() {
        if (JwtUtils.signKey == null) {
            signKey = new String(FileCopyUtils.copyToByteArray(ResourceUtils.getFile("classpath:sign_key")));
        }
        return signKey;
    }
}
