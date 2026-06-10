-- Sistema HOSPEDAR - Quick Check-in
-- Banco de dados MySQL para a aplicação web e a API RESTful com JWT.

-- O script recria o banco para garantir uma instalação limpa durante os testes.
DROP DATABASE IF EXISTS hotel_checkin;
CREATE DATABASE hotel_checkin
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE hotel_checkin;

CREATE TABLE IF NOT EXISTS usuarios (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    senha      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS hospedes (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    cpf             VARCHAR(14) NOT NULL UNIQUE,
    email           VARCHAR(100) NOT NULL UNIQUE,
    telefone        VARCHAR(20),
    data_nascimento DATE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quartos (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    numero       VARCHAR(10) NOT NULL UNIQUE,
    tipo         ENUM('SIMPLES', 'DUPLO', 'SUITE') NOT NULL,
    preco_diaria DECIMAL(10,2) NOT NULL,
    disponivel   BOOLEAN DEFAULT TRUE,
    descricao    TEXT
);

CREATE TABLE IF NOT EXISTS reservas (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    hospede_id   INT NOT NULL,
    quarto_id    INT NOT NULL,
    data_entrada DATE NOT NULL,
    data_saida   DATE NOT NULL,
    status       ENUM('PENDENTE', 'CONFIRMADA', 'CANCELADA', 'FINALIZADA') DEFAULT 'PENDENTE',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reserva_hospede FOREIGN KEY (hospede_id) REFERENCES hospedes(id),
    CONSTRAINT fk_reserva_quarto FOREIGN KEY (quarto_id) REFERENCES quartos(id)
);

CREATE TABLE IF NOT EXISTS checkins (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    reserva_id    INT NOT NULL UNIQUE,
    data_checkin  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_checkout DATETIME,
    status        ENUM('ATIVO', 'FINALIZADO') DEFAULT 'ATIVO',
    observacoes   TEXT,
    CONSTRAINT fk_checkin_reserva FOREIGN KEY (reserva_id) REFERENCES reservas(id)
);

-- Usuário inicial para testar o login no Postman:
-- e-mail: admin@hotel.com
-- senha: admin123
INSERT INTO usuarios (nome, email, senha) VALUES
('Administrador', 'admin@hotel.com',
 '$2a$10$z4ytrLWPclrbeD4en/59eOlfG6x1DIiSNNPr308hBz/s6Tn17YXvC');

INSERT INTO quartos (numero, tipo, preco_diaria, descricao) VALUES
('101', 'SIMPLES', 150.00, 'Quarto simples com cama de solteiro e banheiro privativo'),
('102', 'SIMPLES', 150.00, 'Quarto simples com vista para o jardim'),
('201', 'DUPLO',   250.00, 'Quarto duplo com cama de casal e TV 40 polegadas'),
('202', 'DUPLO',   280.00, 'Quarto duplo com varanda e vista para a piscina'),
('301', 'SUITE',   450.00, 'Suíte luxo com jacuzzi e sala de estar separada'),
('302', 'SUITE',   500.00, 'Suíte master com vista panorâmica e café da manhã incluso');

-- CPFs fictícios válidos para facilitar a demonstração das validações.
INSERT INTO hospedes (nome, cpf, email, telefone, data_nascimento) VALUES
('João Silva',  '529.982.247-25', 'joao@email.com',  '(34) 99999-1111', '1990-05-15'),
('Maria Souza', '168.995.350-09', 'maria@email.com', '(34) 99999-2222', '1985-08-22');
