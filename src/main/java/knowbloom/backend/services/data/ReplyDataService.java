package knowbloom.backend.services.data;

import knowbloom.backend.constants.CommentConstants;
import knowbloom.backend.constants.ReplyConstants;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CommentModel;
import knowbloom.backend.models.ReplyModel;
import knowbloom.backend.repositories.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyDataService {
    private final ReplyRepository replyRepository;

    @Transactional
    public ReplyModel save(ReplyModel replyModel){
        String title = replyModel.getTitle();

        log.info("Attempting to save reply with title: {}", title);

        ReplyModel savedReplyModel = this.replyRepository.save(replyModel);

        log.info("Successfully saved reply with title: {}", title);

        return savedReplyModel;
    }

    @Transactional(readOnly = true)
    public List<ReplyModel> findAll(){
        log.info("Fetching all the replies");

        return this.replyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ReplyModel findById(UUID id){
        log.info("Fethcing reply by id: {}", id);

        return this.replyRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ReplyConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeAll(){
        log.info("Attempt to remove all the replies");
        this.replyRepository.deleteAll();
        log.info("All replies have been removed");
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempt to remove reply wit id: {}", id);
        this.replyRepository.deleteById(id);
        log.info("Reply wit id: {} has been deleted", id);
    }

}
