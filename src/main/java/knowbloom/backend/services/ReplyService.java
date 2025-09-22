package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.ReplyCreateRequestDto;
import knowbloom.backend.dtos.responses.ReplyResponseDto;
import knowbloom.backend.mappers.ReplyMapper;
import knowbloom.backend.models.ReviewModel;
import knowbloom.backend.models.ReplyModel;
import knowbloom.backend.services.data.ReplyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyMapper replyMapper;
    private final CommentService commentService;
    private final ReplyDataService replyDataService;

    @Transactional
    public ReplyResponseDto create(ReplyCreateRequestDto replyCreateRequestDto){
        UUID commentId = replyCreateRequestDto.getCommentId();

        ReplyModel replyModel = this.replyMapper.toModel(replyCreateRequestDto);

        ReviewModel reviewModel = this.commentService.getModelById(commentId);
        replyModel.setReview(reviewModel);

        ReplyModel savedReplyModel = this.replyDataService.save(replyModel);

        return this.replyMapper.toDto(savedReplyModel);
    }

    @Transactional(readOnly = true)
    public List<ReplyResponseDto> getAll() {
        log.info("Getting all the replies");
        return this.replyDataService
            .findAll()
            .stream()
            .map(this.replyMapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public ReplyResponseDto getById(UUID id) {
        log.info("Getting reply with id {}", id);
        ReplyModel replyModel = this.replyDataService.findById(id);
        return this.replyMapper.toDto(replyModel);
    }

    @Transactional
    public void deleteAll(){
        log.info("Attempting to delete all the replies");
        this.replyDataService.removeAll();
        log.info("All replies have been deleted");
    }

    @Transactional
    public void deleteById(UUID id){
        log.info("Attempting to delete reply with id {}", id);
        this.replyDataService.removeById(id);
        log.info("Reply with id {} was deleted", id);
    }
}
