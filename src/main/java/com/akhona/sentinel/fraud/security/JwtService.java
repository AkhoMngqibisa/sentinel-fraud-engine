package com.akhona.sentinel.fraud.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtService {

    private static final String KEY = "mySuperSecretKeyThatIsAtLeast32CharactersLong!";

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
