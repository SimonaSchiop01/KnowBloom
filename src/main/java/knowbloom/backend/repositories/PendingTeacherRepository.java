package knowbloom.backend.repositories;

import knowbloom.backend.models.PendingMemberModel;
import knowbloom.backend.models.PendingTeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface PendingTeacherRepository extends JpaRepository<PendingTeacherModel, UUID> {
    boolean existsByEmail(String email);
    Optional<PendingTeacherModel> findByPhoneNumber(String phoneNumber);
    Optional<PendingTeacherModel> findByEmail(String email);

}