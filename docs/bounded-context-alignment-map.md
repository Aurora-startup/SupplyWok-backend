# Bounded Context Alignment Map

## Purchasing

| .NET source | Spring target | Rule |
| --- | --- | --- |
| `IPurchaseOrderCommandService` | `PurchaseOrderCommandService` | Remove `I` prefix in Java |
| `PurchaseOrderCommandService` (C# impl) | `PurchaseOrderCommandServiceImpl` | Use `Impl` only for concrete implementations |
| `IPurchaseOrderQueryService` | `PurchaseOrderQueryService` | Remove `I` prefix in Java |
| `IPurchaseOrderRepository` | `PurchaseOrderRepository` | Domain repository port naming |
| EF `PurchaseOrderRepository` | `PurchaseOrderPersistenceRepository` + `PurchaseOrderRepositoryImpl` | Split persistence repository and adapter |
| `PurchaseOrdersController` | `PurchaseOrdersController` | Plural controller for collection endpoints |
| `CreatePurchaseOrderResource` | `CreatePurchaseOrderResource` | Resource naming retained |
| `PurchaseOrderAclResource` | `PurchaseOrderAclResource` | ACL resource retained |

## Inventory

| .NET source | Spring target | Rule |
| --- | --- | --- |
| `Supply` aggregate | `Supply` | Keep domain concept, not the previous `Item/Category` detour |
| `ISupplyCommandService` | `SupplyCommandService` | Remove `I` prefix in Java |
| `ISupplyQueryServices` | `SupplyQueryService` | Normalize contract naming to DAOS singular service style |
| `IStockMovementCommandService` | `StockMovementCommandService` | Remove `I` prefix in Java |
| `IStockMovementQueryService` | `StockMovementQueryService` | Remove `I` prefix in Java |
| `ISupplyRepository` | `SupplyRepository` | Domain repository port naming |
| EF `SupplyRepository` | `SupplyPersistenceRepository` + `StockMovementPersistenceRepository` + `SupplyRepositoryImpl` | Split persistence repositories and adapter |
| `SuppliesController` | `SuppliesController` | Plural controller for collection endpoints |
| `CreateSupplyResource` | `CreateSupplyResource` | Resource naming retained |
| `UpdateSupplyResource` | `UpdateSupplyResource` | Resource naming retained |
| `SupplyResource` | `SupplyResource` | Resource naming retained |
| `IInventoryContextFacade` | `InventoryContextFacade` | ACL facade naming without `I` prefix |

## Notes

- The previous Java `inventory` model based on `Item`, `Category`, and `InventoryActivity` did not match the current .NET source bounded context.
- The corrected Java `inventory` bounded context now follows the `.NET -> Spring` mapping for `Supply` and `StockMovement`.
