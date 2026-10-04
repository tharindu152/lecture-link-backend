# LectureLink microservices

LectureLink is split into independently deployable services:

| Service | Module | Port | Responsibility |
| --- | --- | ---: | --- |
| `institute-service` | `institute-service/` | 8080 | Institutes, programs, subjects and email |
| `lecturer-service` | `lecturer-service/` | 9000 | Lecturers, qualifications, email and AI-match client |
| `ai-match-service` | `ai-match-service/` | 5000 | Python lecturer recommendations |
| `gatewayserver` | `gatewayserver/` | 8072 | Routes API traffic to the domain services |
| `eurekaserver` | `eurekaserver/` | 8070 | Service discovery |
| `configserver` | `configserver/` | 8071 | Serves checked-in service configuration |

## Run the services

From the repository root, build the Java services with Maven:

```powershell
Push-Location institute-service
.\mvnw.cmd -f ..\pom.xml -B verify
Pop-Location
```

Each Java module also includes a Maven wrapper. By default, running a service
directly uses its own in-memory H2 database. For persistent storage, set
`DB_URL`, `DB_USERNAME` and `DB_PASSWORD` to that service's database. The
institute and lecturer services own separate databases; IDs that refer to data
owned by the other service are stored as IDs, not JPA relationships.

Start the full local environment, including two isolated MySQL databases, with:

```powershell
docker compose --profile local -f docker-compose/default/docker-compose.yml up --build
```

The local profile starts Keycloak at `http://localhost:7080`. Its development
admin login defaults to `admin` / `admin`; override these with
`KEYCLOAK_ADMIN_USERNAME` and `KEYCLOAK_ADMIN_PASSWORD` for local use. The
realm, `INSTITUTE` / `LECTURER` roles, and `lecturelink-postman` PKCE client are
imported from `keycloak/lecturelink-realm.json` on first startup.

For QA, start the local Keycloak profile as well. Production uses an externally
managed Keycloak; provide its issuer and a JWK URL reachable from the service
containers:

```powershell
docker compose --profile local -f docker-compose/default/docker-compose.yml -f docker-compose/qa/docker-compose.yml up --build
docker compose -f docker-compose/default/docker-compose.yml -f docker-compose/prod/docker-compose.yml up --build
```

The Compose defaults and embedded Keycloak are for local development only. Set
`KEYCLOAK_ISSUER_URI`, `KEYCLOAK_JWK_SET_URI`, `INSTITUTE_DB_PASSWORD`,
`LECTURER_DB_PASSWORD`, and `DB_ROOT_PASSWORD` through the environment or a
local, untracked `.env` file for deployments. The issuer must exactly match the
`iss` claim in access tokens; the JWK URL must be reachable by the containers.
Configure `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME` and `MAIL_PASSWORD` to
enable email.

Create users in the Keycloak `lecturelink` realm and assign each user the
`INSTITUTE` or `LECTURER` realm role. There are no application registration or
login endpoints: Keycloak issues tokens, and the API accepts bearer tokens.
Institute, program, subject, and institute-email APIs require `INSTITUTE`;
lecturer and qualification APIs require `LECTURER`. AI predictions require
`LECTURER`; model retraining requires `INSTITUTE`. The gateway and each Java
service verify tokens, and the AI service verifies tokens forwarded by the
lecturer service. Health endpoints remain public for readiness probes.

Flyway removes the old local password-hash columns from existing institute and
lecturer databases at startup. Back up the databases before upgrading. JPA
creates/updates the remaining schema by default; set `DDL_AUTO=validate` only
after the schemas have been provisioned.

## Profile image storage

Institute logos and lecturer pictures can be uploaded as multipart form data.
Firebase Storage is opt-in; JSON requests that provide an existing image URL
continue to work without Firebase. To enable uploads, set `FIREBASE_ENABLED=true`
and `FIREBASE_STORAGE_BUCKET` to the bucket name, and provide Google Application
Default Credentials through `GOOGLE_APPLICATION_CREDENTIALS`. The service
account must have permission to create, read, sign URLs for, and delete objects
in that bucket. When running in Compose, mount the credential file read-only
into both Java services and set `GOOGLE_APPLICATION_CREDENTIALS` to its path
inside the containers.

Never add a service-account JSON file or private key to this repository or an
image. Keep credentials outside the repository and rotate them if they have
been exposed. Multipart uploads are available at institute/lecturer create and
update routes using `logo` or `picture` as the file field.

## Gateway routes

- `/api/v1/institutes/**`, `/api/v1/programs/**`, `/api/v1/subjects/**` and
  `/api/v1/email/**` route to `institute-service`.
- `/api/v1/lecturers/**` and `/api/v1/qualifications/**` route to
  `lecturer-service`; lecturer email is also available at
  `/api/v1/lecturers/email/**`.
- The gateway is at `http://localhost:8072`; the services can also be accessed
  directly on their listed ports.

All domain API calls require `Authorization: Bearer <Keycloak access token>`.
Get tokens with the `lecturelink-postman` client using OAuth 2.0 Authorization
Code with PKCE. In Postman use:

- Authorization URL:
  `http://localhost:7080/realms/lecturelink/protocol/openid-connect/auth`
- Access token URL:
  `http://localhost:7080/realms/lecturelink/protocol/openid-connect/token`
- Client ID: `lecturelink-postman`
- Scope: `openid`
- Callback URL: `https://oauth.pstmn.io/v1/callback`

Use the resulting access token for the corresponding role. The AI retraining
endpoint is destructive and should only be used by a trusted institute-role
operator.

## Postman collection

Import [postman/LectureLink.postman_collection.json](./postman/LectureLink.postman_collection.json)
into Postman. The collection defaults to the local gateway and includes
Keycloak discovery, health checks, institute and lecturer APIs, qualifications,
email, and AI matching. Set `instituteToken` and `lecturerToken` collection
variables after obtaining the matching tokens with Authorization Code + PKCE.
Update the collection variables if your services use different ports.
Image-upload examples require Firebase Storage to be enabled; model retraining
replaces the currently loaded AI model.

## AI matching API

The Python service loads the supplied model and encoders at startup. See
[ai-match-service/README.md](./ai-match-service/README.md) for request fields, response shapes,
retraining requirements and standalone run instructions. The lecturer service
proxies prediction and retraining through `/api/v1/lecturers/ai-match` and
`/api/v1/lecturers/ai-match/retrain`.
