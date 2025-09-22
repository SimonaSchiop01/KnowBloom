package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.CommentCreateRequestDto;
import knowbloom.backend.dtos.responses.CommentResponseDto;
import knowbloom.backend.mappers.CommentMapper;
import knowbloom.backend.models.*;
import knowbloom.backend.services.data.CommentDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentDataService commentDataService;

    public CommentResponseDto create(CommentCreateRequestDto commentCreateRequestDto){
        String title = commentCreateRequestDto.getTitle();

        log.info("Attempting to create comment with title: {}", title);

        ReviewModel reviewModel = this.commentMapper.toModel(commentCreateRequestDto);
        ReviewModel savedReviewModel = this.commentDataService.save(reviewModel);

        return this.commentMapper.toDto(savedReviewModel);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAll() {
        log.info("Getting all the categories");
        return this.commentDataService
            .findAll()
            .stream()
            .map(this.commentMapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getById(UUID id) {
        log.info("Getting comment with id {}", id);
        ReviewModel reviewModel = this.commentDataService.findById(id);
        return this.commentMapper.toDto(reviewModel);
    }

    @Transactional(readOnly = true)
    public ReviewModel getModelById(UUID id){
        log.info("Getting comment model wit id {}", id);
        return this.commentDataService.findById(id);
    }

    @Transactional
    public void deleteAll(){
        log.info("Attempting to delete all the comments");
        this.commentDataService.removeAll();
        log.info("All comments have been deleted");
    }

    @Transactional
    public void deleteById(UUID id){
        log.info("Attempting to delete comment with id {}", id);
        this.commentDataService.removeById(id);
        log.info("Comment with id {} was deleted", id);
    }
}
