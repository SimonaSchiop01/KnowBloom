package knowbloom.backend.repositories;

import knowbloom.backend.models.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<MemberModel, UUID> {
    boolean existsByEmail(String email);
    Optional<MemberModel> findByEmail(String email);
}
