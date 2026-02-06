# Projeto Fullstack – Gerenciamento de Artistas e Álbuns (ArtistHub)

PROCESSO SELETIVO CONJUNTO Nº 001/2026/SEPLAG/
SEFAZ/SEDUC/SESP/PJC/PMMT/CBMMT/DETRAN/POLITEC/SEJUS/
SEMA/SEAF/SINFRA/SECITECI/PGE/MTPREV

Jonathan Santana Silva - 44120554856 - Engenheiro de Software – Fullstack Sênior

- **Front-end**: Angular 19, TypeScript 5.7, TailwindCSS para estilização e responsividade. O projeto utiliza Standalone Components, Reactive Forms para manipulação de formulários e RxJS para programação reativa. A comunicação com o backend é feita via HTTP Client e WebSocket (STOMP/SockJS) para atualizações em tempo real. A autenticação JWT é gerenciada via Interceptor, garantindo segurança nas requisições. O projeto é dockerizado e servido via Nginx.

Justificativa: Escolhi o Angular por sua robustez e arquitetura bem definida, ideal para aplicações corporativas escaláveis. A combinação com TailwindCSS permite um desenvolvimento ágil de interfaces modernas e responsivas. A arquitetura baseada em serviços e componentes garante a separação de responsabilidades e facilitando a manutenção.

## Como rodar o projeto

### Requisitos
- Docker e Docker Compose instalados.

### Passos

1. Clone o repositório e, na raiz do projeto (pasta `frontend`), execute:

```bash
docker-compose up --build -d
```

2. A aplicação estará disponível em:
   - **Endereço da Aplicação**: `http://localhost:80`

## Testes

Para rodar os testes unitários (requer Node.js instalado localmente se não usar Docker):

```bash
npm test
```
Ou via Docker (se configurado para tal, ou executando dentro do container):
```bash
docker exec -it artisthub-frontend npm test
```
