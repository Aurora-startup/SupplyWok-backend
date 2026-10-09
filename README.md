# SupplyWok – backend monolítico (referencia)

Este repositorio contiene el monolito modular original de SupplyWok (Spring Boot, DDD con ocho bounded contexts).
A partir del Sprint 1 el backend se migró a una arquitectura de microservicios; el código activo está en:

| Repositorio | Contenido |
|---|---|
| [SupplyWok-api-gateway](https://github.com/1ASI0657-2620-9199-Fundamentos-de-Arqui/SupplyWok-api-gateway) | API Gateway (Spring Cloud Gateway), punto único de entrada |
| [SupplyWok-iam-service](https://github.com/1ASI0657-2620-9199-Fundamentos-de-Arqui/SupplyWok-iam-service) | Identity & Access: usuarios, autenticación JWT y perfiles |
| [SupplyWok-inventory-service](https://github.com/1ASI0657-2620-9199-Fundamentos-de-Arqui/SupplyWok-inventory-service) | Inventory & Supplies: insumos y movimientos de stock |
| [SupplyWok-operations-service](https://github.com/1ASI0657-2620-9199-Fundamentos-de-Arqui/SupplyWok-operations-service) | Restaurant Operations: mesas, comandas, sensores IoT y alertas |
| [SupplyWok-purchasing-service](https://github.com/1ASI0657-2620-9199-Fundamentos-de-Arqui/SupplyWok-purchasing-service) | Purchasing & Analytics: órdenes de compra, proveedores y catálogo |
| [SupplyWok-infrastructure](https://github.com/1ASI0657-2620-9199-Fundamentos-de-Arqui/SupplyWok-infrastructure) | Docker Compose, PostgreSQL y RabbitMQ para el entorno completo |

El monolito se conserva como referencia de la migración y ya incluye las correcciones previas a la separación
(configuración externalizada, historial de inventario, regla de órdenes en estado Pending y seguridad de IAM).
