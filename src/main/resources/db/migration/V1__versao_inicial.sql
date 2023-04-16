CREATE TABLE IF NOT EXISTS public.carro
(
    codigo serial8,
    ano integer NOT NULL,
    categoria character varying(255),
    cor character varying(255),
    modelo character varying(255),
    placa character varying(255),
    preco double precision,
    CONSTRAINT carro_pkey PRIMARY KEY (codigo)
    );

CREATE TABLE IF NOT EXISTS public.emprestimo
(
    codigo serial8,
    cpf character varying(255),
    devolucao timestamp without time zone,
    inicio timestamp without time zone,
    locatario character varying(255),
    pagamento double precision,
    taxa double precision,
    termino timestamp without time zone,
    carro bigint,
    CONSTRAINT emprestimo_pkey PRIMARY KEY (codigo),
    CONSTRAINT carro_fkey FOREIGN KEY (carro)
    REFERENCES public.carro (codigo) MATCH SIMPLE
                        ON UPDATE NO ACTION
                        ON DELETE NO ACTION
    );