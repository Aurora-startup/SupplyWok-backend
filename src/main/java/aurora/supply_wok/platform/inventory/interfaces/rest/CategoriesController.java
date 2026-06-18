package aurora.supply_wok.platform.inventory.interfaces.rest;

import aurora.supply_wok.platform.inventory.application.commandservices.CategoryCommandService;
import aurora.supply_wok.platform.inventory.application.queryservices.CategoryQueryService;
import aurora.supply_wok.platform.inventory.domain.model.commands.DeleteCategoryCommand;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetAllCategoriesQuery;
import aurora.supply_wok.platform.inventory.domain.model.queries.GetCategoryByIdQuery;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CategoryResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.resources.CreateCategoryResource;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.CategoryResourceFromEntityAssembler;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.CreateCategoryCommandFromResourceAssembler;
import aurora.supply_wok.platform.inventory.interfaces.rest.transform.UpdateCategoryCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Categories", description = "Category Management Endpoints")
public class CategoriesController {
    private final CategoryQueryService categoryQueryService;
    private final CategoryCommandService categoryCommandService;

    public CategoriesController(CategoryQueryService categoryQueryService, CategoryCommandService categoryCommandService) {
        this.categoryQueryService = categoryQueryService;
        this.categoryCommandService = categoryCommandService;
    }

    @PostMapping
    public ResponseEntity<CategoryResource> createCategory(@RequestBody CreateCategoryResource resource) {
        var command = CreateCategoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var categoryId = this.categoryCommandService.handle(command);

        if (categoryId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getCategoryByIdQuery = new GetCategoryByIdQuery(categoryId);
        var optionalCategory = this.categoryQueryService.handle(getCategoryByIdQuery);

        var categoryResource = CategoryResourceFromEntityAssembler.toResourceFromEntity(optionalCategory.get());
        return new ResponseEntity<>(categoryResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResource>> getAllCategories() {

        var getAllCategoriesQuery = new GetAllCategoriesQuery();
        var categories = this.categoryQueryService.handle(getAllCategoriesQuery);
        var categoryResources = categories.stream()
                .map(CategoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(categoryResources);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResource> getCategoryById(@PathVariable Long categoryId) {

        var getCategoryByIdQuery = new GetCategoryByIdQuery(categoryId);
        var optionalCategory = this.categoryQueryService.handle(getCategoryByIdQuery);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var categoryResource = CategoryResourceFromEntityAssembler.toResourceFromEntity(optionalCategory.get());
        return ResponseEntity.ok(categoryResource);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResource> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryResource resource) {

        var updateCategoryCommand = UpdateCategoryCommandFromResourceAssembler.toCommandFromResource(categoryId, resource);
        var optionalCategory = this.categoryCommandService.handle(updateCategoryCommand);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var categoryResource = CategoryResourceFromEntityAssembler.toResourceFromEntity(optionalCategory.get());
        return ResponseEntity.ok(categoryResource);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        var deleteCategoryCommand = new DeleteCategoryCommand(categoryId);
        this.categoryCommandService.handle(deleteCategoryCommand);
        return ResponseEntity.noContent().build();
    }

}
