// package com.example.demo.security;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;


// import java.util.HashMap;
// import java.util.Map;
// import java.util.Base64;
// import javax.crypto.Mac;
// import javax.crypto.spec.SecretKeySpec;

// @Component
// public class JwtUtil {
    
//     private final String secret;
//     private final long expirationMs;

//     public JwtUtil(@Value("${app.jwt.secret}") String secret, 
//                    @Value("${app.jwt.expiration-ms}") Long expirationMs) {
//         this.secret = secret;
//         this.expirationMs = expirationMs;
//     }

//     public JwtUtil(byte[] secret, Long expirationMs) {
//         this.secret = new String(secret);
//         this.expirationMs = expirationMs;
//     }

//     public String generateToken(Long userId, String email, String role) {
//         Map<String, Object> claims = new HashMap<>();
//         claims.put("userId", userId);
//         claims.put("email", email);
//         claims.put("role", role);
        
//         return createToken(claims, email);
//     }

//     private String createToken(Map<String, Object> claims, String subject) {
//         String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
//         String payload = createPayload(claims, subject);
        
//         String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes());
//         String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes());
        
//         String signature = createSignature(encodedHeader + "." + encodedPayload);
        
//         return encodedHeader + "." + encodedPayload + "." + signature;
//     }

//     private String createPayload(Map<String, Object> claims, String subject) {
//         long now = System.currentTimeMillis();
//         long exp = now + expirationMs;
        
//         StringBuilder payload = new StringBuilder();
//         payload.append("{");
//         payload.append("\"sub\":\"").append(subject).append("\",");
//         payload.append("\"iat\":").append(now / 1000).append(",");
//         payload.append("\"exp\":").append(exp / 1000);
        
//         for (Map.Entry<String, Object> entry : claims.entrySet()) {
//             payload.append(",\"").append(entry.getKey()).append("\":");
//             if (entry.getValue() instanceof String) {
//                 payload.append("\"").append(entry.getValue()).append("\"");
//             } else {
//                 payload.append(entry.getValue());
//             }
//         }
        
//         payload.append("}");
//         return payload.toString();
//     }

//     private String createSignature(String data) {
//         try {
//             Mac mac = Mac.getInstance("HmacSHA256");
//             SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//             mac.init(secretKeySpec);
//             byte[] hash = mac.doFinal(data.getBytes());
//             return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
//         } catch (Exception e) {
//             throw new RuntimeException("Error creating signature", e);
//         }
//     }

//     public String extractEmail(String token) {
//         return extractClaim(token, "sub");
//     }

//     public String extractRole(String token) {
//         return extractClaim(token, "role");
//     }

//     public Long extractUserId(String token) {
//         String userIdStr = extractClaim(token, "userId");
//         return userIdStr != null ? Long.valueOf(userIdStr) : null;
//     }

//     public boolean validateToken(String token) {
//         try {
//             String[] parts = token.split("\\.");
//             if (parts.length != 3) return false;
            
//             String signature = createSignature(parts[0] + "." + parts[1]);
//             return signature.equals(parts[2]);
//         } catch (Exception e) {
//             return false;
//         }
//     }

//     private String extractClaim(String token, String claimName) {
//         try {
//             String[] parts = token.split("\\.");
//             if (parts.length != 3) return null;
            
//             String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            
//             String searchKey = "\"" + claimName + "\":";
//             int startIndex = payload.indexOf(searchKey);
//             if (startIndex == -1) return null;
            
//             startIndex += searchKey.length();
//             if (payload.charAt(startIndex) == '"') {
//                 startIndex++;
//                 int endIndex = payload.indexOf('"', startIndex);
//                 return payload.substring(startIndex, endIndex);
//             } else {
//                 int endIndex = payload.indexOf(',', startIndex);
//                 if (endIndex == -1) endIndex = payload.indexOf('}', startIndex);
//                 return payload.substring(startIndex, endIndex);
//             }
//         } catch (Exception e) {
//             return null;
//         }
//     }
// }

package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String secret;
    private final long expirationMs;

    @Autowired
    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") Long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    // ----------------------------------------------------------------
    // Generate Token (used by your AuthController)
    // ----------------------------------------------------------------
    public String generateToken(Long id, String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("email", email);
        claims.put("role", role);
        claims.put("iat", System.currentTimeMillis());  // issued at
        claims.put("exp", System.currentTimeMillis() + expirationMs); // expiration time

        return createToken(claims);
    }

    // Optional simple version (if needed)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("iat", System.currentTimeMillis());
        claims.put("exp", System.currentTimeMillis() + expirationMs);
        return createToken(claims);
    }

    // ----------------------------------------------------------------
    // Create JWT manually
    // ----------------------------------------------------------------
    private String createToken(Map<String, Object> claims) {
        try {
            // Header
            String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String header = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(headerJson.getBytes());

            // Payload
            String payload = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(claims.toString().getBytes());

            // Signature = HMACSHA256(header.payload)
            String signature = hmacSha256(header + "." + payload, secret);

            return header + "." + payload + "." + signature;

        } catch (Exception e) {
            throw new RuntimeException("Error while creating JWT", e);
        }
    }

    // ----------------------------------------------------------------
    // Validate token
    // ----------------------------------------------------------------
    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String header = parts[0];
            String payload = parts[1];
            String signature = parts[2];

            String expectedSignature = hmacSha256(header + "." + payload, secret);

            return expectedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }

    // ----------------------------------------------------------------
    // Extract fields from token
    // ----------------------------------------------------------------
    public String extractEmail(String token) {
        String payload = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
        return payload.split("email=")[1].split(",")[0];
    }

    public Long extractId(String token) {
        String payload = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
        return Long.valueOf(payload.split("id=")[1].split(",")[0]);
    }

    public String extractRole(String token) {
        String payload = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
        return payload.split("role=")[1].split(",")[0];
    }

    // ----------------------------------------------------------------
    // HMAC SHA256
    // ----------------------------------------------------------------
    private String hmacSha256(String data, String secretKey) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);

        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(sha256_HMAC.doFinal(data.getBytes()));
    }
}
