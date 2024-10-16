package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.Preventivo;
import fabioran.faziarte_website.repositories.PreventivoRepository;
import fabioran.faziarte_website.services.MailgunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preventivi")
@CrossOrigin(origins = "http://localhost:5173")
public class PreventivoController {

    @Autowired
    private PreventivoRepository preventivoRepository;

    @Autowired
    private MailgunService mailgunService;

    @PostMapping
    public ResponseEntity<Preventivo> createPreventivo(@RequestBody Preventivo preventivo) {
        Preventivo savedPreventivo = preventivoRepository.save(preventivo);

        String subjectClient = "Conferma richiesta preventivo";
        String textClient = "Grazie per la tua richiesta, " + preventivo.getEmail() +
                ". Verrai ricontattato presto.\n\n" +
                "Dettagli richiesta:\n" +
                "Servizio: " + preventivo.getServiceType() + "\n" +
                "Materiale: " + preventivo.getMaterial() + "\n" +
                "Dimensioni: " + preventivo.getSize() + "\n" +
                "Messaggio: " + preventivo.getMessage();

        String subjectAdmin = "Nuova richiesta di preventivo";
        String textAdmin = "È stata ricevuta una nuova richiesta di preventivo:\n\n" +
                "Email del cliente: " + preventivo.getEmail() + "\n" +
                "Servizio: " + preventivo.getServiceType() + "\n" +
                "Materiale: " + preventivo.getMaterial() + "\n" +
                "Dimensioni: " + preventivo.getSize() + "\n" +
                "Messaggio: " + preventivo.getMessage();

        try {
            mailgunService.sendEmail(
                    preventivo.getEmail(), subjectClient, textClient
            );

            mailgunService.sendEmail(
                    "fabio.ranocchiari@live.it", subjectAdmin, textAdmin
            );

        } catch (Exception e) {
            throw new RuntimeException("Errore durante l'invio dell'email", e);
        }

        return ResponseEntity.ok(savedPreventivo);
    }
}