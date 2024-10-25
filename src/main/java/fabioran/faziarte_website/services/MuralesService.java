package fabioran.faziarte_website.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fabioran.faziarte_website.entities.Murales;
import fabioran.faziarte_website.repositories.MuralesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MuralesService {

    private final MuralesRepository muralesRepository;
    private final Cloudinary cloudinary;

    public List<Murales> getAllMurales() {
        return muralesRepository.findAll();
    }

    public Murales addMurales(String name, String description, double lat, double lng, MultipartFile image) {
        String imageUrl = saveImage(image);
        Murales murales = new Murales(null, name, description, lat, lng, imageUrl);
        return muralesRepository.save(murales);
    }

    private String saveImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'immagine", e);
        }
    }

    public Murales updateMurales(Long id, String name, String description, double lat, double lng, MultipartFile image) {
        Murales murales = muralesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Murale non trovato"));

        murales.setName(name);
        murales.setDescription(description);
        murales.setLat(lat);
        murales.setLng(lng);

        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            murales.setImageUrl(imageUrl);
        }

        return muralesRepository.save(murales);
    }

    public void deleteMurales(Long id) {
        Murales murales = muralesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Murale non trovato"));

        // deleteImageFromCloudinary(murales.getImageUrl());

        muralesRepository.deleteById(id);
    }

    public Murales findByNormalizedName(String normalizedName) {
        return muralesRepository.findByNormalizedName(normalizedName);
    }

    public Murales findById(Long id) {
        return muralesRepository.findById(id).orElse(null);
    }

    // Se vuoi implementare l'eliminazione dell'immagine da Cloudinary
    /*
    private void deleteImageFromCloudinary(String imageUrl) {
        try {
            String publicId = extractPublicIdFromUrl(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Errore nell'eliminazione dell'immagine", e);
        }
    }

    private String extractPublicIdFromUrl(String url) {
    }
    */
}
