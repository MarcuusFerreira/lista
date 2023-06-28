SET SQL_SAFE_UPDATES = 0;
DROP DATABASE IF EXISTS DB_LISTA_MINIMO;
CREATE DATABASE DB_LISTA_MINIMO;


CREATE TABLE DB_LISTA_MINIMO.CLIENTE (
	ID_CLIENTE INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NOME_CLIENTE VARCHAR (100) NOT NULL,
    CPF VARCHAR (11) NOT NULL UNIQUE,
    DATA_NASCIMENTO DATE NOT NULL,
    DATA_CADASTRO DATETIME NOT NULL,
	TIPO_USUARIO INTEGER NOT NULL,
    NOME_USUARIO VARCHAR (20) NOT NULL UNIQUE,
    SENHA VARCHAR(40) NOT NULL,
    INDEX (ID_CLIENTE)
);

CREATE TABLE DB_LISTA_MINIMO.LISTA (
	ID_LISTA INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ID_CLIENTE INTEGER NOT NULL,
    NOME VARCHAR (55) NOT NULL,
    DATA_LISTA DATETIME NOT NULL,
    CONSTRAINT FK_ID_CLIENTE FOREIGN KEY(ID_CLIENTE) REFERENCES CLIENTE (ID_CLIENTE),
    INDEX (ID_LISTA),
    INDEX (ID_CLIENTE)
);

CREATE TABLE DB_LISTA_MINIMO.PRODUTO (
	ID_PRODUTO INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	SETOR VARCHAR(55) NOT NULL,
	MARCA VARCHAR (55) NOT NULL,
	NOME VARCHAR(55) NOT NULL,
    DATA_CADASTRO DATETIME NOT NULL,
    INDEX (ID_PRODUTO)
);

CREATE TABLE DB_LISTA_MINIMO.LISTA_PRODUTO (
	ID_LISTA INTEGER NOT NULL,
    ID_PRODUTO INTEGER NOT NULL,
    MARCADO TINYINT,
    UNIDADE_MEDIDA ENUM ('KG', 'QTD'),
    VALOR_UNIDADE DOUBLE,
    OBS VARCHAR (255),
    PRIMARY KEY (ID_LISTA, ID_PRODUTO),
    CONSTRAINT FK_ID_LISTA FOREIGN KEY (ID_LISTA) REFERENCES LISTA (ID_LISTA),
    CONSTRAINT FK_ID_PRODUTO FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO (ID_PRODUTO),
    INDEX (ID_LISTA),
    INDEX(ID_PRODUTO)
);

DROP USER IF EXISTS 'SISTEMA_MINIMO'@'LOCALHOST';

FLUSH PRIVILEGES;

CREATE USER 'SISTEMA_MINIMO'@'LOCALHOST' IDENTIFIED BY 'XpTO$98245015#';

GRANT SELECT, INSERT , UPDATE, DELETE ON DB_LISTA_MINIMO.* TO 'SISTEMA_MINIMO'@'LOCALHOST';

-- GRANT SELECT, UPDATE, DELETE ON DB_LISTA_MINIMO_LOG.* TO 'SISTEMA_MINIMO'@'LOCALHOST';

FLUSH PRIVILEGES;


-- CLIENTES 
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('ADM', '68535626077', '2000-11-12', '2023-06-18', '0', 'ADM', 'ADM123!');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Amanda Sousa', '14159661092', '1981-06-16', '2023-06-18', '1', 'amandasousa', 'senha123456');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Bruna Santos', '61749224054', '1983-09-05', '2023-06-18', '1', 'brunasantos', 'senha789123');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Camila Rodrigues', '81063410037', '1993-04-22', '2023-06-18', '1', 'camilarodrigues', 'abc123456');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Carlos Eduardo', '09221892000', '1982-09-25', '2023-06-18', '1', 'carloseduardo', '987654');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Diego Pereira', '27867564055', '1996-01-11', '2023-06-18', '1', 'diegopereira', 'abc123789');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Maria da Silva', '13181560090', '1995-03-20', '2023-06-18', '1', 'mariasilva', 'abcdef');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('João Carlos', '71064900089', '1988-07-05', '2023-06-18', '1', 'joaocarlos', 'qwerty');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Fernando Lima', '33982838029', '1994-05-09', '2023-06-18', '1', 'fernandolima', 'qwerty789456123');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Lucas Martins', '52376543045', '1999-02-28', '2023-06-18', '1', 'lucasmartins', 'qwerty789');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Fernando Santos', '35024299019', '1987-02-18', '2023-06-18', '1', 'fernandosantos', 'senhaabc');
INSERT INTO DB_LISTA_MINIMO.CLIENTE (NOME_CLIENTE, CPF, DATA_NASCIMENTO, DATA_CADASTRO, TIPO_USUARIO, NOME_USUARIO, SENHA) VALUES ('Rodrigo Santos', '49425971021', '1983-11-27', '2023-06-18', '1', 'rodrigosantos', 'senha456');


-- PRODUTOS

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Frutas e Vegetais', 'Fuji', 'Maçã', 1, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Frutas e Vegetais', 'Baía', 'Laranja', 1, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Frutas e Vegetais', 'Branca', 'Banana', 1, '2023-06-19 10:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Açougue', 'Friboi', 'Maminha', 1, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Açougue', 'Friboi', 'Picanha', 1, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Açougue', 'Friboi', 'Alcatra', 1, '2023-06-19 10:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Laticínios', 'Itambé', 'Iogurte Natural', 0, '2023-06-19 11:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Laticínios', 'Frimesa', 'Queijo Minas Frescal', 0, '2023-06-19 11:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Laticínios', 'Batavo', 'Manteiga', 0, '2023-06-19 11:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Congelados', 'Perdigão', 'Pizza Margherita', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Congelados', 'Sadia', 'Pizza Calabresa', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Congelados', 'Aurora', 'Pizza Quatro Queijos', 0, '2023-06-19 10:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Mercearia', 'Tio João', 'Arroz', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Mercearia', 'Camil', 'Feijão', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Mercearia', 'Rocha', 'Tapioca', 0, '2023-06-19 10:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Higiene Pessoal', 'Dove', 'Sabonete', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Higiene Pessoal', 'Dove', 'Shampoo', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Higiene Pessoal', 'Close Up', 'Creme Dental', 0, '2023-06-19 10:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Limpeza Doméstica', 'Limpol', 'Detergente', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Limpeza Doméstica', 'Veja', 'Limpador Multiuso', 0, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Limpeza Doméstica', 'Comfort', 'Amaciante de Roupas', 0, '2023-06-19 10:00:00');

INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Bebidas Alcoólicas', 'Cachaça', 'Cachacinha Dom Ivo', 1, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Bebidas Alcoólicas', 'Cerveja', 'Heineken', 1, '2023-06-19 10:00:00');
INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Bebidas Alcoólicas', 'Vinho', 'Doña Dominga', 1, '2023-06-19 10:00:00');

-- INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Outros', 'Marca Outros', 'Produto Genérico', 0, '2023-06-19 10:00:00');
-- INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Outros', 'Marca Outros', 'Produto Genérico', 0, '2023-06-19 10:00:00');
-- INSERT INTO DB_LISTA_MINIMO.PRODUTO (SETOR, MARCA, NOME, TIPO_PRODUTO, DATA_CADASTRO) VALUES ('Outros', 'Marca Outros', 'Produto Genérico', 0, '2023-06-19 10:00:00');

select * from DB_LISTA_MINIMO.CLIENTE;
select * from DB_LISTA_MINIMO.LISTA;
select * from DB_LISTA_MINIMO.PRODUTO;
select * from DB_LISTA_MINIMO.lista_produto;

update lista set nome = ? where id_lista = ?