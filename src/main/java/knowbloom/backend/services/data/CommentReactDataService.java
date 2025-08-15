package knowbloom.backend.services.data;

import knowbloom.backend.constants.CommentConstants;
import knowbloom.backend.constants.CommentReactConstants;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CommentModel;
import knowbloom.backend.models.CommentReactModel;
import knowbloom.backend.repositories.CommentReactRepository;
import knowbloom.backend.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentReactDataService {
    private final CommentRepository commentRepository;
    private final CommentReactRepository commentReactRepository;

    @Transactional
    public CommentReactModel save(CommentReactModel commentReactModel){
        log.info("Attempting to save a comment react");

        CommentReactModel savedCommentReactModel = this.commentReactRepository.save(commentReactModel);

        log.info("Successfully saved comment react");

        return savedCommentReactModel;
    }

    @Transactional(readOnly = true)
    public List<CommentReactModel> findAllByCommentId(UUID commentId){
        log.info("Fetching all the comment reacts");
        return commentReactRepository.findAllByCommentId(commentId);
    }

    @Transactional
    public void removeAllByCommentId(UUID commentId){
        log.info("Attempt to remove all comment react with comment id {}", commentId);

        if (!this.commentRepository.existsById(commentId)) {
            throw new NotFoundException(CommentConstants.NOT_FOUND_BY_ID);
        }

        this.commentReactRepository.deleteAllByCommentId(commentId);
        log.info("All comment reacts belonging to comment with id {} has been removed", commentId);
    }

    @Transactional(readOnly = true)
    public CommentReactModel findById(UUID id){
        log.info("Fetching comment react by id: {}", id);
        return this.commentReactRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(CommentReactConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeAll(){
        log.info("Attempt to remove all the comment reacts");
        this.commentReactRepository.deleteAll();
        log.info("All comment reacts have been removed");
    }

    @Transactional
    public void removeById(UUID id) {
        log.info("Attempt to remove comment react with id {}", id);

        if (!this.commentReactRepository.existsById(id)) {
            throw new NotFoundException(CommentReactConstants.NOT_FOUND_BY_ID);
        }

        this.commentReactRepository.deleteById(id);
        log.info("Comment react with id {} has been removed", id);
    }
}
