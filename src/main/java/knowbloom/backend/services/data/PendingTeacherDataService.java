package knowbloom.backend.services.data;

import knowbloom.backend.constants.CategoryConstants;
import knowbloom.backend.constants.CommentConstants;
import knowbloom.backend.constants.PendingTeacherConstants;
import knowbloom.backend.constants.UserConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CategoryModel;
import knowbloom.backend.models.PendingMemberModel;
import knowbloom.backend.models.PendingTeacherModel;
import knowbloom.backend.repositories.PendingTeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingTeacherDataService {
    private final UserDataService userDataService;
    private final PendingTeacherRepository pendingTeacherRepository;

    @Transactional
    public PendingTeacherModel create(PendingTeacherModel pendingTeacherModel) {
        String email = pendingTeacherModel.getEmail();

        log.info("Attempting to save pending teacher with email: {}", email);

        if (this.userDataService.existsByEmail(email)) {
            throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        if(this.pendingTeacherRepository.existsByEmail(email)){
            throw new ConflictException(PendingTeacherConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        PendingTeacherModel savedPendingTeacherModel = this.pendingTeacherRepository.save(pendingTeacherModel);

        log.info("Successfully saved pending teacher with email: {}", email);

        return savedPendingTeacherModel;
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email){
        return this.pendingTeacherRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public PendingTeacherModel findById(UUID id){
        log.info("Fetching pending teacher by id: {}", id);
        return this.pendingTeacherRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(PendingTeacherConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempt to remove pending teacher with id: {}", id);
        this.pendingTeacherRepository.deleteById(id);
        log.info("Pending teacher with id: {} has been deleted", id);
    }
}
