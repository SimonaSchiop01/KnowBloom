package knowbloom.backend.repositories;

import knowbloom.backend.models.ReplyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReplyRepository extends JpaRepository<ReplyModel, UUID> {
}
