package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.RichiestaParticolare;
import fabioran.faziarte_website.repositories.RichiestaParticolareRepository;
import fabioran.faziarte_website.services.MailgunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scrivimi")
@CrossOrigin(origins = "http://localhost:5173")
public class RichiestaParticolareController {

    @Autowired
    private RichiestaParticolareRepository richiestaRepository;

    @Autowired
    private MailgunService mailgunService;

    @PostMapping
    public ResponseEntity<RichiestaParticolare> createRichiesta(@RequestBody RichiestaParticolare richiesta) {
        RichiestaParticolare savedRichiesta = richiestaRepository.save(richiesta);

        String subjectClient = "Conferma ricezione richiesta";
        String textClient = "Grazie per averci contattato, " + richiesta.getName() +
                ". Ti risponderemo al più presto.\n\n" +
                "Il tuo messaggio:\n" + richiesta.getMessage();

        String subjectAdmin = "Nuova richiesta tramite il modulo 'Scrivimi'";
        String textAdmin = "È stata ricevuta una nuova richiesta tramite 'Scrivimi'.\n\n" +
                "Nome: " + richiesta.getName() + " " + richiesta.getSurname() + "\n" +
                "Email: " + richiesta.getEmail() + "\n" +
                "Messaggio: " + richiesta.getMessage();

        try {
            mailgunService.sendEmail(
                    richiesta.getEmail(), subjectClient, textClient
            );

            mailgunService.sendEmail(
                    "fabio.ranocchiari@live.it", subjectAdmin, textAdmin
            );

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'invio dell'email", e);
        }

        return ResponseEntity.ok(savedRichiesta);
    }
}