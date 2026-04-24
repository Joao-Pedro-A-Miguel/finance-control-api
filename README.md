# 💰 Finance Control API

API REST para controle financeiro pessoal, permitindo gerenciar **usuários, categorias e transações**, com suporte a **relatórios mensais**.

---

## 📌 Sobre o projeto

Esta API foi desenvolvida com o objetivo de simular um sistema real de controle financeiro, aplicando boas práticas de desenvolvimento backend com Spring Boot.

---

## 🚀 Tecnologias

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Docker & Docker Compose
* Maven

---

## ⚙️ Como executar o projeto

### 🔹 Pré-requisitos

* Java 17+
* Docker (opcional)
* Maven (ou usar o wrapper incluído)

---

### 🔹 1. Clonar o repositório

```bash
git clone https://github.com/Joao-Pedro-A-Miguel/finance-control-api.git
cd finance-control-api
```

---

### 🔹 2. Configurar variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
DB_HOST=localhost
DB_NAME=finance_db
DB_USER=root
DB_PASSWORD=123456
```

---

### 🔹 3. Rodar com Docker (recomendado)

```bash
docker-compose up --build
```

---

### 🔹 4. Rodar localmente

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## 📡 Endpoints principais

### 👤 Usuários

* `POST /usuarios` → criar usuário
* `GET /usuarios` → listar usuários

### 🏷️ Categorias

* `POST /categorias` → criar categoria
* `GET /categorias` → listar categorias

### 💰 Transações

* `POST /transacoes` → criar transação
* `GET /transacoes` → listar transações

### 📊 Relatórios

* `GET /transacoes/resumo` → resumo financeiro mensal

---

## 🔐 Segurança

As credenciais do banco são configuradas via variáveis de ambiente (`.env`)
e não são versionadas no repositório.

---

## 📁 Estrutura do projeto

```
src/
 ├── controller
 ├── service
 ├── repository
 ├── entity
 └── config
```

---

## 🧠 Melhorias futuras

* Autenticação com JWT
* Testes automatizados
* Documentação com Swagger
* Deploy em nuvem

---

## 👨‍💻 Autor

João Pedro Miguel
[https://github.com/Joao-Pedro-A-Miguel](https://github.com/Joao-Pedro-A-Miguel)

---

