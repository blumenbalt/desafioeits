CREATE TABLE departamento (
    id bigint NOT NULL,
    descricao character varying(255),
    nome character varying(255),
    qtdusuarios integer DEFAULT 0,
    departamentopai_id bigint
);

CREATE TABLE departamento_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    descricao character varying(255),
    nome character varying(255),
    qtdusuarios integer DEFAULT 0,
    departamentopai_id bigint
);

CREATE SEQUENCE departamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE departamento_id_seq OWNED BY departamento.id;

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE mensagem (
    id bigint NOT NULL,
    data date,
    texto character varying(144) NOT NULL
);

CREATE TABLE mensagem_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    data date,
    texto character varying(255)
);

CREATE TABLE mensagem_departamento (
    id bigint NOT NULL,
    departamento_id bigint,
    mensagem_id bigint,
    usuario_id bigint
);

CREATE TABLE mensagem_departamento_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    departamento_id bigint,
    mensagem_id bigint,
    usuario_id bigint
);

CREATE SEQUENCE mensagem_departamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE mensagem_departamento_id_seq OWNED BY mensagem_departamento.id;

CREATE SEQUENCE mensagem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE mensagem_id_seq OWNED BY mensagem.id;

CREATE TABLE revinfo (
    rev integer NOT NULL,
    revtstmp bigint
);

CREATE TABLE usuario (
    id bigint NOT NULL,
    ativo boolean NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    papel character varying(255),
    perfil character varying(255),
    senha character varying(255) NOT NULL,
    departamento bigint,
    file_path character varying(144)
);

CREATE TABLE usuario_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    ativo boolean,
    email character varying(255),
    nome character varying(255),
    papel character varying(255),
    perfil character varying(255),
    senha character varying(255),
    departamento bigint,
    file_path character varying(144)
);

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;

ALTER TABLE ONLY departamento ALTER COLUMN id SET DEFAULT nextval('departamento_id_seq'::regclass);

ALTER TABLE ONLY mensagem ALTER COLUMN id SET DEFAULT nextval('mensagem_id_seq'::regclass);

ALTER TABLE ONLY mensagem_departamento ALTER COLUMN id SET DEFAULT nextval('mensagem_departamento_id_seq'::regclass);

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);

ALTER TABLE ONLY departamento_aud
    ADD CONSTRAINT departamento_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY departamento
    ADD CONSTRAINT departamento_pkey PRIMARY KEY (id);

ALTER TABLE ONLY mensagem_aud
    ADD CONSTRAINT mensagem_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY mensagem_departamento_aud
    ADD CONSTRAINT mensagem_departamento_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY mensagem_departamento
    ADD CONSTRAINT mensagem_departamento_pkey PRIMARY KEY (id);

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT mensagem_pkey PRIMARY KEY (id);

ALTER TABLE ONLY revinfo
    ADD CONSTRAINT revinfo_pkey PRIMARY KEY (rev);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_4tdehxj7dh8ghfc68kbwbsbll UNIQUE (email);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_be2iakogyjxkqgsvnckqfvqmj UNIQUE (file_path);

ALTER TABLE ONLY departamento
    ADD CONSTRAINT uk_t3llx01h88orevrmbt9iapvvp UNIQUE (nome);

ALTER TABLE ONLY usuario_aud
    ADD CONSTRAINT usuario_aud_pkey PRIMARY KEY (id, rev);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);

ALTER TABLE ONLY mensagem_aud
    ADD CONSTRAINT fk3agrqgvd4hw6wunhhh5iolusv FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk72dk4hhstejm69ldah20058qd FOREIGN KEY (departamento) REFERENCES departamento(id);

ALTER TABLE ONLY departamento_aud
    ADD CONSTRAINT fk8n93x0lujtx6hlwj1clwuyllm FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY usuario_aud
    ADD CONSTRAINT fkah1jxhvcyb3x0y3vuuddwbifv FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY mensagem_departamento
    ADD CONSTRAINT fkh3xih2sim802s3ahj21apog FOREIGN KEY (mensagem_id) REFERENCES mensagem(id);

ALTER TABLE ONLY mensagem_departamento_aud
    ADD CONSTRAINT fkmyuidbnja9jvtamn19fikwte1 FOREIGN KEY (rev) REFERENCES revinfo(rev);

ALTER TABLE ONLY mensagem_departamento
    ADD CONSTRAINT fkoeogvdqur022tqrdnwb4jxdyf FOREIGN KEY (departamento_id) REFERENCES departamento(id);

ALTER TABLE ONLY mensagem_departamento
    ADD CONSTRAINT fkpdk3k649glv61dw4mso7w07le FOREIGN KEY (usuario_id) REFERENCES usuario(id);

ALTER TABLE ONLY departamento
    ADD CONSTRAINT fkqagl4np1v3m9iecg4fr3kqnqm FOREIGN KEY (departamentopai_id) REFERENCES departamento(id);