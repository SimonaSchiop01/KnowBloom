package knowbloom.backend.services.data;

import jdk.jfr.Category;
import knowbloom.backend.constants.CategoryConstants;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.exceptions.NotFoundException;
import knowbloom.backend.models.CategoryModel;
import knowbloom.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryDataService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryModel save(CategoryModel categoryModel) {
        String name = categoryModel.getName();

        log.info("Attempting to save category with name: {}", name);

        if(this.categoryRepository.existsCategoryModelByName(name)){
            throw new ConflictException(CategoryConstants.ALREADY_EXISTS_BY_NAME);
        }

        CategoryModel savedCategoryModel = this.categoryRepository.save(categoryModel);

        log.info("Successfully saved category with name: {}", name);

        return savedCategoryModel;
    }

    @Transactional(readOnly = true)
    public List<CategoryModel> findAll(){
        log.info("Fetching all the categories");
        return this.categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoryModel findById(UUID id){
        log.info("Fetching the category by id: {}", id);
        return this.categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(CategoryConstants.NOT_FOUND_BY_ID));
    }

    @Transactional
    public void removeAll(){
        log.info("Attempt to remove all the categories");
        this.categoryRepository.deleteAll();
        log.info("All categories have been removed");
    }

    @Transactional
    public void removeById(UUID id){
        log.info("Attempting to remove category with id {}", id);
        this.categoryRepository.deleteById(id);
        log.info("Attempting to remove category with id {}", id);
    }
}
