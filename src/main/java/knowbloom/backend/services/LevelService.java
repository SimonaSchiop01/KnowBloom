package knowbloom.backend.services;


import knowbloom.backend.dtos.requests.LevelCreateRequestDto;
import knowbloom.backend.dtos.responses.LevelResponseDto;
import knowbloom.backend.mappers.LevelMapper;
import knowbloom.backend.models.LevelModel;
import knowbloom.backend.services.data.LevelDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelService {
    private final LevelMapper levelMapper;

    private final LevelDataService levelDataService;

    @Transactional
    public LevelResponseDto create(LevelCreateRequestDto levelCreateRequestDto){
        String name = levelCreateRequestDto.getName();

        log.info("Attempting to create level with name: {}", name);

        LevelModel levelModel = this.levelMapper.toModel(levelCreateRequestDto);

        LevelModel savedLevelModel = this.levelDataService.save(levelModel);

        log.info("Level with name {} was created successfully", name);

        return this.levelMapper.toDto(savedLevelModel);
    }

    @Transactional(readOnly = true)
    public List<LevelResponseDto> getAll(){
        log.info("Getting all the levels");

        return this.levelDataService
                .findAll()
                .stream()
                .map(this.levelMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public LevelResponseDto getById(UUID id) {
        log.info("Getting level with id {}", id);

        LevelModel cityModel = this.levelDataService.findById(id);
        return this.levelMapper.toDto(cityModel);
    }

    @Transactional
    public void deleteAll(){
        log.info("Attempting to delete all the levels");

        this.levelDataService.removeAll();

        log.info("All levels have been deleted");
    }

    @Transactional
    public void deleteById(UUID id){
        log.info("Attempting to delete level with id {}", id);

        this.levelDataService.removeById(id);

        log.info("Level with id {} was deleted", id);
    }
}
