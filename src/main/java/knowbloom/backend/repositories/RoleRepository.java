package knowbloom.backend.repositories;

import knowbloom.backend.enums.Role;
import knowbloom.backend.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    boolean existsByName(Role name);
    Optional<RoleModel> findByName(Role name);
}