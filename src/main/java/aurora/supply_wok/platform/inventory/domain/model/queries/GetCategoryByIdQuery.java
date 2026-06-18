package aurora.supply_wok.platform.inventory.domain.model.queries;

public record GetCategoryByIdQuery(Long categoryId) {

    public GetCategoryByIdQuery {
        if (categoryId == null || categoryId < 0) {
            throw new IllegalArgumentException("Invalid category id");
        }
    }
}
