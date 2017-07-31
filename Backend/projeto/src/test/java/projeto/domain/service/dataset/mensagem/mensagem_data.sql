TRUNCATE "mensagem" CASCADE;

INSERT INTO mensagem (id, texto, data) 
VALUES 
(1, 'teste1', null),
(2, 'teste2', null),
(3, 'teste3', null),
(4, 'teste4', null),
(5, 'teste5', null),
(6, 'teste6', null);

SELECT pg_catalog.setval('public.mensagem_id_seq', 1000, TRUE);