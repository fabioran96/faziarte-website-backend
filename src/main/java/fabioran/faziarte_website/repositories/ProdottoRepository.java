package fabioran.faziarte_website.repositories;

import fabioran.faziarte_website.entities.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
}
