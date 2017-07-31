TRUNCATE "mensagem_departamento" CASCADE;

INSERT INTO mensagem_departamento (id, usuario_id, departamento_id, mensagem_id) 
VALUES 
(1, 1, 1, 1),
(2, 1, 1, 2),
(3, 3, 1, 3),
(4, 1, 1, 4),
(5, 3, 1, 5);

SELECT pg_catalog.setval('public.mensagem_departamento_id_seq', 1000, TRUE);