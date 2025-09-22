package knowbloom.backend.repositories;

import knowbloom.backend.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<ReviewModel, UUID> {
    List<ReviewModel> findByTitle(String title);
}
