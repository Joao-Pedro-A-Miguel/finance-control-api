
API REST para controle financeiro pessoal, permitindo o gerenciamento de usuários, categorias e transações com autenticação segura via JWT.

---

##  Tecnologias utilizadas

- Java 17 
- Spring Boot 
- Spring Security 
- JWT (JSON Web Token) 
- Spring Data JPA 
- MySQL 
- Docker 

---

##  Funcionalidades

- ✅ Cadastro e autenticação de usuários 
- ✅ CRUD de categorias 
- ✅ CRUD de transações (receitas e despesas) 
- ✅ Filtro por mês e ano 
- ✅ Resumo financeiro mensal 
- ✅ Segurança por usuário (cada usuário acessa apenas seus dados) 
- ✅ Criptografia de senha com BCrypt 
- ✅ Proteção de rotas com JWT 

---

##  Segurança

A aplicação utiliza autenticação baseada em JWT.

Após o login, o usuário recebe um token que deve ser enviado em todas as requisições protegidas:
Authorization: Bearer SEU_TOKEN_AQUI

###  Isolamento de dados

Cada usuário só pode acessar:

- Suas próprias transações 
- Suas próprias categorias 
- Sua própria conta 

Isso é garantido no backend por validações como:

```java
findByIdAndUsuarioEmail

 Estrutura do projeto
src/main/java/com/pedro/finance/api
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service

 Configuração do ambiente
 Variáveis de ambiente
Configure as variáveis:
DB_PORT=3306
DB_NAME=finance
DB_USER=root
DB_PASSWORD=senha

JWT_SECRET=sua_chave_super_secreta_com_32_bytes
```
🐳 Rodando com Docker
```
docker-compose up --build
````
 Executando o projeto
 ```
./mvnw spring-boot:run
```
 Autenticação
 Login
 ```
POST /auth/login
 Request
{
 "email": "usuario@email.com",
 "senha": "123456"
}
 Response
{
 "token": "JWT_TOKEN_AQUI"
}
```
 Endpoints principais
👤 Usuário
Método
Endpoint
Descrição
POST
/usuarios
Criar usuário
GET
/usuarios
Listar usuários
GET
/usuarios/{id}
Buscar por ID
PUT
/usuarios/{id}
Atualizar
DELETE
/usuarios/{id}
Deletar (somente o próprio usuário)


 Categoria
Método
Endpoint
Descrição
POST
/categorias
Criar
GET
/categorias
Listar
PUT
/categorias/{id}
Atualizar
DELETE
/categorias/{id}
Deletar

 Transações
Método
Endpoint
Descrição
POST
/transacoes
Criar
GET
/transacoes
Listar
GET
/transacoes/{id}
Buscar
PUT
/transacoes/{id}
Atualizar
DELETE
/transacoes/{id}
Deletar


 Relatórios
🔹 Resumo mensal
GET /transacoes/resumo?mes=5&ano=2026
Response:
```
{
 "totalReceitas": 5000,
 "totalDespesas": 2000,
 "saldo": 3000
}
```

🔹 Filtro por mês/ano
GET /transacoes/filtro?mes=5&ano=2026

 Observações importantes
• Não existe perfil ADMIN neste projeto
• Cada usuário acessa apenas seus próprios dados
• Exclusões são restritas ao usuário autenticado
• Projeto em evolução (pode receber melhorias futuras)

 Melhorias futuras
 • Paginação (Pageable)
 • Testes unitários
 • Documentação com Swagger
 • Dashboard (frontend futuro)

 Autor
João Pedro

 Considerações finais
• Projeto desenvolvido com foco em:
• Boas práticas de backend
• Segurança com JWT
• Organização de código
• Regras de negócio bem definidas

