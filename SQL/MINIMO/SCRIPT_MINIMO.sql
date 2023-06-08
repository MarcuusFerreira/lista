SET SQL_SAFE_UPDATES = 0;
DROP DATABASE IF EXISTS DB_LISTA_MINIMO;
CREATE DATABASE DB_LISTA_MINIMO;
DROP DATABASE IF EXISTS DB_LISTA_MINIMO_LOG;
CREATE DATABASE DB_LISTA_MINIMO_LOG;

CREATE TABLE DB_LISTA_MINIMO.CLIENTE (
	ID_CLIENTE INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NOME_CLIENTE VARCHAR (100) NOT NULL,
    CPF VARCHAR (11) NOT NULL UNIQUE,
    DATA_NASCIMENTO DATE NOT NULL,
    DATA_CADASTRO DATETIME NOT NULL,
	TIPO_USUARIO INTEGER NOT NULL,
    NOME_USUARIO VARCHAR (20) NOT NULL,
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
    NOME VARCHAR(55) NOT NULL,
    DATA_CADASTRO DATETIME NOT NULL,
    DESCRICAO VARCHAR (255),
    TIPO_PRODUTO INTEGER NOT NULL,
    MARCA VARCHAR (55) NOT NULL,
    INDEX (ID_PRODUTO)
);

CREATE TABLE DB_LISTA_MINIMO.LISTA_PRODUTO (
	ID_LISTA INTEGER NOT NULL,
    ID_PRODUTO INTEGER NOT NULL,
    MARCADO TINYINT,
    QUANTIDADE INTEGER,
    PESO DOUBLE,
    OBS VARCHAR (255),
    PRIMARY KEY (ID_LISTA, ID_PRODUTO),
    CONSTRAINT FK_ID_LISTA FOREIGN KEY (ID_LISTA) REFERENCES LISTA (ID_LISTA),
    CONSTRAINT FK_ID_PRODUTO FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO (ID_PRODUTO),
    INDEX (ID_LISTA),
    INDEX(ID_PRODUTO)
);

------------------------------------------------------------------------------------------------------------

/*-- Log do cliente
CREATE TABLE DB_LISTA_MINIMO_LOG.HISTORICO_CLIENTE_LOG(
	ID_HISTORICO_CLIENTE_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USUARIO VARCHAR(55),
    DT_ALTERACAO DATETIME,
    TIPO_ALTERACAO VARCHAR (25),
    TABELA VARCHAR(55)
);

CREATE TABLE DB_LISTA_MINIMO_LOG.CLIENTE_LOG (
	ID_CLIENTE_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ID_HISTORICO_CLIENTE INTEGER NOT NULL,
    CAMPO VARCHAR(55),
    VALOR_ANTES VARCHAR(255),
    VALOR_DEPOIS VARCHAR(255),
    CONSTRAINT FK_ID_HISTORICO_CLIENTE_LOG FOREIGN KEY (ID_HISTORICO_CLIENTE_LOG) REFERENCES HISTORICO_CLIENTE_LOG(ID_HISTORICO_CLIENTE_LOG)
);

-- Log da lista
CREATE TABLE DB_LISTA_MINIMO_LOG.HISTORICO_LISTA_LOG(
	ID_HISTORICO_LISTA_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USUARIO VARCHAR(55),
    DT_ALTERACAO DATETIME,
    TIPO_ALTERACAO VARCHAR (25),
    TABELA VARCHAR(55)
);

CREATE TABLE DB_LISTA_MINIMO_LOG.LISTA_LOG (
	ID_LISTA_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ID_HISTORICO_CLIENTE INTEGER NOT NULL,
    CAMPO VARCHAR(55),
    VALOR_ANTES VARCHAR(255),
    VALOR_DEPOIS VARCHAR(255),
    CONSTRAINT FK_ID_HISTORICO_LISTA_LOG FOREIGN KEY (ID_HISTORICO_LISTA_LOG) REFERENCES HISTORICO_LISTA_LOG(ID_HISTORICO_LISTA_LOG)
);

-- Log de produto
CREATE TABLE DB_LISTA_MINIMO_LOG.HISTORICO_PRODUTO_LOG(
	ID_HISTORICO_PRODUTO_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USUARIO VARCHAR(55),
    DT_ALTERACAO DATETIME,
    TIPO_ALTERACAO VARCHAR (25),
    TABELA VARCHAR(55)
);

CREATE TABLE DB_LISTA_MINIMO_LOG.PRODUTO_LOG (
	ID_PRODUTO_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ID_HISTORICO_CLIENTE INTEGER NOT NULL,
    CAMPO VARCHAR(55),
    VALOR_ANTES VARCHAR(255),
    VALOR_DEPOIS VARCHAR(255),
    CONSTRAINT FK_ID_HISTORICO_PRODUTO_LOG FOREIGN KEY (ID_HISTORICO_PRODUTO_LOG) REFERENCES HISTORICO_PRODUTO_LOG(ID_HISTORICO_PRODUTO_LOG)
);

-- Log da Lista Produto
CREATE TABLE DB_LISTA_MINIMO_LOG.HISTORICO_LISTA_PRODUTO_LOG(
	ID_HISTORICO_LISTA_PRODUTO_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    USUARIO VARCHAR(55),
    DT_ALTERACAO DATETIME,
    TIPO_ALTERACAO VARCHAR (25),
    TABELA VARCHAR(55)
);

CREATE TABLE DB_LISTA_MINIMO_LOG.LISTA_PRODUTO_LOG(
	ID_LISTA_PRODUTO_LOG INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ID_HISTORICO_LISTA_PRODUTO_LOG INTEGER NOT NULL,
    CAMPO VARCHAR(55),
    VALOR_ANTES VARCHAR(255),
    VALOR_DEPOIS VARCHAR(255),
    CONSTRAINT FK_ID_HISTORICO_LISTA_PRODUTO_LOG FOREIGN KEY (ID_HISTORICO_LISTA_PRODUTO_LOG) REFERENCES HISTORICO_LISTA_PRODUTO_LOG(ID_HISTORICO_LISTA_PRODUTO_LOG)
);*/

DROP USER IF EXISTS 'SISTEMA_MINIMO'@'LOCALHOST';

FLUSH PRIVILEGES;

CREATE USER 'SISTEMA_MINIMO'@'LOCALHOST' IDENTIFIED BY 'XpTO$98245015#';

GRANT SELECT, UPDATE, DELETE ON DB_LISTA_MINIMO.* TO 'SISTEMA_MINIMO'@'LOCALHOST';

-- GRANT SELECT, UPDATE, DELETE ON DB_LISTA_MINIMO_LOG.* TO 'SISTEMA_MINIMO'@'LOCALHOST';

FLUSH PRIVILEGES;
/*
DELIMITER $$
CREATE TRIGGER TRG_CLIENTE_INSERT_LOG_AI AFTER INSERT ON CLIENTE FOR EACH ROW
BEGIN
	
END $$
DELIMITER ;/*