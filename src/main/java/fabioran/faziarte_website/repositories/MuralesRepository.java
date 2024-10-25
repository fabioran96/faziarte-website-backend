package fabioran.faziarte_website.repositories;

import fabioran.faziarte_website.entities.Murales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuralesRepository extends JpaRepository<Murales, Long> {
    @Query("SELECT m FROM Murales m WHERE LOWER(REPLACE(REPLACE(REPLACE(REPLACE(m.name, ' ', '-'), 'à', 'a'), 'è', 'e'), 'ì', 'i')) = :normalizedName")
    Murales findByNormalizedName(@Param("normalizedName") String normalizedName);

}
