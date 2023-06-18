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


DROP USER IF EXISTS 'SISTEMA_MINIMO'@'LOCALHOST';

FLUSH PRIVILEGES;

CREATE USER 'SISTEMA_MINIMO'@'LOCALHOST' IDENTIFIED BY 'XpTO$98245015#';

GRANT SELECT, INSERT, UPDATE, DELETE ON DB_LISTA_MINIMO.* TO 'SISTEMA_MINIMO'@'LOCALHOST';

select * from DB_LISTA_MINIMO.CLIENTE;
select * from DB_LISTA_MINIMO.LISTA;
select * from DB_LISTA_MINIMO.PRODUTO;