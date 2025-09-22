package knowbloom.backend.services.data;

import knowbloom.backend.constants.AdminConstants;
import knowbloom.backend.constants.CategoryConstants;
import knowbloom.backend.constants.UserConstants;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.AdminModel;
import knowbloom.backend.models.UserModel;
import knowbloom.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserModel findByEmail(String email){
        log.info("Fetching user by email: {}", email);
        return this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserConstants.NOT_FOUND_BY_EMAIL));
    }
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber){
        return this.userRepository.existsByPhoneNumber(phoneNumber);
    }

}
