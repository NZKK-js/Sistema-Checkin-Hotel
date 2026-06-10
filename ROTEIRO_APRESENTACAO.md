# Roteiro curto para apresentação

## 1. Introdução

O projeto HOSPEDAR é um sistema de check-in de hotel desenvolvido em Java. Ele permite gerenciar hóspedes, quartos, reservas e check-ins. Nesta etapa do trabalho, foi adicionada uma API RESTful com autenticação JWT.

## 2. Estrutura em camadas

- **Controller:** recebe as requisições e devolve respostas HTTP.
- **Service:** valida os dados e aplica as regras de negócio.
- **DAO:** executa as consultas no banco MySQL.
- **Filter:** verifica se o token JWT foi enviado corretamente.

## 3. Demonstração no Postman

1. Execute `POST /api/auth/login` com o usuário `admin@hotel.com` e a senha `admin123`.
2. Copie o token retornado.
3. Faça `GET /api/hospedes` sem token para mostrar o erro `401 Unauthorized`.
4. Adicione `Authorization: Bearer SEU_TOKEN` e repita a consulta.
5. Cadastre um hóspede com `POST /api/hospedes`.
6. Consulte o hóspede criado com `GET /api/hospedes/{id}`.
7. Atualize os dados com `PUT /api/hospedes/{id}`.
8. Exclua o registro com `DELETE /api/hospedes/{id}`.

## 4. Pontos para explicar

- O login retorna um token JWT com validade de oito horas.
- As senhas são protegidas com BCrypt.
- As rotas `/api/hospedes` exigem autenticação.
- O sistema retorna JSON e códigos HTTP adequados.
- O projeto também mantém a interface visual em JSP para demonstrar o sistema no navegador.
