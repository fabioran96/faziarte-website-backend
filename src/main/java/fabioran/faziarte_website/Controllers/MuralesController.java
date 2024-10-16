package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.Murales;
import fabioran.faziarte_website.repositories.MuralesRepository;
import fabioran.faziarte_website.services.MuralesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/murales")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class MuralesController {

    private final MuralesService muralesService;

    @GetMapping
    public List<Murales> getAllMurales() {
        return muralesService.getAllMurales();
    }

    @PostMapping
    public Murales addMurales(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam MultipartFile image
    ) {
        return muralesService.addMurales(name, description, lat, lng, image);
    }

        @Autowired
        private MuralesRepository muralesRepository;  // Assumi che ci sia un repository JPA per salvare i murales

        @PostMapping("/add-murale")
        public ResponseEntity<String> addMurale(@RequestBody Murales murales) {
            try {
                muralesRepository.save(murales); // Salva il nuovo murale nel database
                return ResponseEntity.ok("Murale aggiunto con successo!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'aggiunta del murale");
            }
        }
    }

