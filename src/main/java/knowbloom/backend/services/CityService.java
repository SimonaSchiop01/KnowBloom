package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.CityCreateRequestDto;
import knowbloom.backend.dtos.responses.CityResponseDto;
import knowbloom.backend.mappers.CityMapper;
import knowbloom.backend.models.CityModel;
import knowbloom.backend.services.data.CityDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {
    private final CityMapper cityMapper;

    private final CityDataService cityDataService;

    @Transactional
    public CityResponseDto create(CityCreateRequestDto cityCreateRequestDto){
        String name = cityCreateRequestDto.getName();

        log.info("Attempting to create city with name: {}", name);

        CityModel cityModel = this.cityMapper.toModel(cityCreateRequestDto);

        CityModel savedCityModel = this.cityDataService.save(cityModel);

        log.info("City with name {} was created successfully", name);

        return this.cityMapper.toDto(savedCityModel);
    }

    @Transactional(readOnly = true)
    public List<CityResponseDto> getAll(){
        log.info("Getting all the cities");

        return this.cityDataService
                .findAll()
                .stream()
                .map(this.cityMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CityResponseDto getById(UUID id) {
        log.info("Getting city with id {}", id);

        CityModel cityModel = this.cityDataService.findById(id);
        return this.cityMapper.toDto(cityModel);
    }

    @Transactional
    public void deleteAll(){
        log.info("Attempting to delete all the cities");

        this.cityDataService.removeAll();

        log.info("All cities have been deleted");
    }

    @Transactional
    public void deleteById(UUID id){
        log.info("Attempting to delete city with id {}", id);

        this.cityDataService.removeById(id);

        log.info("City with id {} was deleted", id);
    }

}
