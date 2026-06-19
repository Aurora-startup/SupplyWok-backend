package aurora.supply_wok.platform.shared.domain.model.aggregates;

/**
 * Minimal base class for domain aggregate roots.
 *
 * <p>This class intentionally keeps persistence concerns out of the domain model.
 * Domain events are intentionally omitted in this project iteration.</p>
 *
 * @param <T> the concrete aggregate root type
 */
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>> {
}
