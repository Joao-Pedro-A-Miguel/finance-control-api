#  API de Controle Financeiro

API REST desenvolvida com **Spring Boot** para gerenciamento de finanças pessoais.

A aplicação permite o controle completo de usuários, categorias e transações (receitas e despesas), com autenticação segura via JWT e isolamento de dados por usuário.

---

##  Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* MySQL
* Docker

---

##  Funcionalidades

* ✅ Cadastro e autenticação de usuários
* ✅ CRUD de categorias
* ✅ CRUD de transações (receitas e despesas)
* ✅ Filtro por mês e ano
* ✅ Resumo financeiro mensal
* ✅ Segurança por usuário (cada usuário acessa apenas seus dados)
* ✅ Criptografia de senha com BCrypt
* ✅ Proteção de rotas com JWT

---

##  Segurança

A aplicação utiliza autenticação baseada em **JWT**.

Após o login, o usuário recebe um token que deve ser enviado em todas as requisições protegidas:

Authorization: Bearer SEU_TOKEN_AQUI

---

##  Isolamento de dados

Cada usuário só pode acessar:

* Suas próprias transações
* Suas próprias categorias
* Sua própria conta

Isso é garantido no backend com validações por usuário autenticado.

---

##  Configuração do ambiente

### Variáveis de ambiente

```env
DB_PORT=3306
DB_NAME=finance
DB_USER=root
DB_PASSWORD=senha

JWT_SECRET=sua_chave_super_secreta_com_32_bytes
```

---

## 🐳 Rodando com Docker

```bash
docker-compose up --build
```

---

##  Rodando localmente

```bash
./mvnw spring-boot:run
```

A API estará disponível em:

http://localhost:8080

---

##  Autenticação

### Login

POST /auth/login

#### Request

```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

#### Response

```json
{
  "token": "JWT_TOKEN_AQUI"
}
```

---

##  Relatórios

### Resumo mensal

GET /transacoes/resumo?mes=5&ano=2025

#### Response

```json
{
  "totalReceitas": 5000,
  "totalDespesas": 2000,
  "saldo": 3000
}
```

###  Descrição dos campos

* **totalReceitas** → soma de todas as entradas do mês
* **totalDespesas** → soma de todas as saídas do mês
* **saldo** → diferença entre receitas e despesas

---

##  Endpoints principais

###  Usuários

| Método | Endpoint       | Descrição                   |
| ------ | -------------- | --------------------------- |
| POST   | /usuarios      | Criar usuário               |
| GET    | /usuarios      | Listar usuários             |
| GET    | /usuarios/{id} | Buscar por ID               |
| PUT    | /usuarios/{id} | Atualizar usuário           |
| DELETE | /usuarios/{id} | Deletar (somente o próprio) |

---

###  Categorias

| Método | Endpoint         | Descrição |
| ------ | ---------------- | --------- |
| POST   | /categorias      | Criar     |
| GET    | /categorias      | Listar    |
| PUT    | /categorias/{id} | Atualizar |
| DELETE | /categorias/{id} | Deletar   |

---

###  Transações

| Método | Endpoint         | Descrição     |
| ------ | ---------------- | ------------- |
| POST   | /transacoes      | Criar         |
| GET    | /transacoes      | Listar        |
| GET    | /transacoes/{id} | Buscar por ID |
| PUT    | /transacoes/{id} | Atualizar     |
| DELETE | /transacoes/{id} | Deletar       |

---

##  Regras importantes

* ❌ Não existe perfil ADMIN
* 🔐 Cada usuário acessa apenas seus próprios dados
* 🗑️ Exclusões são restritas ao usuário autenticado
* 🚧 Projeto em evolução

---

##  Melhorias futuras

* Implementar paginação com Pageable
* Adicionar testes unitários (JUnit)
* Documentação com Swagger/OpenAPI
* Criação de dashboard frontend

---

##  Estrutura do projeto

```bash
src/main/java/com/seuprojeto/api
│
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

---

##  Autor

João Pedro

---

##  Considerações finais

Projeto desenvolvido com foco em:

* Boas práticas de backend
* Segurança com JWT
* Organização de código
* Regras de negócio bem definidas
