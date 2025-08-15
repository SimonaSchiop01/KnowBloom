package knowbloom.backend.services.data;

import knowbloom.backend.constants.CommentConstants;
import knowbloom.backend.constants.PendingMemberConstants;
import knowbloom.backend.constants.UserConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CommentModel;
import knowbloom.backend.models.PendingMemberModel;
import knowbloom.backend.repositories.PendingMemberRepository;
import knowbloom.backend.repositories.PendingTeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingMemberDataService {

    private final UserDataService userDataService;
    private final PendingMemberRepository pendingMemberRepository;

    @Transactional
    public PendingMemberModel save(PendingMemberModel pendingMemberModel) {
        String email = pendingMemberModel.getEmail();

        log.info("Attempting to save pending member with email: {}", email);

        if (this.userDataService.existsByEmail(email)) {
            throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        if (this.pendingMemberRepository.existsByEmail(email)) {
            throw new ConflictException(PendingMemberConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        PendingMemberModel savedPendingMemberModel = this.pendingMemberRepository.save(pendingMemberModel);

        log.info("Successfully saved pending member with email: {}", email);

        return savedPendingMemberModel;
    }

    @Transactional(readOnly = true)
    public PendingMemberModel findByEmail(String email){
        log.info("Fetching pending member by email: {}", email);
        return this.pendingMemberRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException(CommentConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email){
        return this.pendingMemberRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber){
        return this.pendingMemberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempt to remove pending member wit id: {}", id);
        this.pendingMemberRepository.deleteById(id);
        log.info("Pending member with id: {} has been deleted", id);
    }
}
