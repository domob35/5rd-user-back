package spring.guro.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import spring.guro.entity.Member;

@Component
@Slf4j
public class JwtUtil {
    // 1시간
    public static Long expired = 1000 * 60 * 60L;

    private String secret = "guro-5rd-secret-key-fsadnfilanvlsnvlajkcnijldahlifafesacnidalschlasc";

    private SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    // create jwt token with Member entity
    public String createToken(Member member) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(member.getUserName())
                .claim("role", member.getAuthority().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(key)
                .compact();
    }

    // // verify jwt token
    // public boolean validateToken(String token) {
    //     try {
    //         // check role and primary_key is in token
    //         Claims claims = Jwts.parserBuilder()
    //                 .setSigningKey(key)
    //                 .build()
    //                 .parseClaimsJws(token)
    //                 .getBody();
    //         log.info("claims: {}", claims.toString());
    //         if (claims.get("role") == null) {
    //             return false;
    //         }
    //     } catch (Exception e) {
    //         return false;
    //     }
    //     return true;
    // }

    // // get role from jwt token
    // public String getRole(String token) {
    //     return Jwts.parserBuilder()
    //             .setSigningKey(key)
    //             .build()
    //             .parseClaimsJws(token)
    //             .getBody()
    //             .get("role", String.class);
    // }

    // get userName from jwt token
    public String getUserName(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}
