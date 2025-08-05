package knowbloom.backend.repositories;

import knowbloom.backend.models.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectModel, UUID> {
    Optional<SubjectRepository> findByName(String name);
}
