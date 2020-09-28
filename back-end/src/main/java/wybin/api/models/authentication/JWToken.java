package wybin.api.models.authentication;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWToken {
    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_ROLE_CLAIM = "role";

    private Long userId;
    private Integer role;

    public JWToken(Long userId, Integer role) {
        this.userId = userId;
        this.role = role;
    }

    private static Key getKey(String passPhrase) {
        byte[] hmacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public static JWToken decode(String token, String passPhrase) {
        try {
            Key key = getKey(passPhrase);
            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            Claims claims = jws.getBody();

            return new JWToken(
                    Long.valueOf(claims.get(JWT_USERID_CLAIM).toString()),
                    (Integer) claims.get(JWT_ROLE_CLAIM)
            );
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String encode(String issuer, String passphrase) {
        Key key = getKey(passphrase);
        return Jwts.builder()
                .claim(JWT_USERID_CLAIM, this.userId)
                .claim(JWT_ROLE_CLAIM, this.role)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 19 * 24 * 3600 * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
