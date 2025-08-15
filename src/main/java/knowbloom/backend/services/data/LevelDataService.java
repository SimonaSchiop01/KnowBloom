package knowbloom.backend.services.data;

import knowbloom.backend.constants.CityConstants;
import knowbloom.backend.constants.LevelConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CityModel;
import knowbloom.backend.models.LevelModel;
import knowbloom.backend.repositories.LevelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LevelDataService {
    private final LevelRepository levelRepository;

    @Transactional
    public LevelModel save(LevelModel levelModel) {
        String name = levelModel.getName();

        log.info("Attempting to save level with name: {}", name);

        if(this.levelRepository.existsCityModelByName(name)){
            throw new ConflictException(CityConstants.ALREADY_EXISTS_BY_NAME);
        }

        LevelModel savedLevelModel = this.levelRepository.save(levelModel);

        log.info("Successfully saved level with name: {}", name);

        return savedLevelModel;
    }

    @Transactional(readOnly = true)
    public List<LevelModel> findAll(){
        log.info("Fetching all the levels");

        return this.levelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public LevelModel findById(UUID id){
        log.info("Fetching the level by id: {}", id);

        return this.levelRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(LevelConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeAll(){
        log.info("Attempt to remove all the levels");

        this.levelRepository.deleteAll();

        log.info("All levels have been removed");
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempting to remove level with id {}", id);

        this.levelRepository.deleteById(id);

        log.info("Attempting to remove level with id {}", id);
    }
}
