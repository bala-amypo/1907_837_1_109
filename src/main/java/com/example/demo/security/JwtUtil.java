// package com.example.demo.security;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;
// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtUtil {
//     private final Key key;
//     private final long expirationMs;

//     public JwtUtil() {
//         this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//         this.expirationMs = 3600000;
//     }

//     // Constructor required by test setup
//     public JwtUtil(byte[] secret, Long expirationMs) {
//         this.key = Keys.hmacShaKeyFor(secret);
//         this.expirationMs = expirationMs;
//     }

//     public String generateToken(Long userId, String email, String role) {
//         return Jwts.builder()
//                 .setSubject(email)
//                 .claim("userId", userId)
//                 .claim("role", role)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
//                 .signWith(key)
//                 .compact();
//     }

//     public String extractEmail(String token) {
//         return parseClaims(token).getSubject();
//     }

//     public String extractRole(String token) {
//         return parseClaims(token).get("role", String.class);
//     }

//     public Long extractUserId(String token) {
//         return parseClaims(token).get("userId", Long.class);
//     }

//     public boolean validateToken(String token) {
//         try {
//             parseClaims(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }

//     private Claims parseClaims(String token) {
//         return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//     }
// }

package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private final Key key;
    private final long expirationMs;

    // EXACT CONSTRUCTOR REQUIRED BY TESTS
    public JwtUtil(byte[] secret, Long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret);
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Long extractUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("userId", Long.class);
    }
}