CREATE TABLE offering(
    id                  UUID                NOT NULL,
    user_id             UUID                NOT NULL,
    category_id         UUID                NOT NULL,
    name                VARCHAR(200)        NOT NULL,
    description         TEXT,
    price               DECIMAL(10, 2)      NOT NULL,
    duration_minutes    INT                 NOT NULL,
    created_at          TIMESTAMPTZ         NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMPTZ         NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMPTZ,

    CONSTRAINT pk_offering PRIMARY KEY (id),
    CONSTRAINT fk_offering_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_offering_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE RESTRICT
);

CREATE INDEX idx_offering_user_id ON offering(user_id);
CREATE INDEX idx_offering_category_id ON offering(category_id);

COMMENT ON TABLE offering IS
    'Armazena os serviços oferecidos pela profissional. Cada serviço pertence a uma categoria e possui preço e duração.';

COMMENT ON COLUMN offering.user_id IS
    'Identifica a profissional responsável pelo serviço.';

COMMENT ON COLUMN offering.category_id IS
    'Identifica a categoria à qual o serviço pertence.';

COMMENT ON COLUMN offering.name IS
    'Nome do serviço oferecido.';

COMMENT ON COLUMN offering.description IS
    'Descrição opcional do serviço.';

COMMENT ON COLUMN offering.price IS
    'Preço atual do serviço.';

COMMENT ON COLUMN offering.duration_minutes IS
    'Duração do serviço em minutos.';

COMMENT ON COLUMN offering.deleted_at IS
    'Data e hora da exclusão lógica do serviço. NULL indica que o serviço não foi excluído.';