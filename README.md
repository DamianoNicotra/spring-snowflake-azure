# spring-snowflake-azure

**Spring Boot API on Azure that logs requests to Snowflake. Infrastructure as Code with Terraform.**

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Azure](https://img.shields.io/badge/Azure-Deployed-0089D6.svg)](https://azure.microsoft.com/)
[![Snowflake](https://img.shields.io/badge/Snowflake-Logs-29B5E8.svg)](https://www.snowflake.com/)
[![Terraform](https://img.shields.io/badge/Terraform-IaC-623CE4.svg)](https://www.terraform.io/)
[![Docker](https://img.shields.io/badge/Docker-Container-2496ED.svg)](https://www.docker.com/)

## 🚀 Overview

This project demonstrates how to build a **complete REST API** on Azure, with automatic logging to **Snowflake** and infrastructure fully managed with **Terraform**.

The API exposes three endpoints:
- `GET /bits/{n}` – Finds positions of 1-bits in binary representation
- `POST /roman` – Converts a number to Roman numerals
- `GET /health` – Health check for the service

**Every request is automatically saved to Snowflake** with:
- Called endpoint
- Request data (JSON)
- Response data (JSON)
- Timestamp and status code

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Cloud Provider | Microsoft Azure |
| Database | Snowflake (Data Warehouse) |
| IaC | Terraform |
| Container | Docker |
| Build Tool | Maven |
| Version Control | Git + GitHub |

## 📦 API Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| GET | `/bits/{n}` | Find 1-bit positions | `/bits/42` → `{"bitPositions":"1,3,5"}` |
| POST | `/roman` | Convert to Roman numerals | `{"number":2024}` → `{"roman":"MMXXIV"}` |
| GET | `/health` | Health check | `{"status":"healthy"}` |

### Example calls

```bash
# Calculate bit positions
curl http://localhost:8080/bits/42

# Convert to Roman numerals
curl -X POST http://localhost:8080/roman \
  -H "Content-Type: application/json" \
  -d '{"number": 2024}'

# Health check
curl http://localhost:8080/health
