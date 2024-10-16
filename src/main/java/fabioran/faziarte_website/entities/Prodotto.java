package fabioran.faziarte_website.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String imageUrl;
}
