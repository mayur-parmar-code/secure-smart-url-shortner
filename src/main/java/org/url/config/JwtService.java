package org.url.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.url.constants.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET =
            "23F452848284D6251655468576D5A713474375367566859703373367639792F4";

    private static final long EXPIRATION = 30 * 60 * 1000; // 30 minutes

    // -------------------- Create Token --------------------

    public String generateToken(String email, Role role) {
        return createToken(Map.of("role", role), email);
    }

    private String createToken(Map<String, Object> claims, String email) {

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(now))
                .expiration(new Date(now + EXPIRATION))
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    // -------------------- Get Signing Key --------------------

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);   // returns SecretKey
    }

    // -------------------- Extract Claims --------------------

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // -------------------- Validation --------------------

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    // -------------------- Extract Token from Header --------------------

    public String extractAuthToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }
}
