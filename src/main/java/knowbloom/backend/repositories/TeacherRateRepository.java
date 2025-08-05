package knowbloom.backend.repositories;

import knowbloom.backend.models.TeacherRateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeacherRateRepository extends JpaRepository<TeacherRateModel, UUID> {
}
