package knowbloom.backend.repositories;

import knowbloom.backend.models.LevelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LevelRepository extends JpaRepository<LevelModel, UUID> {
    Optional<LevelModel> findByName(String name);

    boolean existsCityModelByName(String name);
}
