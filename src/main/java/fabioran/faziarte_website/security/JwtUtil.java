package fabioran.faziarte_website.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        SecretKey key = getSigningKey();
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        System.out.println("JWT Secret during initialization: " + jwtSecret);
    }

    private SecretKey getSigningKey() {
        System.out.println("JWT Secret: " + jwtSecret);  // Aggiungi questo log

        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Genera un token JWT
    public String generateToken(String username) {
        System.out.println("Generating token with secret: " + jwtSecret);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 ore
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    // Estrae lo username dal token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Ottiene i claims dal token JWT
    private Claims getClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    // Valida il token
    public boolean validateToken(String token) {
        try {
            getClaims(token);  // Verifica la validità del token
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
