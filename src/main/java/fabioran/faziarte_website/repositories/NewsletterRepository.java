package fabioran.faziarte_website.repositories;

import fabioran.faziarte_website.entities.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}
