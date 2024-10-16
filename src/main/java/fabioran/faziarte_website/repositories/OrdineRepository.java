package fabioran.faziarte_website.repositories;

import fabioran.faziarte_website.entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
}
