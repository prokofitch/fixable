package com.fixable.fixable.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "MinhaChaveSecreta1234567890ABCDE";
    private final long expiration = 3600000;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        Jwt<?, ?> parsedJwt = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parse(token);

        Claims claims = (Claims) parsedJwt.getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwt<?, ?> parsedJwt = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parse(token);

            Claims claims = (Claims) parsedJwt.getPayload();
            Date expirationDate = claims.getExpiration();
            return expirationDate.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
