package knowbloom.backend.services.data;

import knowbloom.backend.constants.CityConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CityModel;
import knowbloom.backend.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityDataService {
    private final CityRepository cityRepository;

    @Transactional
    public CityModel save(CityModel cityModel) {
        String name = cityModel.getName();

        log.info("Attempting to save city with name: {}", name);

        if(this.cityRepository.existsCityModelByName(name)){
            throw new ConflictException(CityConstants.ALREADY_EXISTS_BY_NAME);
        }

        CityModel savedCityModel = this.cityRepository.save(cityModel);

        log.info("Successfully saved city with name: {}", name);

        return savedCityModel;
    }

    @Transactional(readOnly = true)
    public List<CityModel> findAll(){
        log.info("Fetching all the cities");
        return this.cityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CityModel findById(UUID id){
        log.info("Fetching the city by id: {}", id);
        return this.cityRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(CityConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeAll(){
        log.info("Attempt to remove all the cities");
        this.cityRepository.deleteAll();
        log.info("All cities have been removed");
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempting to remove city with id {}", id);
        this.cityRepository.deleteById(id);
        log.info("Attempting to remove city with id {}", id);
    }
}
