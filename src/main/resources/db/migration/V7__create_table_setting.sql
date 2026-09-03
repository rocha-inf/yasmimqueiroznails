CREATE TABLE setting (
    id                          UUID            NOT NULL ,
    user_id                     UUID            NOT NULL,
    auto_approve_appointments   BOOLEAN         NOT NULL    DEFAULT FALSE,
    created_at                  TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_setting PRIMARY KEY (id),
    CONSTRAINT fk_setting_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT uq_setting_user UNIQUE (user_id)
);


COMMENT ON TABLE setting IS
    'Armazena as configurações da profissional relacionadas ao funcionamento dos agendamentos.';

COMMENT ON COLUMN setting.user_id IS
    'Identifica a profissional responsável pelas configurações.';

COMMENT ON COLUMN setting.auto_approve_appointments IS
    'Indica se novos agendamentos devem ser aprovados automaticamente ou permanecer pendentes para aprovação manual.';