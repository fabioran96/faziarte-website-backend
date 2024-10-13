package fabioran.faziarte_website.Controllers;

import fabioran.faziarte_website.entities.Preventivo;
import fabioran.faziarte_website.repositories.PreventivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("preventivi")
public class PreventivoController {

    @Autowired
    private PreventivoRepository preventivoRepository;

    @PostMapping
    public ResponseEntity<Preventivo> createPreventivo(@RequestBody Preventivo preventivo) {
        Preventivo savedPreventivo = preventivoRepository.save(preventivo);
        return ResponseEntity.ok(savedPreventivo);
    }
}
