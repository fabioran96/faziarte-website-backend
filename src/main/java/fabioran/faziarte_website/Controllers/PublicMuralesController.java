package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.Murales;
import fabioran.faziarte_website.services.MuralesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping ("/murales")
public class PublicMuralesController {

    private final MuralesService muralesService;

    public PublicMuralesController(MuralesService muralesService) {
        this.muralesService = muralesService;
    }

    @GetMapping
    public List<Murales> getAllMurales() {
        return muralesService.getAllMurales();
    }

   /* @GetMapping("/{normalizedName}")
    public ResponseEntity<Murales> getMuraleByName(@PathVariable String normalizedName) {
        Murales murale = muralesService.findByNormalizedName(normalizedName);
        if (murale != null) {
            return ResponseEntity.ok(murale);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    */

    @GetMapping("/{id}")
    public ResponseEntity<Murales> getMuraleById(@PathVariable Long id) {
        System.out.println("ID ricevuto: " + id);
        Murales murale = muralesService.findById(id);
        if (murale != null) {
            return ResponseEntity.ok(murale);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Endpoint pubblico funzionante");
    }
}
