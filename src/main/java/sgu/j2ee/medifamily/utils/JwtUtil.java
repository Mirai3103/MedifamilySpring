package sgu.j2ee.medifamily.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sgu.j2ee.medifamily.entities.User;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private final String SECRET_KEY = "medifamily";
    private final String TOKEN_PREFIX = "Bearer ";
    private final String HEADER_STRING = "Authorization";
    private final int EXPIRATION_TIME = 86400;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME * 1000);

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("id", user.getId())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
