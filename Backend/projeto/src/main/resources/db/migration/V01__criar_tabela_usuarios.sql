CREATE TABLE usuarios (
	id SERIAL PRIMARY KEY,
	nome varchar(64),
	email varchar(64),
	senha varchar(128),
	ativo boolean,
	perfil varchar (24)
);