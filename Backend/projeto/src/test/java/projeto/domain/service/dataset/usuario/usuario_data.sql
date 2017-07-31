TRUNCATE "usuario" CASCADE;

INSERT INTO usuario (id, nome, senha, email, ativo, departamento, papel, perfil) 
VALUES (1, 'admin', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 'admin@admin.com', true, 1,  'SUPERIOR', 'ROLE_ADMINISTRADOR'),
	   (2, 'user02','$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 'user02@email.com', true, null ,  null, 'ROLE_COLABORADOR'), 
	   (3, 'user03',  '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 'user03@email.com', true, 1 , 'COMUM', 'ROLE_COLABORADOR'),
	   (4, 'user04',  '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 'user04@email.com', false, null,  null, 'ROLE_COLABORADOR');

SELECT pg_catalog.setval('public.usuario_id_seq', 1000, TRUE);