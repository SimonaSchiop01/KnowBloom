package knowbloom.backend.services;

import knowbloom.backend.dtos.requests.CategoryCreateRequestDto;
import knowbloom.backend.dtos.requests.SubjectCreateRequestDto;
import knowbloom.backend.dtos.responses.CategoryResponseDto;
import knowbloom.backend.mappers.CategoryMapper;
import knowbloom.backend.mappers.SubjectMapper;
import knowbloom.backend.models.CategoryModel;
import knowbloom.backend.models.SubjectModel;
import knowbloom.backend.services.data.CategoryDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final SubjectMapper subjectMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryDataService categoryDataService;

    @Transactional
    public CategoryResponseDto create(CategoryCreateRequestDto categoryDto) {
        String name = categoryDto.getName();
        List<SubjectCreateRequestDto> subjectsDtos = categoryDto.getSubjects();

        log.info("Attempting to create category with name: {}", name);

        CategoryModel categoryModel = this.categoryMapper.toModel(categoryDto);

        for(SubjectCreateRequestDto subjectDto: subjectsDtos) {
            SubjectModel subjectModel = this.subjectMapper.toModel(subjectDto);
            categoryModel.addSubject(subjectModel);
        }

//        Set<SubjectModel> subjects = new HashSet<>();
//
//       for(SubjectCreateRequestDto subjectCreateRequestDto: requestDto.getSubjects()) {
//           SubjectModel subjectModel = this.subjectMapper.toModel(subjectCreateRequestDto);
//           subjectModel.setCategory(categoryModel);
//
//           subjects.add(subjectModel);
//       }
//
//       categoryModel.setSubjects(subjects);

//       CategoryModel savedCategoryModel = this.categoryRepository.save(categoryModel);

        CategoryModel savedCategoryModel = this.categoryDataService.save(categoryModel);

        log.info("Category with name {} was created successfully", name);

        return this.categoryMapper.toDto(savedCategoryModel);

        //       CategoryResponseDto categoryResponseDto = this.categoryMapper.toDto(savedCategoryModel);
//
//        Set<SubjectResponseDto> subjectResponseDtos = new HashSet<>();
//
//        for(SubjectModel subjectModel: savedCategoryModel.getSubjects()){
//            SubjectResponseDto subjectResponseDto = this.subjectMapper.toDto(subjectModel);
//
//            subjectResponseDtos.add(subjectResponseDto);
//        }
//
//        categoryResponseDto.setSubjectResponseDtos(subjectResponseDtos);
//
//        return categoryResponseDto
 }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAll(){
        log.info("Getting all the categories");
        return this.categoryDataService
            .findAll()
            .stream()
            .map(this.categoryMapper::toDto)
            .toList();

//        log.info("Getting all the categories");
//
//        List<CategoryModel> categoryModels = this.categoryDataService.findAll();
//
//        List<CategoryResponseDto> responseDtos = new ArrayList<>();
//
//        for (CategoryModel model : categoryModels) {
//            CategoryResponseDto dto = this.categoryMapper.toDto(model);
//            responseDtos.add(dto);
//        }
//
//        return responseDtos;
    }


    @Transactional(readOnly = true)
    public CategoryResponseDto getById(UUID id) {
        log.info("Getting category with id {}", id);
        CategoryModel categoryModel = this.categoryDataService.findById(id);
        return this.categoryMapper.toDto(categoryModel);
    }

    //    public CategoryResponseDto getById(UUID id){
//        Optional<CategoryModel> optionalCategoryModel = this.categoryDataService.getById(id);
//
//        if (optionalCategoryModel.isEmpty()) {
//            return null;
//        }
//
//        CategoryModel categoryModel = optionalCategoryModel.get();
//
//        CategoryResponseDto categoryResponseDto = this.categoryMapper.toDto(categoryModel);
//
//        return categoryResponseDto;
//    }

    @Transactional
    public void deleteAll(){
        log.info("Attempting to delete all the categories");
        this.categoryDataService.removeAll();
        log.info("All categories have been deleted");
    }

    @Transactional
    public void deleteById(UUID id){
        log.info("Attempting to delete category with id {}", id);
        this.categoryDataService.removeById(id);
        log.info("Category with id {} was deleted", id);
    }
}
