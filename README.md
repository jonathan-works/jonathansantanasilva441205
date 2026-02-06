# ArtistHub - Gerenciamento de Artistas e Álbuns

**Processo Seletivo Conjunto Nº 001/2026/SEPLAG e Órgãos**
**Candidato:** Jonathan Santana Silva (CPF: 44120554856)
**Cargo:** Engenheiro de Software – Fullstack Sênior

---

## Sobre o Projeto

Sistema fullstack para gestão de artistas e álbuns, feito com **Java (Spring Boot)** no backend e **Angular** no frontend. O ambiente de desenvolvimento está configurado com Docker para facilitar a execução.

### Tecnologias

*   **Backend:** Java 21, Spring Boot 4.0, PostgreSQL 15, Flyway, Spring Security (JWT), MinIO (S3) e WebSocket.
*   **Frontend:** Angular 19, TypeScript, TailwindCSS e Nginx.

## Como Rodar

Para subir o ambiente completo (Front, Back, Banco e MinIO), basta usar o Docker Compose na raiz do projeto:

```bash
docker-compose up -d --build
```

Depois de subir, acesse:

Aplicação:** [http://localhost](http://localhost)
API:** [http://localhost:8080](http://localhost:8080)
Swagger (Docs):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
MinIO Console:** [http://localhost:9001](http://localhost:9001) (User/Pass: `minioadmin`)

## Testes

Caso queira rodar os testes localmente sem o Docker:

Backend:** `./gradlew test` (dentro da pasta `backend`)
Frontend:** `npm test` (dentro da pasta `frontend`)
