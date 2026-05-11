
## Lancer le projet

```bash
./mvnw spring-boot:run
```

Sous Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

## Tests

```bash
./mvnw test
```

Sous Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## URLs utiles

- Swagger UI:http://localhost:8081/swagger-ui/index.html

- Console H2: http://localhost:8081/h2-console/

## Configuration H2

- JDBC URL: `jdbc:h2:mem:vehicledb`
- User: `sa`
- Password: vide

## Comptes préchargés

- `admin` / `admin123` / `ROLE_ADMIN`
- `employee` / `employee123` / `ROLE_EMPLOYEE`
- `client` / `client123` / `ROLE_CLIENT`

## Authentification

1. Appeler `POST /api/auth/login` avec `username` et `password`.
2. Récupérer le JWT dans la réponse.
3. Envoyer le header `Authorization: Bearer <token>` sur les endpoints protégés.

## Endpoints principaux

- `POST /api/auth/login`
- `POST /api/auth/register`
- `GET /api/agencies`
- `GET /api/agencies/{id}`
- `POST /api/agencies`
- `PUT /api/agencies/{id}`
- `DELETE /api/agencies/{id}`
- `GET /api/vehicles`
- `GET /api/vehicles/{id}`
- `GET /api/vehicles/search?status=Disponible&type=Car`
- `GET /api/agencies/{agencyId}/vehicles`
- `POST /api/vehicles`
- `PUT /api/vehicles/{id}`
- `DELETE /api/vehicles/{id}`
- `GET /api/rentals`
- `GET /api/rentals/{id}`
- `GET /api/rentals/vehicles/{vehicleId}`
- `GET /api/rentals/history?vehicleId=&page=&size=`
- `POST /api/rentals`
- `PUT /api/rentals/{id}`
- `DELETE /api/rentals/{id}`

## Notes techniques

- Les entités ne sont jamais exposées directement par les controllers.
- Les véhicules utilisent un identifiant UUID `String`.
- Le statut du véhicule passe automatiquement à `LOUE` lors de la création d'une location.
- La valeur `totalPrice` est calculée automatiquement à partir de la durée et du prix journalier.