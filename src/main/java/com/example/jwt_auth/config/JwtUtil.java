    package com.example.jwt_auth.config;

    import io.jsonwebtoken.*;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    import jakarta.annotation.PostConstruct;

    import javax.crypto.SecretKey;
    import java.nio.charset.StandardCharsets;
    import java.util.Base64;
    import java.util.Date;
    import java.util.function.Function;

    @Component
    public class JwtUtil {

        @Value("${jwt.secret}")
        private String secretString;

        private SecretKey secretKey;

        @PostConstruct
        public void init() {
            byte[] keyBytes = Base64.getDecoder().decode(secretString);
            secretKey = Keys.hmacShaKeyFor(keyBytes);
        }
    //    public void init() {
    //        // Initialize the SecretKey from the secret string
    //        secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    //    }

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)   // Use secretKey here
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        public String generateToken(String username) {
            return Jwts.builder()
                    .setSubject(username)

                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10)) // 10 hrs
                    .signWith(secretKey)  // Use secretKey here too
                    .compact();
        }

        public String generateRefreshToken(String username) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days
                    .signWith(secretKey)
                    .compact();
        }

        public Boolean validateToken(String token, String username) {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        }
    }
