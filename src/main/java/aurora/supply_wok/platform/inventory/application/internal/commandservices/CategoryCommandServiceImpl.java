package aurora.supply_wok.platform.inventory.application.internal.commandservices;

import aurora.supply_wok.platform.inventory.application.commandservices.CategoryCommandService;
import aurora.supply_wok.platform.inventory.domain.model.aggregates.Category;
import aurora.supply_wok.platform.inventory.domain.model.commands.CreateCategoryCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteCategoryCommand;
import aurora.supply_wok.platform.inventory.domain.model.commands.UpdateCategoryCommand;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.CategoryPersistenceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryPersistenceRepository categoryRepository;

    public CategoryCommandServiceImpl(CategoryPersistenceRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long handle(CreateCategoryCommand command) {

        var name = command.name();
        if (this.categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category with name " + name + " already exists");
        }

        var category = new Category(command.name(), command.description());
        try {
            this.categoryRepository.save(category);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error while saving category: " + e.getMessage());
        }

        return category.getId();
    }

    @Override
    public Optional<Category> handle(UpdateCategoryCommand command) {

        var categoryId = command.categoryId();
        if (!this.categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist");
        }

        var categoryToUpdate = this.categoryRepository.findById(categoryId).get();
        categoryToUpdate.updateInformation(command.name(), command.description());

        try{
            var updateCategory = this.categoryRepository.save(categoryToUpdate);
            return Optional.of(updateCategory);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating category: " + e.getMessage());
        }

    }

    @Override
    public void handle(DeleteCategoryCommand command) {

        var categoryId = command.categoryId();
        if (!this.categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist");
        }

        try {
            this.categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting category: " + e.getMessage());
        }

    }
}
