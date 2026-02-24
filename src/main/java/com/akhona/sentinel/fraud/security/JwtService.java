package com.akhona.sentinel.fraud.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtService {

    public String generateToken(String username) {
        String KEY = "secret-key";
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)
                ).signWith(Keys.hmacShaKeyFor(KEY.getBytes())).compact();
    }
}
