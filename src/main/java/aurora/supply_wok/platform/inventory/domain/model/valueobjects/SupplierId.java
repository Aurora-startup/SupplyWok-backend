package aurora.supply_wok.platform.inventory.domain.model.valueobjects;

public record SupplierId(Long supplierId) {

    public SupplierId{

        if(supplierId == null || supplierId < 0)
        {
            throw new IllegalArgumentException("SupplierId cannot be null or less than 0");
        }
    }


}
