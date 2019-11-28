package github.leyan95.app.filter;

import github.leyan95.app.controller.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author wujianchuan 2019/1/30
 */
@Component
public class TokenUtil {
    static final String CLAIM_KEY_AVATAR = "avatar";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_AUTH = "auth";

    private String tokenSecret;
    private long tokenExpiration;

    public TokenUtil() {
        ResourceBundle resourceApplication = ResourceBundle.getBundle("application", Locale.US);
        tokenSecret = resourceApplication.getString("app.token.secret");
        tokenExpiration = Long.parseLong(resourceApplication.getString("app.token.expiration"));
    }

    public String generateToken(AppUser user) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(CLAIM_KEY_AVATAR, user.getAvatar());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_AUTH, user.getAuthIds());
        return this.generateToken(claims);
    }

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public boolean shouldRefreshed(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
