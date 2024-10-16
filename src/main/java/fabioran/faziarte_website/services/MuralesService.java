package fabioran.faziarte_website.services;

import fabioran.faziarte_website.entities.Murales;
import fabioran.faziarte_website.repositories.MuralesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MuralesService {

    private final MuralesRepository muraleRepository;
    private final Path uploadDir = Paths.get("uploads");

    public List<Murales> getAllMurales() {
        return muraleRepository.findAll();
    }

    public Murales addMurales(String name, String description, double lat, double lng, MultipartFile image) {
        String imageUrl = saveImage(image);
        Murales murales = new Murales(null, name, description, lat, lng, imageUrl);
        return muraleRepository.save(murales);
    }

    private String saveImage(MultipartFile image) {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(image.getOriginalFilename());
            Files.copy(image.getInputStream(), filePath);
            return "/uploads/" + image.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'immagine", e);
        }
    }
}
