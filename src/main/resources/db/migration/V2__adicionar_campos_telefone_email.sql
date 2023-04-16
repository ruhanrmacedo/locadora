ALTER TABLE IF EXISTS public.emprestimo
    ADD COLUMN IF NOT EXISTS telefone character varying(100);

ALTER TABLE IF EXISTS public.emprestimo
    ADD COLUMN IF NOT EXISTS email character varying(100);