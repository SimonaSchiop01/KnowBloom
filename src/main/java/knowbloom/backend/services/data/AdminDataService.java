package knowbloom.backend.services.data;


import knowbloom.backend.constants.AdminConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.AdminModel;
import knowbloom.backend.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminDataService {
    private final UserDataService userDataService;
    private final AdminRepository adminRepository;

    @Transactional
    public AdminModel create(AdminModel adminModel) {
        String email = adminModel.getEmail();

        log.info("Attempting to create admin with email: {}", email);

        if (this.userDataService.existsByEmail(email)) {
            throw new ConflictException(AdminConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        AdminModel savedAdminModel = this.adminRepository.save(adminModel);

        log.info("Successfully created admin with email: {}", email);

        return savedAdminModel;
    }

    @Transactional(readOnly = true)
    public AdminModel getById(UUID id) {
        log.info("Getting admin with id: {}", id);
        return this.adminRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(AdminConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.info("Checking if admin exists by email: {}", email);
        return this.adminRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        log.info("Checking if admin exists by phone number: {}", phoneNumber);
        return this.adminRepository.existsByPhoneNumber(phoneNumber);
    }
}
