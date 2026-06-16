# HOSPEDAR - Sistema de Check-in de Hotel

Projeto acadêmico desenvolvido para a disciplina **Aplicações para Internet**.

O sistema possui uma interface web para gerenciamento de hotel e uma API RESTful desenvolvida em Java para realizar o CRUD de hóspedes. A API utiliza autenticação JWT, separação em camadas e banco de dados MySQL.

## Objetivo do projeto

Permitir o gerenciamento de hóspedes, quartos, reservas e check-ins de um hotel.

A complementação solicitada no trabalho foi desenvolvida no mesmo projeto, mantendo a organização em camadas:

- **Controller:** recebe as requisições HTTP e retorna as respostas.
- **Service:** contém as regras de negócio e validações.
- **DAO:** realiza o acesso ao banco de dados.
- **Model:** representa as entidades do sistema.
- **Filter:** valida o token JWT antes de liberar as rotas protegidas da API.
- **Util:** reúne classes auxiliares, como conexão com o banco, conversão para JSON e geração de tokens.

## Tecnologias utilizadas

- Java 11
- Jakarta Servlet 5.0
- JSP e JSTL
- MySQL 8
- JDBC
- Maven
- Tomcat 10
- Gson 2.14.0 para conversão de objetos em JSON
- JJWT 0.13.0 para geração e validação de tokens JWT
- BCrypt para armazenamento seguro das senhas

## Funcionalidades

### Aplicação web

- Cadastro, edição, listagem e exclusão de hóspedes.
- Cadastro, edição, listagem e exclusão de quartos.
- Criação, edição, confirmação, cancelamento e exclusão de reservas.
- Realização de check-in, checkout e cancelamento de check-in.
- Dashboard com quantidade de hóspedes, quartos disponíveis, reservas e check-ins ativos.

### API RESTful

- Login com geração de token JWT.
- Registro de usuário com senha protegida por BCrypt.
- CRUD completo de hóspedes em JSON.
- Proteção das rotas da API pelo cabeçalho `Authorization: Bearer SEU_TOKEN`.
- Respostas com códigos HTTP adequados, como `200`, `201`, `204`, `400`, `401`, `404` e `409`.

## Organização do projeto

```text
src/main/java/com/hotel/
├── api/
│   └── HospedeApiController.java
├── controller/
│   ├── AuthController.java
│   ├── CheckInController.java
│   ├── HomeController.java
│   ├── HospedeController.java
│   ├── QuartoController.java
│   └── ReservaController.java
├── dao/
│   ├── CheckInDAO.java
│   ├── HospedeDAO.java
│   ├── MysqlDAO.java
│   ├── QuartoDAO.java
│   ├── ReservaDAO.java
│   └── UsuarioDAO.java
├── filter/
│   └── JwtFilter.java
├── model/
│   ├── CheckIn.java
│   ├── Hospede.java
│   ├── Mapeavel.java
│   ├── Quarto.java
│   ├── Reserva.java
│   └── Usuario.java
├── service/
│   ├── AuthService.java
│   ├── CheckInService.java
│   ├── HospedeService.java
│   ├── QuartoService.java
│   └── ReservaService.java
└── util/
    ├── ConnectionFactory.java
    ├── JsonUtil.java
    └── JwtUtil.java
```

## Como executar

### 1. Criar o banco de dados

Abra o MySQL Workbench ou outro cliente MySQL e execute o arquivo:

```text
sql/hotel_checkin.sql
```

O script recria o banco `hotel_checkin`, cria as tabelas e adiciona um usuário inicial para testes. Como ele remove a versão anterior do banco, utilize-o em um ambiente de estudo ou faça backup antes de executá-lo:

```text
E-mail: admin@hotel.com
Senha: admin123
```

### 2. Conferir a conexão com o MySQL

As credenciais estão no arquivo:

```text
src/main/java/com/hotel/util/ConnectionFactory.java
```

A configuração padrão utiliza:

```text
Banco: hotel_checkin
Usuário: root
Senha: vazia
```

Caso o MySQL do computador utilize outra senha, altere o valor de `PASSWORD`.

### 3. Gerar o arquivo WAR

Na pasta principal do projeto, execute:

```bash
mvn clean package
```

O Maven irá gerar:

```text
target/hotel-checkin.war
```

### 4. Publicar no Tomcat

Copie o arquivo WAR para a pasta `webapps` do Tomcat 10 e inicie o servidor.

Exemplo de endereço local:

```text
http://localhost:8080/hotel-checkin/
```

## Autenticação JWT

Para utilizar as rotas protegidas, primeiro realize o login:

### Login

```http
POST /hotel-checkin/api/auth/login
Content-Type: application/json
```

```json
{
  "email": "admin@hotel.com",
  "senha": "admin123"
}
```

Resposta esperada:

```json
{
  "mensagem": "Login realizado com sucesso.",
  "token": "SEU_TOKEN_JWT",
  "tipo": "Bearer"
}
```

Depois, envie o token nas demais requisições:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

Quando o token não é enviado ou está inválido, a API retorna:

```json
{
  "erro": "Acesso não autorizado"
}
```

## Endpoints da API

### Autenticação

| Método | Endpoint | Protegido | Descrição |
|---|---|---:|---|
| `POST` | `/api/auth/login` | Não | Valida e-mail e senha e retorna um token JWT. |
| `POST` | `/api/auth/registrar` | Não | Registra um usuário com senha protegida por BCrypt. |

### Hóspedes

| Método | Endpoint | Protegido | Descrição |
|---|---|---:|---|
| `GET` | `/api/hospedes` | Sim | Lista todos os hóspedes. |
| `GET` | `/api/hospedes?busca=João` | Sim | Pesquisa hóspedes pelo nome. |
| `GET` | `/api/hospedes/{id}` | Sim | Busca um hóspede pelo ID. |
| `POST` | `/api/hospedes` | Sim | Cadastra um novo hóspede. |
| `PUT` | `/api/hospedes/{id}` | Sim | Atualiza todos os dados de um hóspede. |
| `DELETE` | `/api/hospedes/{id}` | Sim | Exclui um hóspede. |

## Exemplos do CRUD de hóspedes

### Cadastrar hóspede

```http
POST /hotel-checkin/api/hospedes
Authorization: Bearer SEU_TOKEN_JWT
Content-Type: application/json
```

```json
{
  "nome": "Carlos Oliveira",
  "cpf": "111.444.777-35",
  "email": "carlos@email.com",
  "telefone": "(34) 99999-3333",
  "dataNascimento": "1998-10-20"
}
```

### Atualizar hóspede

```http
PUT /hotel-checkin/api/hospedes/3
Authorization: Bearer SEU_TOKEN_JWT
Content-Type: application/json
```

```json
{
  "nome": "Carlos Oliveira Santos",
  "cpf": "111.444.777-35",
  "email": "carlos.santos@email.com",
  "telefone": "(34) 98888-3333",
  "dataNascimento": "1998-10-20"
}
```

### Excluir hóspede

```http
DELETE /hotel-checkin/api/hospedes/3
Authorization: Bearer SEU_TOKEN_JWT
```

Resposta esperada:

```text
204 No Content
```

## Coleção do Postman

O arquivo abaixo pode ser importado no Postman para testar os endpoints:

```text
postman/HOSPEDAR_API.postman_collection.json
```

Após executar a requisição de login, copie o token retornado e salve-o na variável `token` da coleção.

## Regras de negócio aplicadas

- O CPF do hóspede é validado antes do cadastro.
- CPF e e-mail do hóspede não podem ser duplicados.
- Uma reserva não pode possuir data de saída anterior ou igual à data de entrada.
- Um quarto não pode receber reservas conflitantes no mesmo período.
- Uma reserva não pode gerar mais de um check-in.
- Ao cancelar um check-in ativo, o quarto volta a ficar disponível.
- Ao realizar checkout, o quarto é liberado e a reserva é finalizada.

## Autoria

Projeto desenvolvido para fins acadêmicos na disciplina **Aplicações para Internet**.
