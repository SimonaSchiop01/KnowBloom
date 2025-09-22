package knowbloom.backend.services;

import knowbloom.backend.enums.Role;
import knowbloom.backend.mappers.AdminMapper;
import knowbloom.backend.mappers.RoleMapper;
import knowbloom.backend.models.AdminModel;
import knowbloom.backend.models.RoleModel;
import knowbloom.backend.records.AdminData;
import knowbloom.backend.records.RoleData;
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
public class RoleService {
    private final RoleMapper roleMapper;
    private final RoleDataService roleDataService;
    private final PasswordEncoder passwordEncoder;
    private final AdminDataService adminDataService;

    @Transactional
    public void seed(RoleData roleData) {
        Role name = roleData.name();

        if(this.roleDataService.existsByName(name)) {
            log.info("Role with name {} already exists, skipping seeding.", name);
            return;
        }

        log.info("Seeding role with name: {}", name);

        RoleModel roleModel = this.roleMapper.toModel(roleData);
        this.roleDataService.create(roleModel);

        log.info("Successfully seeded role with name: {}", name);
    }
}
