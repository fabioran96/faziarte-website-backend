package fabioran.faziarte_website.security;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

public class GenerateSecretKey {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Chiave Segreta (Base64): " + base64EncodedKey);
    }
}
