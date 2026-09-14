# ms-campuslab-report

Microservicio de solo lectura encargado de generar KPIs y reportes operacionales en tiempo casi-real: reservas por hora, tiempo de ciclo, equipos ocupados y recursos más solicitados.

## Responsabilidades

- Calcular y exponer KPIs agregados a partir de los datos de reservas.
- Cachear resultados por un corto período (30 segundos) para simular actualización en tiempo real sin recalcular en cada request.

## Por qué no usa Kafka

Al igual que `audit`, este servicio reemplaza el consumo de streaming por Kafka con lectura directa (solo lectura) sobre la base de datos de `bookings`, usando consultas agregadas de JPA/SQL.

## Stack técnico

- Java 21 + Spring Boot 3.3
- Spring Data JPA + MySQL (solo lectura, `ddl-auto: none`)
- Spring Cache (cache en memoria)
- Spring Security OAuth2 Resource Server (JWT de Azure AD)

## Configuración

| Variable | Descripción | Default |
|---|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexión a la base de `bookings` | `jdbc:mysql://localhost:3306/bookings_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de MySQL | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Password de MySQL | (vacío) |

Puerto por defecto: **8084**

**Importante:** este servicio se conecta a la misma base de datos que `ms-campuslab-bookings` (`bookings_db`), ya que lee directamente de la tabla `bookings`. No gestiona su propio esquema (no tiene Flyway).

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/report/kpis?range=last24h` | KPIs generales del rango solicitado |
| `GET` | `/api/report/top-resources?range=last7d` | Top de recursos más reservados |

Valores válidos de `range`: `last24h`, `last7d`, `last30d`

### Ejemplo: KPIs

```http
GET /api/report/kpis?range=last24h
```

Respuesta:

```json
{
  "totalBookings": 42,
  "activeBookings": 8,
  "completedBookings": 30,
  "avgCycleTimeMinutes": 95.5
}
```

### Ejemplo: recursos más reservados

```http
GET /api/report/top-resources?range=last7d
```

Respuesta:

```json
[
  { "resourceId": "LAB-01", "totalBookings": 15 },
  { "resourceId": "LAB-02", "totalBookings": 9 }
]
```

## Cómo funciona el "tiempo real"

Los resultados se cachean en memoria (`@Cacheable`) por servicio y rango. Un job programado (`@Scheduled(fixedRate = 30000)`) invalida el cache cada 30 segundos, forzando un recálculo fresco desde la base de datos sin sobrecargar el sistema con queries en cada request.

## Cómo correr localmente

```bash
mvn spring-boot:run
```

## Cómo correr con Docker

```bash
docker build -t ms-campuslab-report .
docker run -p 8084:8084 ms-campuslab-report
```

## Seguridad

Todos los endpoints requieren JWT válido de Azure AD en el header `Authorization: Bearer <access_token>`, excepto `/actuator/health`.