# Sistema de Venda de Ingressos
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/gustavohazambuja/sistema_venda_ingressos/blob/main/LICENSE)

# Sobre o projeto
Sistema de Venda de Ingressos é uma aplicação backend que consiste em uma API para gerenciamento de eventos e venda de ingressos, permitindo que organizadores cadastrem eventos com múltiplos tipos de ingresso (Pista, VIP, Camarote) e que usuários se inscrevam nesses eventos, garantindo o controle de disponibilidade em tempo real.

Nela podemos cadastrar usuários, criar eventos já com seus lotes de ingresso, realizar inscrições que validam automaticamente disponibilidade de estoque e duplicidade de inscrição por usuário, além de permitir o cancelamento de uma inscrição com devolução automática da unidade ao estoque disponível.

O projeto segue uma arquitetura em camadas (Models, DTOs, Controllers e Services), separando a representação dos dados, a validação de entrada/saída e as regras de negócio de disponibilidade e inscrição.

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- Spring Data JPA
- Bean Validation (Jakarta Validation)
- PostgreSQL
- Maven

## Infraestrutura
- Docker
- Docker Compose

# Como executar o projeto
Pré-requisitos: Docker e Docker Compose instalados.

## Executando com Docker

1. Clone o repositório:
```
git clone https://github.com/gustavohazambuja/sistema_venda_ingressos.git
cd sistema_venda_ingressos
```

2. Suba os containers da aplicação e do banco de dados:
```
docker compose up --build
```

3. A aplicação estará disponível em:
```
http://localhost:8080
```

O banco de dados PostgreSQL é criado automaticamente junto com a aplicação, e os dados são persistidos em um volume Docker, permanecendo salvos mesmo após o encerramento dos containers.

## Executando localmente (sem Docker)

Pré-requisitos: Java 25 e PostgreSQL instalados.

1. Crie o banco de dados no PostgreSQL:
```
psql -h localhost -U postgres -c "CREATE DATABASE venda_ingressos;"
```

2. Execute a aplicação (as tabelas são criadas automaticamente na primeira execução):
```
./mvnw spring-boot:run
```

3. A aplicação estará disponível em:
```
http://localhost:8080
```

# Endpoints Principais

**Usuários**
- Usuários: POST /usuarios
- Usuários: GET /usuarios/{id}
- Usuários: GET /usuarios
- Usuários: PUT /usuarios/{id}
- Usuários: DELETE /usuarios/{id}

**Eventos**
- Eventos: POST /eventos
- Eventos: GET /eventos/{id}
- Eventos: GET /eventos
- Eventos: PUT /eventos/{id}
- Eventos: DELETE /eventos/{id}

**Ingressos**
- Ingressos: POST /eventos/{eventoId}/ingressos
- Ingressos: GET /eventos/{eventoId}/ingressos
- Ingressos: GET /eventos/{eventoId}/ingressos/{id}
- Ingressos: PUT /eventos/{eventoId}/ingressos/{id}
- Ingressos: DELETE /eventos/{eventoId}/ingressos/{id}

**Inscrições**
- Inscrições: POST /inscricoes
- Inscrições: GET /inscricoes/{id}
- Inscrições: GET /inscricoes/usuarios/{usuarioId}
- Inscrições: PATCH /inscricoes/{id}/cancelar

# Autor
Gustavo Henrique Azambuja

https://www.linkedin.com/in/gustavohazambuja/
