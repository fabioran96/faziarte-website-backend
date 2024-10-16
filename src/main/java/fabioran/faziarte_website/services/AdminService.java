package fabioran.faziarte_website.services;

import fabioran.faziarte_website.entities.Admin;
import fabioran.faziarte_website.repositories.AdminRepository;
import fabioran.faziarte_website.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (passwordEncoder.matches(password, admin.getPassword())) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Password non valida");
        }
    }
}
