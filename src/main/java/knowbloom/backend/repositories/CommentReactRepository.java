package knowbloom.backend.repositories;

import knowbloom.backend.models.ReviewReactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentReactRepository extends JpaRepository<ReviewReactModel, UUID> {
    List<ReviewReactModel> findAllByCommentId(UUID commentId);

    void deleteAllByCommentId(UUID commentId);
}
