package slash.code.game.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayRepository extends JpaRepository<Play, UUID> {



}
