package knowbloom.backend.services.data;

import knowbloom.backend.constants.CategoryConstants;
import knowbloom.backend.constants.CommentConstants;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CategoryModel;
import knowbloom.backend.models.CommentModel;
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
public class CommentDataService {
    private final CommentRepository commentRepository;

    @Transactional
    public CommentModel save(CommentModel commentModel){
        String title = commentModel.getTitle();

        log.info("Attempting to save comment with title: {}", title);

        CommentModel savedCommentModel = this.commentRepository.save(commentModel);

        log.info("Successfully saved comment with title: {}", title);

        return savedCommentModel;
    }

    @Transactional(readOnly = true)
    public List<CommentModel> findAll(){
        log.info("Fetching all the comments");
        return this.commentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CommentModel findById(UUID id){
        log.info("Fetching comment by id: {}", id);
        return this.commentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(CommentConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeAll(){
        log.info("Attempt to remove all the comments");
        this.commentRepository.deleteAll();
        log.info("All comments have been removed");
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempt to remove comment wit id: {}", id);
        this.commentRepository.deleteById(id);
        log.info("Comment wit id: {} has been deleted", id);
    }
}
