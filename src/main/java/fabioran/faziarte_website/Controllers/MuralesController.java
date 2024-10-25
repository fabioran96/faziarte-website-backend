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
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequiredArgsConstructor
public class MuralesController {

    private final MuralesService muralesService;

    @GetMapping ("/murales")
    public List<Murales> getAllMurales() {
        return muralesService.getAllMurales();
    }

    @PostMapping ("/murales")
    public Murales addMurales(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam MultipartFile image) {
        return muralesService.addMurales(name, description, lat, lng, image);
    }

    @PutMapping("/murales/{id}")
    public ResponseEntity<Murales> updateMurales(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(required = false) MultipartFile image) {
        Murales updatedMurales = muralesService.updateMurales(id, name, description, lat, lng, image);
        return ResponseEntity.ok(updatedMurales);
    }

    @DeleteMapping("/murales/{id}")
    public ResponseEntity<Void> deleteMurales(@PathVariable Long id) {
        muralesService.deleteMurales(id);
        return ResponseEntity.noContent().build();
    }


}