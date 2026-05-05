# 📊 Finance Control API

API REST para controle financeiro pessoal, permitindo o gerenciamento de usuários, categorias e transações com autenticação segura via JWT.

---

## 🚀 Tecnologias utilizadas

- Java 17  
- Spring Boot  
- Spring Security  
- JWT (JSON Web Token)  
- Spring Data JPA  
- MySQL  
- Docker  

---

## 🔐 Funcionalidades

- ✅ Cadastro e autenticação de usuários  
- ✅ CRUD de categorias  
- ✅ CRUD de transações (receitas e despesas)  
- ✅ Filtro por mês e ano  
- ✅ Resumo financeiro mensal  
- ✅ Segurança por usuário (cada usuário acessa apenas seus dados)  
- ✅ Criptografia de senha com BCrypt  
- ✅ Proteção de rotas com JWT  

---

## 🛡️ Segurança

A aplicação utiliza autenticação baseada em JWT.

Após o login, o usuário recebe um token que deve ser enviado em todas as requisições protegidas:


### 🔒 Isolamento de dados

Cada usuário só pode acessar:

- Suas próprias transações  
- Suas próprias categorias  
- Sua própria conta  

Isso é garantido no backend por validações como:


Estrutura do projeto
```
src/main/java/com/pedro/finance/api
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
└── service
````
Configuração do ambiente
🔑 Variáveis de ambiente

Configure as variáveis:
