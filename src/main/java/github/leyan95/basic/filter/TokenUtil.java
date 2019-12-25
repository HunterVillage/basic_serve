package github.leyan95.basic.filter;

import github.leyan95.basic.config.TokenConfig;
import github.leyan95.basic.controller.UserView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wujianchuan 2019/1/30
 */
@Component
public class TokenUtil {

    public static final String CLAIM_KEY_AVATAR = "avatar";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_KEY_AUTH = "auth";

    private String tokenSecret;
    private long tokenExpiration;
    private String tokenHead;

    @Autowired
    public TokenUtil(TokenConfig tokenConfig) {
        tokenSecret = tokenConfig.getSecret();
        tokenExpiration = tokenConfig.getExpiration();
        tokenHead = tokenConfig.getHead();
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public String generateToken(UserView user) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(CLAIM_KEY_AVATAR, user.getAvatar());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_AUTH, String.join(",", user.getAuthIds()));
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
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

    String refreshToken(Claims claims) {
        String refreshedToken;
        try {
            claims.remove(CLAIM_KEY_CREATED, new Date());
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    boolean shouldRefresh(Claims claims) {
        long createTime = (long) claims.get(CLAIM_KEY_CREATED);
        // token 超时前这30分钟的范围内如果有活动就刷新token，如果这一小时没有任何操作则必须重新登录
        long plusTimeMillis = tokenExpiration - 30 * 10000;
        if (plusTimeMillis <= 0) {
            throw new InvalidTokenException("The timeout of token shall not be less than half an hour.");
        }
        return new Date(createTime + tokenExpiration - 30 * 10000).before(new Date());
    }
}
