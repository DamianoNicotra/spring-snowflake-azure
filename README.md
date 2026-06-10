# spring-snowflake-azure

**Spring Boot API su Azure che scrive log su Snowflake. Infrastruttura as Code con Terraform.**

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Azure](https://img.shields.io/badge/Azure-Deployed-0089D6.svg)](https://azure.microsoft.com/)
[![Snowflake](https://img.shields.io/badge/Snowflake-Logs-29B5E8.svg)](https://www.snowflake.com/)
[![Terraform](https://img.shields.io/badge/Terraform-IaC-623CE4.svg)](https://www.terraform.io/)
[![Docker](https://img.shields.io/badge/Docker-Container-2496ED.svg)](https://www.docker.com/)

## 🚀 Panoramica

Questo progetto dimostra come costruire un'**API REST completa** su Azure, con logging automatico su **Snowflake** e infrastruttura interamente gestita con **Terraform**.

L'API espone tre endpoint:
- `GET /bits/{n}` – Calcola le posizioni dei bit a 1 nella rappresentazione binaria
- `POST /roman` – Converte un numero in numeri romani
- `GET /health` – Health check per il servizio

**Ogni richiesta viene automaticamente salvata su Snowflake** con:
- Endpoint chiamato
- Dati della richiesta (JSON)
- Risposta restituita (JSON)
- Timestamp e status code

## 🛠️ Tech Stack

| Layer | Tecnologia |
|-------|------------|
| Linguaggio | Java 17 |
| Framework | Spring Boot 3.2 |
| Cloud Provider | Microsoft Azure |
| Database | Snowflake (Data Warehouse) |
| IaC | Terraform |
| Container | Docker |
| Build Tool | Maven |
| Controllo versioni | Git + GitHub |

## 📦 Endpoints API

| Metodo | Endpoint | Descrizione | Esempio |
|--------|----------|-------------|---------|
| GET | `/bits/{n}` | Posizioni dei bit a 1 | `/bits/42` → `{"bitPositions":"1,3,5"}` |
| POST | `/roman` | Conversione in numeri romani | `{"number":2024}` → `{"roman":"MMXXIV"}` |
| GET | `/health` | Health check | `{"status":"healthy"}` |

### Esempio di chiamata

```bash
# Calcolo bit positions
curl http://localhost:8080/bits/42

# Conversione in numeri romani
curl -X POST http://localhost:8080/roman \
  -H "Content-Type: application/json" \
  -d '{"number": 2024}'

# Health check
curl http://localhost:8080/health
