package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.CommentReactCreateRequestDto;
import knowbloom.backend.dtos.responses.CommentReactResponseDto;
import knowbloom.backend.mappers.CommentReactMapper;
import knowbloom.backend.models.ReviewModel;
import knowbloom.backend.models.ReviewReactModel;
import knowbloom.backend.services.data.CommentDataService;
import knowbloom.backend.services.data.CommentReactDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentReactService {
    private final CommentDataService commentDataService;
    private final CommentReactMapper commentReactMapper;
    private final CommentReactDataService commentReactDataService;

    @Transactional
    public CommentReactResponseDto create(UUID commentId, CommentReactCreateRequestDto commentReactCreateRequestDto){
        log.info("Attempting to create comment react");

        ReviewModel reviewModel = this.commentDataService.findById(commentId);

        ReviewReactModel reviewReactModel = this.commentReactMapper.toModel(commentReactCreateRequestDto);

        reviewReactModel.setComment(reviewModel);

        ReviewReactModel savedReviewReactModel = this.commentReactDataService.save(reviewReactModel);

        log.info("Comment react was created successfully");

        return this.commentReactMapper.toDto(savedReviewReactModel);
    }

    @Transactional(readOnly = true)
    public List<CommentReactResponseDto> getAllByCommentId(UUID commentId){
        log.info("Getting all the comment reacts");
        return this.commentReactDataService
            .findAllByCommentId(commentId)
            .stream()
            .map(this.commentReactMapper::toDto)
            .toList();
    }

    @Transactional
    public void deleteAllByCommentId(UUID commentId){
        log.info("Attempting to delete all reacts by comment id {}", commentId);
        this.commentReactDataService.removeAllByCommentId(commentId);
        log.info("All reacts of this comment id {} have been deleted", commentId);
    }

    @Transactional(readOnly = true)
    public CommentReactResponseDto getById(UUID id) {
        log.info("Getting the comment react with id {}", id);
        ReviewReactModel reviewReactModel = this.commentReactDataService.findById(id);
        return this.commentReactMapper.toDto(reviewReactModel);
    }

    @Transactional
    public void deleteAll(){
        log.info("Attempting to delete all the comment reacts");
        this.commentReactDataService.removeAll();
        log.info("All comment reacts have been deleted");
    }

    @Transactional
    public void deleteById(UUID id){
        log.info("Attempting to delete comment react by id {}", id);
        this.commentReactDataService.removeById(id);
        log.info("Comment react with id {} was deleted", id);
    }
}