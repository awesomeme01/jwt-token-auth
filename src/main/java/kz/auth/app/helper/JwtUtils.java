package kz.auth.app.helper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String signKey = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDmfRf8zabm3K81\n" +
            "1/wSxyEp7s97Wli8h9aNNkw9Ys9vhS+n/N77tbjHL2XQGB0yDctS5mFSZ1I5XAg3\n" +
            "4ZrBEXBB2dWbaHRWroc7xRUSuT7viAhN1pf9hbn3CudtkSO0MTYdfK2BnLWZ6GXw\n" +
            "LUR+fgh7GOdoqGz7LxzOUhmxXH1lmPWCM9aCwlrSO7m4OIu//sNY7eKnvzB5+3uc\n" +
            "Xo9yvkdmqmQRUCy8wuxRBpse95q+S6FDIl40whH2NRXLc5Gi5zYgTo8NJajSzukY\n" +
            "EH0umxgUCnOtqcezMtFBYeRxSxlwMGcrqxzpUiFQ7ZROmdz2DuuNC/ky17OeIE2F\n" +
            "EXDRuUHZAgMBAAECggEAHnKhBBbguPenN6wwEwT2NxVimE9/kGw7Zg0ptiRC9d74\n" +
            "xC7OAB9p6Po0b3B4DuAun2VqrtmSTZyAQ22MpXuZwkE2DCEY2UiV+cnXtlD9ZOQq\n" +
            "AKpcCrQrNFze5KT7O/cSSIPrYcdjYBEIwlkghrs8gJJyI2u4ddgr7H+BAGKQ+CYj\n" +
            "8BbeK+pybHePETXOGOayee+8EPkho7LkuvQhgXrHKFhWEXzI7ncuHJkmfGjnNj11\n" +
            "oKHSnv7blKPMNTz4AjsgvzJi6p01Sq3ike258o+A7s4+IW+ec1tZKsXTkHcesmPu\n" +
            "N9PkCPmfgG9jDzlE7ATvOyW7Uqq+QkKkhgh3eDAHFwKBgQD9dkjt5mFrYhkF5k6N\n" +
            "yzJked2ChVD3GJTqLpT1hTAPHeYDvElSWoKMzK32O6BhqpHXCle3f2Kqk+IWQbyV\n" +
            "KFh+21IllYDSm9zKGypQg/w4FtCrAXSTraTvsu3XJXGZj1w3AedF8+ZQqMqDMIrQ\n" +
            "xYxgeah3YOWxfbE9zX3iNHoM8wKBgQDoy+tu5Leu0N+bkJknb72W31p6KhzjpeXh\n" +
            "93BREJoh5XUSpJLeQAGjfVSa9zM33EcE11TLJM6N4kp1AK7zZRnA4oPLLjfy8ej2\n" +
            "O0SJARlHccyZeV68vPOhPRtvrlN/3PdbrqImJoCS3n1MkGj3Ak+8LIJg+JsXGze6\n" +
            "X6asgTM5AwKBgCHw3QM+fn0qvb8UgCBsvWrHBuqbf6QXHpndcwUNuMnNbyfA5vCo\n" +
            "PiMspX77rRZnCPCK6gQggs3kz7m7Q82VgYQ7SlzOEFojlQVbbecqRXQdWgQMRdgg\n" +
            "wJ14kHDnCrdNg0O6dYXgi07xy2yV64DZc1rX779MsnV6J2nnQpwv+sgRAoGAGbbE\n" +
            "l6gt3eJ2gJF1SVTfZTXgoZPbQw4FGXOidWHXFJ1q1BdXnbMGO0/Rs5hQkQAE3DUI\n" +
            "ybOUGToArXHY4Uuuj461rhC531z3NZOQW23CpBCe3+j3HPPmGU5NpIVl9b6rl/Xv\n" +
            "NTWViCOJRZbB8V7Q/eEQubNcNi4sdqqwF2fiB48CgYBvuUeM/TL9SmSk6VgC3vQM\n" +
            "GSnJCogUovtGMQp95Tp+ylT9UUe0sChvJK31IXsDQN97J9T5vYzQgDz4ILTQuceE\n" +
            "C3DIahkIXTH+dUx7MjvB4Yg3inZOsp+hXVZt5RdEbSze+VUKUURiISMpZ8cg6rdD\n" +
            "mOHVLY268dRc83UEHH8WFQ==\n" +
            "-----END PRIVATE KEY-----";
    private static final long expirationTime = 1000L;

    public String generateAccessToken(String username, Collection<String> authorities) {
        final Date date = new Date();
        var accessInstant = date.toInstant().plusMillis(expirationTime);

        var expireDate = new Date(accessInstant.toEpochMilli());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .addClaims(Collections.singletonMap("authentication", authorities))
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(token);
    }
}
