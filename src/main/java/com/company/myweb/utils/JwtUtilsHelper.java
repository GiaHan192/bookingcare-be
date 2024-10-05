package com.company.myweb.utils;

import com.company.myweb.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtilsHelper {
    @Value("${jwt.privateKey}")
    private String privateKey;

    @Value("${jwt.expiration}")
    private String expiration;


    public String generateToken(UserDTO users) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
        long expirationNumber = Long.parseLong(expiration) * 1000;
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", users.getUserName());
        claims.put("fullName", users.getFullName());
        claims.put("role", users.getRoles().getRoleName());

        return Jwts.builder()
                .claims(claims)
                .subject(users.getUserName())
                .signWith(SignatureAlgorithm.ES512, key)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationNumber))
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKey));
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
