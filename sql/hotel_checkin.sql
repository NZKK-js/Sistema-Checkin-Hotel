-- Sistema de Check-in de Hotel

CREATE DATABASE IF NOT EXISTS hotel_checkin;
USE hotel_checkin;

CREATE TABLE hospedes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    data_nascimento DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE quartos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(10) UNIQUE NOT NULL,
    tipo ENUM('SIMPLES', 'DUPLO', 'SUITE') NOT NULL,
    preco_diaria DECIMAL(10,2) NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE,
    descricao TEXT
);

CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hospede_id INT NOT NULL,
    quarto_id INT NOT NULL,
    data_entrada DATE NOT NULL,
    data_saida DATE NOT NULL,
    status ENUM('PENDENTE', 'CONFIRMADA', 'CANCELADA', 'FINALIZADA') DEFAULT 'PENDENTE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hospede_id) REFERENCES hospedes(id),
    FOREIGN KEY (quarto_id) REFERENCES quartos(id)
);

CREATE TABLE checkins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reserva_id INT NOT NULL,
    data_checkin DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_checkout DATETIME,
    status ENUM('ATIVO', 'FINALIZADO') DEFAULT 'ATIVO',
    observacoes TEXT,
    FOREIGN KEY (reserva_id) REFERENCES reservas(id)
);

INSERT INTO quartos (numero, tipo, preco_diaria, descricao) VALUES
('101', 'SIMPLES', 150.00, 'Quarto simples com cama de solteiro e banheiro privativo'),
('102', 'SIMPLES', 150.00, 'Quarto simples com vista para o jardim'),
('201', 'DUPLO', 250.00, 'Quarto duplo com cama de casal e TV 40"'),
('202', 'DUPLO', 280.00, 'Quarto duplo com varanda e vista para a piscina'),
('301', 'SUITE', 450.00, 'Suíte luxo com jacuzzi e sala de estar separada'),
('302', 'SUITE', 500.00, 'Suíte master com vista panorâmica e café da manhã incluso');

INSERT INTO hospedes (nome, cpf, email, telefone, data_nascimento) VALUES
('João Silva', '123.456.789-00', 'joao@email.com', '(34) 99999-1111', '1990-05-15'),
('Maria Souza', '987.654.321-00', 'maria@email.com', '(34) 99999-2222', '1985-08-22');
