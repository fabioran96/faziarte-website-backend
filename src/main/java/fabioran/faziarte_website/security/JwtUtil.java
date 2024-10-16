package fabioran.faziarte_website.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Genera un token JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 ore
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Estrae lo username dal token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Ottiene i claims dal token JWT
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
