package slash.code.game.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    void deleteAllByValueGreaterThan(Integer value);

}
