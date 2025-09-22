package knowbloom.backend.services.data;

import knowbloom.backend.constants.CategoryConstants;
import knowbloom.backend.constants.TeacherConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CategoryModel;
import knowbloom.backend.models.TeacherModel;
import knowbloom.backend.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherDataService {
    private final TeacherRepository teacherRepository;

    @Transactional
    public TeacherModel create(TeacherModel teacherModel) {
        String phoneNumber = teacherModel.getPhoneNumber();

        log.info("Attempting to save teacher with phone number: {}", phoneNumber);

        if(this.teacherRepository.existsTeacherModelByPhoneNumber(phoneNumber)){
            throw new ConflictException(TeacherConstants.ALREADY_EXISTS_BY_PHONE_NUMBER);
        }

        TeacherModel savedTeacherModel = this.teacherRepository.save(teacherModel);

        log.info("Successfully saved teacher with phone number: {}", phoneNumber);

        return savedTeacherModel;
    }

    @Transactional(readOnly = true)
    public TeacherModel getById(UUID id) {
        log.info("Getting teacher with id: {}", id);
        return this.teacherRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(TeacherConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.info("Checking if teacher exists by email: {}", email);
        return this.teacherRepository.existsByEmail(email);
    }
}
