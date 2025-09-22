package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.PendingTeacherCreateRequestDto;
import knowbloom.backend.dtos.responses.PendingTeacherResponseDto;
import knowbloom.backend.mappers.PendingTeacherMapper;
import knowbloom.backend.models.PendingTeacherModel;
import knowbloom.backend.services.data.PendingTeacherDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingTeacherService {
    private final PendingTeacherMapper pendingTeacherMapper;

    private final PendingTeacherDataService pendingTeacherDataService;

    private final PasswordEncoder passwordEncoder; // injectat automat de Spring


    @Transactional
    public PendingTeacherResponseDto create(PendingTeacherCreateRequestDto pendingTeacherCreateRequestDto){
        String email = pendingTeacherCreateRequestDto.getEmail();

        log.info("Attempting to create pending teacher with email: {}", email);

        pendingTeacherCreateRequestDto.setPassword(passwordEncoder.encode(pendingTeacherCreateRequestDto.getPassword()));

        PendingTeacherModel pendingTeacherModel = this.pendingTeacherMapper.toModel(pendingTeacherCreateRequestDto);

        PendingTeacherModel savedPendingTeacherModel = this.pendingTeacherDataService.create(pendingTeacherModel);

        return this.pendingTeacherMapper.toDto(savedPendingTeacherModel);
    }
}
