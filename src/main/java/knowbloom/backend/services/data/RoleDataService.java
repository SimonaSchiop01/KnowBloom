package knowbloom.backend.services.data;

import knowbloom.backend.constants.AdminConstants;
import knowbloom.backend.constants.ReplyConstants;
import knowbloom.backend.constants.RoleConstants;
import knowbloom.backend.enums.Role;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.AdminModel;
import knowbloom.backend.models.ReplyModel;
import knowbloom.backend.models.RoleModel;
import knowbloom.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleDataService {
    private final RoleRepository roleRepository;

    @Transactional
    public RoleModel create(RoleModel roleModel) {
        Role name = roleModel.getName();

        log.info("Attempting to create role with name: {}", name);

        if (this.roleRepository.existsByName(name)) {
            throw new ConflictException(RoleConstants.ALREADY_EXISTS_BY_NAME);
        }

        RoleModel savedRoleModel = this.roleRepository.save(roleModel);

        log.info("Successfully created role with name: {}", name);

        return savedRoleModel;
    }
    @Transactional(readOnly = true)
    public RoleModel findByName(Role name){
        log.info("Fetching role by name: {}", name);

        return this.roleRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException(ReplyConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public boolean existsByName(Role name) {
        log.info("Checking if role exists by name: {}", name);
        return this.roleRepository.existsByName(name);
    }
}
