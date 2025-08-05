package knowbloom.backend.repositories;

import knowbloom.backend.models.PendingMemberModel;
import knowbloom.backend.models.PendingTeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PendingMemberRepository extends JpaRepository<PendingMemberModel, UUID> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<PendingMemberModel> findByPhoneNumber(String phoneNumber);
    Optional<PendingMemberModel> findByEmail(String email);
}
