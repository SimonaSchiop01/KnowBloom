package knowbloom.backend.repositories;

import knowbloom.backend.models.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<TeacherModel, UUID> {
    Optional<TeacherModel> findByPhoneNumber(String phoneNumber);

    boolean existsTeacherModelByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

}
