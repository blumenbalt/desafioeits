TRUNCATE "departamento" CASCADE;

INSERT INTO departamento (id, nome, descricao, departamentopai_id, qtdusuarios ) 
VALUES  
(1, 'departamento dos administradores', 'departamento administrativo', null, 1),
(2, 'departamento financeiro', 'cuida das finanças da empresa', 1, null),
(3, 'departamento suporte','presta suporte aos usuarios do sistema', 1 , null);

SELECT pg_catalog.setval('public.departamento_id_seq', 1000, TRUE);