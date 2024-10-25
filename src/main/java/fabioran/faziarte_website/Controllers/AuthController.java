package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Debug temporaneo
        System.out.println("Username: " + username + ", Password: " + password);


        if ("fabioran".equals(username) && "12345".equals(password)) {
            String token = jwtUtil.generateToken(username);

            System.out.println("Generated token: " + token);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new RuntimeException("Credenziali non valide");
        }
    }


}