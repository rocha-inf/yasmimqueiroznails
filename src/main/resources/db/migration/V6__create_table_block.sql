CREATE TYPE block_type AS ENUM (
    'ONCE',
    'RECURRING'
    );

CREATE TYPE day_of_week AS ENUM (
    'DAILY',
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
    );

CREATE TABLE block (
    id              UUID            NOT NULL ,
    user_id         UUID            NOT NULL,
    name            VARCHAR(200)    NOT NULL,
    notes           TEXT,
    type            block_type      NOT NULL,
    day_of_week     day_of_week,
    start_date      DATE            NOT NULL,
    end_date        DATE,
    start_time      TIME            NOT NULL,
    end_time        TIME            NOT NULL,
    created_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMPTZ,

    CONSTRAINT pk_block PRIMARY KEY (id),
    CONSTRAINT fk_block_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE INDEX idx_block_user_id ON block(user_id);



COMMENT ON TYPE block_type IS
    'Define se o bloqueio ocorre uma única vez ou de forma recorrente.';

COMMENT ON TYPE day_of_week IS
    'Define os dias da semana em que um bloqueio recorrente se aplica.';

COMMENT ON TABLE block IS
    'Armazena os períodos em que a profissional não está disponível para novos agendamentos.';

COMMENT ON COLUMN block.user_id IS
    'Identifica a profissional responsável pelo bloqueio.';

COMMENT ON COLUMN block.name IS
    'Nome utilizado para identificar o motivo ou finalidade do bloqueio.';

COMMENT ON COLUMN block.notes IS
    'Observações adicionais sobre o bloqueio.';

COMMENT ON COLUMN block.type IS
    'Indica se o bloqueio ocorre uma única vez ou de forma recorrente.';

COMMENT ON COLUMN block.day_of_week IS
    'Define o dia da semana em que o bloqueio recorrente se aplica. NULL indica que o bloqueio não é recorrente.';

COMMENT ON COLUMN block.start_date IS
    'Data de início do período em que o bloqueio se aplica.';

COMMENT ON COLUMN block.end_date IS
    'Data final do período em que o bloqueio se aplica. NULL indica que não há uma data final definida.';

COMMENT ON COLUMN block.start_time IS
    'Horário de início do período de indisponibilidade.';

COMMENT ON COLUMN block.end_time IS
    'Horário de término do período de indisponibilidade.';

COMMENT ON COLUMN block.deleted_at IS
    'Data e hora da exclusão lógica do bloqueio. NULL indica que o bloqueio não foi excluído.';