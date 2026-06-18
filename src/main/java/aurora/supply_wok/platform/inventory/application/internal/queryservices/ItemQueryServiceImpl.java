package aurora.supply_wok.platform.inventory.application.internal.queryservices;

import aurora.supply_wok.platform.inventory.domain.model.aggregates.Item;
import aurora.supply_wok.platform.inventory.domain.model.entities.InventoryActivity;
import aurora.supply_wok.platform.inventory.domain.model.queries.*;
import aurora.supply_wok.platform.inventory.domain.model.entities.StockMovement;
import aurora.supply_wok.platform.inventory.domain.services.ItemQueryService;
import aurora.supply_wok.platform.inventory.infrastructure.persistence.jpa.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;

    public ItemQueryServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> handle(GetAllItemsQuery query) {
        return this.itemRepository.findAll();
    }

    @Override
    public List<Item> handle(GetAllItemsByFiltersQuery query) {
        return this.itemRepository.findAllByFilters(query.search(), query.supplierId(), query.categoryId(), query.stockStatus());
    }

    @Override
    public Optional<Item> handle(GetItemByIdQuery query) {
        return this.itemRepository.findById(query.itemId());
    }

}
