--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

-- Started on 2017-06-29 09:28:28 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2142 (class 1262 OID 16387)
-- Name: eits02; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE eits02 WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'pt_BR.UTF-8' LC_CTYPE = 'pt_BR.UTF-8';


ALTER DATABASE eits02 OWNER TO postgres;

\connect eits02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12397)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2145 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 16391)
-- Name: usuario; Type: TABLE; Schema: public; Owner: eits02
--

CREATE TABLE usuario (
    id integer NOT NULL,
    nome character varying(24) NOT NULL,
    email character varying(64) NOT NULL,
    departamento character varying(64) NOT NULL
);


ALTER TABLE usuario OWNER TO eits02;

--
-- TOC entry 181 (class 1259 OID 16389)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: eits02
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usuario_id_seq OWNER TO eits02;

--
-- TOC entry 2146 (class 0 OID 0)
-- Dependencies: 181
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: eits02
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 2021 (class 2604 OID 16394)
-- Name: id; Type: DEFAULT; Schema: public; Owner: eits02
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 2023 (class 2606 OID 16396)
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: eits02
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2144 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-06-29 09:28:28 BRT

--
-- PostgreSQL database dump complete
--
