package knowbloom.backend.services.data;

import knowbloom.backend.constants.MemberConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.MemberModel;
import knowbloom.backend.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDataService {
    private final UserDataService userDataService;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberModel create(MemberModel memberModel) {
        String email = memberModel.getEmail();

        log.info("Attempting to create member with email: {}", email);

        if (this.userDataService.existsByEmail(email)) {
            throw new ConflictException(MemberConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        MemberModel savedMemberModel = this.memberRepository.save(memberModel);

        log.info("Successfully created member with email: {}", email);

        return savedMemberModel;
    }

    @Transactional(readOnly = true)
    public MemberModel getById(UUID id) {
        log.info("Getting member with id: {}", id);
        return this.memberRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MemberConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.info("Checking if member exists by email: {}", email);
        return this.memberRepository.existsByEmail(email);
    }
}
