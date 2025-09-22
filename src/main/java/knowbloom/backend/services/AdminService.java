package knowbloom.backend.services;
import knowbloom.backend.enums.Role;
import knowbloom.backend.mappers.AdminMapper;
import knowbloom.backend.models.AdminModel;
import knowbloom.backend.models.RoleModel;
import knowbloom.backend.records.AdminData;
import knowbloom.backend.services.data.AdminDataService;
import knowbloom.backend.services.data.RoleDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminMapper adminMapper;
    private final RoleDataService roleDataService;
    private final PasswordEncoder passwordEncoder;
    private final AdminDataService adminDataService;

    @Transactional
    public void seed(AdminData adminData) {
        String email = adminData.email();
        String phoneNumber = adminData.phoneNumber();
        String password = adminData.password();

        if(this.adminDataService.existsByEmail(email)) {
            log.info("Admin with email {} already exists, skipping seeding.", email);
            return;
        }

        if(this.adminDataService.existsByPhoneNumber(phoneNumber)) {
            log.info("Admin with phone number {} already exists, skipping seeding.", phoneNumber);
            return;
        }

        log.info("Seeding admin with email: {}", email);

        AdminModel adminModel = this.adminMapper.toModel(adminData);

        String hashedPassword = this.passwordEncoder.encode(password);
        adminModel.setPassword(hashedPassword);

        RoleModel adminRoleModel = this.roleDataService.findByName(Role.ADMIN);
        adminModel.addRole(adminRoleModel);

        this.adminDataService.create(adminModel);

        log.info("Successfully seeded admin with email: {}", email);
    }
}
