package com.example.whatsappwebclone.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.example.whatsappwebclone.config.JwtConstant.SECRET_KEY;

@Component
public class TokenProvider {
    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        String jwt = Jwts.builder().setIssuer("Kshitij").setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000)) // 24 hrs
                .claim("email", authentication.getName()).signWith(secretKey).compact();

        return jwt;
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claim = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claim.get("email"));
    }
}