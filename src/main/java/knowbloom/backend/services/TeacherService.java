package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.LessonCreateRequestDto;
import knowbloom.backend.dtos.requests.TeacherCreateRequestDto;
import knowbloom.backend.dtos.responses.TeacherResponseDto;
import knowbloom.backend.mappers.LessonMapper;
import knowbloom.backend.mappers.TeacherMapper;
import knowbloom.backend.models.LessonModel;
import knowbloom.backend.models.TeacherModel;
import knowbloom.backend.services.data.TeacherDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherService {
    private final LessonMapper lessonMapper;
    private final TeacherMapper teacherMapper;
    private final TeacherDataService teacherDataService;

    public TeacherResponseDto create(TeacherCreateRequestDto teacherCreateRequestDto){
        String firstName = teacherCreateRequestDto.getFirstName();
        String lastName = teacherCreateRequestDto.getLastName();

        log.info("Attempting to create teacher with firstName: {} and lastName: {}", firstName, lastName);

        TeacherModel teacherModel = this.teacherMapper.toModel(teacherCreateRequestDto);

        for(LessonCreateRequestDto lessonCreateRequestDto:teacherCreateRequestDto.getLessons()){
            LessonModel lessonModel = this.lessonMapper.toModel(lessonCreateRequestDto);
            teacherModel.addLesson(lessonModel);
        }

        TeacherModel savedTeacherModel = this.teacherDataService.create(teacherModel);

        return this.teacherMapper.toDto(savedTeacherModel);
    }
}
