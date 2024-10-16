package fabioran.faziarte_website.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class MailgunService {

    private static final String API_KEY = "82250a01a5fd5e4e374ae23fd21672fc-5dcb5e36-1cfcadc6";
    private static final String DOMAIN = "sandboxf1e3bc373d5c4015a6bfd166423f53d3.mailgun.org";
    private final RestTemplate restTemplate = new RestTemplate();

    public void sendEmail(String to, String subject, String text) {
        String url = "https://api.mailgun.net/v3/" + DOMAIN + "/messages";

        // Configura gli header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("api", API_KEY);

        // Configura il corpo della richiesta
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("from", "noreply@" + DOMAIN);
        body.add("to", to);
        body.add("subject", subject);
        body.add("text", text);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Errore durante l'invio dell'email: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'invio dell'email", e);
        }
    }

    public void sendOrderConfirmation(String customerEmail, String subject, String text, String adminEmail) {
        // Invia email al cliente
        sendEmail(customerEmail, subject, text);

        // Invia notifica all'admin
        String adminSubject = "Nuovo ordine ricevuto";
        String adminText = "È stato effettuato un nuovo ordine dall'utente: " + customerEmail;
        sendEmail(adminEmail, adminSubject, adminText);
    }
}