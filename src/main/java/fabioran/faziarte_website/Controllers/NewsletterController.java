package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.Newsletter;
import fabioran.faziarte_website.repositories.NewsletterRepository;
import fabioran.faziarte_website.services.MailgunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsletter")
@CrossOrigin(origins = "http://localhost:5173")
public class NewsletterController {

    @Autowired
    private NewsletterRepository newsletterRepository;

    @Autowired
    private MailgunService mailgunService;

    @PostMapping
    public ResponseEntity<String> subscribe(@RequestBody Newsletter request) {
        try {
            newsletterRepository.save(request);

            mailgunService.sendEmail(
                    request.getEmail(),
                    "Conferma Iscrizione",
                    "Grazie per esserti iscritto alla newsletter di Faziarte!"
            );

            mailgunService.sendEmail(
                    "fabio.ranocchiari@live.it",
                    "Nuova Iscrizione alla Newsletter",
                    "Un nuovo utente si è iscritto alla newsletter: " + request.getEmail()
            );

            return ResponseEntity.ok("Iscrizione completata con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'iscrizione");
        }
    }
}


