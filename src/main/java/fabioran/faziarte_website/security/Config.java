package fabioran.faziarte_website.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class Config {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disabilitiamo CSRF per applicazioni stateless
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Configurazione CORS
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Sessione stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/products", "/public/**", "/checkout/**", "/options/**").permitAll()  // Rotte pubbliche
                        .requestMatchers("/admin/**").authenticated()  // Rotte admin protette
                        .anyRequest().permitAll()  // Permetti altre rotte per sicurezza (se necessario)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Filtro JWT

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);  // Algoritmo di hash per password
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));  // Origini consentite
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Metodi HTTP consentiti
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));  // Header consentiti
        configuration.setAllowCredentials(true);  // Permetti credenziali (cookie/token)
        configuration.addExposedHeader("Authorization");  // Esponi header specifici come Authorization

        // Risolvi i problemi di richieste preflight (OPTIONS)
        configuration.setMaxAge(3600L);  // Tempo di cache per le richieste preflight

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}