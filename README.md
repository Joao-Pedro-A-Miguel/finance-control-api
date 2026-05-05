Finance Control API

API REST para controle financeiro pessoal, permitindo o gerenciamento de usuários, categorias e transações com autenticação segura via JWT.

Tecnologias utilizadas
Java 17
Spring Boot
Spring Security
JWT (JSON Web Token)
Spring Data JPA
MySQL
Docker

Funcionalidades
✅ Cadastro e autenticação de usuários
✅ Criação, edição e exclusão de categorias
✅ Registro de transações (receitas e despesas)
✅ Filtro por mês e ano
✅ Resumo financeiro (receitas, despesas e saldo)
✅ Segurança por usuário (cada usuário acessa apenas seus dados)
✅ Criptografia de senha com BCrypt
✅ Proteção de rotas com JWT
🛡️ Segurança

A aplicação utiliza autenticação baseada em JWT.

O usuário faz login e recebe um token
O token deve ser enviado no header das requisições:
Authorization: Bearer SEU_TOKEN_AQUI
 Isolamento de dados

Cada usuário só pode acessar:

Suas próprias transações
Suas próprias categorias
Sua própria conta

Isso é garantido por queries como:

findByIdAndUsuarioEmail
 Estrutura do projeto
api
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

Crie um arquivo .env ou configure:

DB_PORT=3306
DB_NAME=finance
DB_USER=root
DB_PASSWORD=senha

JWT_SECRET=sua_chave_super_secreta_com_32_bytes
application.properties
spring.datasource.url=jdbc:mysql://localhost:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=${JWT_SECRET}
🐳 Rodando com Docker
docker-compose up --build
▶️ Executando o projeto
./mvnw spring-boot:run
🔑 Autenticação
📌 Login
POST /auth/login
📥 Request
{
  "email": "usuario@email.com",
  "senha": "123456"
}
📤 Response
{
  "token": "JWT_TOKEN_AQUI"
}
📌 Endpoints principais
👤 Usuário
Método	Endpoint	Descrição
POST	/usuarios	Criar usuário
GET	/usuarios	Listar usuários
GET	/usuarios/{id}	Buscar por ID
PUT	/usuarios/{id}	Atualizar
DELETE	/usuarios/{id}	Deletar (somente o próprio usuário)
📁 Categoria
Método	Endpoint	Descrição
POST	/categorias	Criar
GET	/categorias	Listar
PUT	/categorias/{id}	Atualizar
DELETE	/categorias/{id}	Deletar
💰 Transações
Método	Endpoint	Descrição
POST	/transacoes	Criar
GET	/transacoes	Listar
GET	/transacoes/{id}	Buscar
PUT	/transacoes/{id}	Atualizar
DELETE	/transacoes/{id}	Deletar
📊 Relatórios
🔹 Resumo mensal
GET /transacoes/resumo?mes=5&ano=2026

📤 Response:

{
  "totalReceitas": 5000,
  "totalDespesas": 2000,
  "saldo": 3000
}
🔹 Filtro por mês/ano
GET /transacoes/filtro?mes=5&ano=2026
⚠️ Observações importantes
Não existe perfil ADMIN neste projeto
Cada usuário acessa apenas seus próprios dados
Exclusões são restritas ao próprio usuário autenticado
O projeto está em evolução e pode receber melhorias futuras
🚧 Melhorias futuras
🔄 Paginação (Pageable)
🧪 Testes unitários
📈 Documentação com Swagger/OpenAPI
🐳 Melhorias no Docker
📊 Dashboard (frontend futuro)
👨‍💻 Autor

Desenvolvido por João Pedro

Considerações finais

Este projeto foi desenvolvido com foco em boas práticas de backend:

Separação de responsabilidades
Segurança com JWT
Validações de negócio
Código limpo e organizado

