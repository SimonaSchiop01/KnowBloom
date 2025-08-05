package knowbloom.backend.repositories;

import knowbloom.backend.models.ReplyReactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReplyReactRepository extends JpaRepository<ReplyReactModel, UUID> {
}
