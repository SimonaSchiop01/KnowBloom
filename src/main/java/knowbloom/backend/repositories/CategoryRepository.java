package knowbloom.backend.repositories;

import knowbloom.backend.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    Optional<CategoryModel> findByName(String name);

    boolean existsCategoryModelByName(String name);
}
