# Projeto Backend – Gerenciamento de Artistas e Álbuns (ArtistHub)

PROCESSO SELETIVO CONJUNTO Nº 001/2026/SEPLAG/
SEFAZ/SEDUC/SESP/PJC/PMMT/CBMMT/DETRAN/POLITEC/SEJUS/
SEMA/SEAF/SINFRA/SECITECI/PGE/MTPREV

Jonathan Santana Silva - 44120554856 - Engenheiro de Software – Backend Sênior

- **Back-end**: Java 21, Spring Boot 4.0.1 com banco de dados PostgreSQL 15, Flyway para migrações, e Spring Security para autenticação, autorização e controle de acesso  tudo Dockerizado. Upload de imagens de álbuns é gerenciado pelo MinIO, retornando URLs assinadas com acesso temporário.RESTful versionados em cima do MVC, com camdas de Controller, Service, Repository e Model (DTOs para transferência de dados).JWT para autenticação, Rate Limiting para proteção contra abuso, CORS configurado para permitir integração com Front-end. PostgreSQL para persistência relacional. Flyway garante o versionamento do schema.

Justificativa: Eu escolhi uma API Getway MVC para centralizar as requisições dos clientes, consumo de serviços externos e por requisitos não funcionais predefinidos pelo desafio.

## Como rodar o projeto

### requisitos
- Docker e Docker Compose instalados.

### Passos

1. Clone o repositório. E na raiz do projeto, execute:

```bash
docker-compose up --build -d
```

2. A aplicação estará disponível em:
   - **Endereço da API**: `http://localhost:8080`

## Testes

Para rodar os testes, rode o comando:

```bash
./gradlew test
```

se quiser ver os relatórios eles ficam em: `build/reports/tests/test/index.html`.

## Documentação da api (swagger) 

Acesse `http://localhost:8080/swagger-ui.html` 