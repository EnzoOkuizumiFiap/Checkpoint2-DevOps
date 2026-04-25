# Checkpoint 2 - DevOps

## 📌 Sobre o Projeto

Este projeto faz parte do **Checkpoint 2 de DevOps** da FIAP. O objetivo é demonstrar o uso de **containers Docker** rodando em máquinas virtuais na nuvem — uma na **Oracle Cloud Infrastructure (OCI)** e outra na **Microsoft Azure**.

A aplicação consiste em uma **API REST** de gerenciamento de transações financeiras, desenvolvida com **Spring Boot 3** e conectada a um banco de dados **MySQL 8**, onde ambos os serviços rodam como containers Docker.

---

## 🏗️ Arquitetura

```
┌────────────────────────────────────────────────┐
│               VM (OCI / Azure)                 │
│                                                │
│   ┌──────────────────┐  ┌──────────────────┐  │
│   │  transacoes-api  │  │   mysql-dimdim   │  │
│   │  (Spring Boot)   │◄─┤   (MySQL 8.0)    │  │
│   │    porta 8080    │  │   porta 3306     │  │
│   └──────────────────┘  └──────────────────┘  │
└────────────────────────────────────────────────┘
```

| Serviço | Tecnologia | Porta |
|---|---|---|
| `transacoes-api` | Spring Boot 3 + Java 17 | 8080 |
| `mysql-dimdim` | MySQL 8.0 | 3306 |

---

## 🗂️ Estrutura do Repositório

```
├── docs/                          # Screenshots da aplicação rodando nas VMs
├── transacoes-api/
│   ├── Dockerfile.api             # Imagem da API (multi-stage build)
│   ├── pom.xml
│   └── src/                       # Código-fonte Spring Boot
└── mysql-dimdim/
    ├── Dockerfile.mysql           # Imagem do MySQL com banco e dados iniciais
    └── docker-entrypoint-initdb.d/
        └── init.sql               # Criação da tabela e dados seed
```

---

## 🗃️ Modelo de Dados

**Tabela:** `transacoes`

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | INT (PK, AUTO_INCREMENT) | Identificador único |
| `descricao` | VARCHAR(255) | Descrição da transação |
| `valor` | DECIMAL(10,2) | Valor (positivo = crédito, negativo = débito) |
| `data_transacao` | TIMESTAMP | Data e hora da transação |

---

## 🚀 Como Executar

### Pré-requisitos

- Docker instalado na VM

### 1. Clonar o repositório

```bash
git clone https://github.com/EnzoOkuizumiFiap/Checkpoint2-DevOps.git
cd Checkpoint2-DevOps
```

### 2. Criar a rede Docker

```bash
docker network create dimdim-net
```

### 3. Build e execução do MySQL

```bash
docker build -f mysql-dimdim/Dockerfile.mysql -t mysql-dimdim ./mysql-dimdim

docker run -d \
  --name mysql-dimdim \
  --network dimdim-net \
  -p 3306:3306 \
  mysql-dimdim
```

### 4. Build e execução da API

```bash
docker build -f transacoes-api/Dockerfile.api -t transacoes-api ./transacoes-api

docker run -d \
  --name transacoes-api \
  --network dimdim-net \
  -p 8080:8080 \
  transacoes-api
```

### 5. Verificar containers em execução

```bash
docker ps
```

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/transacoes` | Lista todas as transações |
| GET | `/transacoes/{id}` | Busca transação por ID |
| POST | `/transacoes` | Cria nova transação |
| PUT | `/transacoes/{id}` | Atualiza transação |
| DELETE | `/transacoes/{id}` | Remove transação |

**Exemplo de body (POST/PUT):**
```json
{
  "descricao": "Depósito bancário",
  "valor": 1500.00
}
```

---

## 📸 Screenshots

### Containers rodando na VM

![Rodando os dois containers](docs/Rodando%20os%20dois%20containers.png)

---

### API respondendo na VM OCI

![GET API da VM OCI](docs/Get%20API%20da%20VM%20OCI.png)

---

### Requisição via Insomnia na VM OCI

![GET Insomnia API da VM OCI](docs/Get%20Insomnia%20API%20da%20VM%20OCI.png)

---

### Requisições na VM Azure

![Requisições VM Azure](docs/Requisicoes%20VM%20Azure.png)

---

## 👨‍💻 Integrantes

| Nome | RM |
|---|---|
| Enzo Okuizumi | — |

> **FIAP** — Checkpoint 2 · DevOps Tools & Cloud Computing
