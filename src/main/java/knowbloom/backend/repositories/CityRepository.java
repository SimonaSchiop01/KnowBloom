package knowbloom.backend.repositories;

import knowbloom.backend.models.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<CityModel, UUID> {
    Optional<CityModel> findByName(String name);

    boolean existsCityModelByName(String name);
}
