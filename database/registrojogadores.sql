CREAT DATABASE registrojogadores;
USE registrojogadores

CREATE TABLE time(
	id INTEGER(10) NOT NULL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	nomeCidade VARCHAR(50) NOT NULL
);

CREATE TABLE jogador(
	id INTEGER(10) NOT NULL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	dataNasc DATE NOT NULL,
	altura DECIMAL(3,2) NOT NULL,
	peso DECIMAL(5,2) NOT NULL,
	idTime INTEGER (10),  FOREIGN KEY (teamID) REFERENCES team (id)
);