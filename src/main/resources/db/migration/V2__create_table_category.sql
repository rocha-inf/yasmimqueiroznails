CREATE TABLE category(
    id              UUID            NOT NULL,
    name            VARCHAR(200)    NOT NULL,
    description     TEXT,
    created_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMPTZ,

    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uq_category_name
    ON category(name)
    WHERE deleted_at IS NULL;


COMMENT ON TABLE category IS
    'Armazena as categorias utilizadas para organizar os serviços oferecidos pela profissional.';

COMMENT ON COLUMN category.name IS
    'Nome da categoria do serviço.';

COMMENT ON COLUMN category.description IS
    'Descrição opcional da categoria.';

COMMENT ON COLUMN category.deleted_at IS
    'Data e hora da exclusão lógica da categoria. NULL indica que a categoria não foi excluída.';

COMMENT ON INDEX uq_category_name IS
    'Garante que duas categorias não excluídas não possuam o mesmo nome.';