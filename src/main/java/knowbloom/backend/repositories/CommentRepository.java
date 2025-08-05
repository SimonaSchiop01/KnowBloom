package knowbloom.backend.repositories;

import knowbloom.backend.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentModel, UUID> {
    List<CommentModel> findByTitle(String title);
}
