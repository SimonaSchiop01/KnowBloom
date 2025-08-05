package knowbloom.backend.repositories;

import knowbloom.backend.models.CommentModel;
import knowbloom.backend.models.CommentReactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentReactRepository extends JpaRepository<CommentReactModel, UUID> {
    List<CommentReactModel> findAllByCommentId(UUID commentId);

    void deleteAllByCommentId(UUID commentId);
}
