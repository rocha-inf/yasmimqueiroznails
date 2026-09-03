CREATE TYPE appointment_status AS ENUM(
    'PENDING',
    'CONFIRMED',
    'CANCELLED',
    'REJECTED',
    'COMPLETED'
    );

CREATE TABLE appointment(
    id              UUID                    NOT NULL,
    user_id         UUID                    NOT NULL,
    start_at        TIMESTAMPTZ             NOT NULL,
    user_notes      TEXT,
    admin_notes     TEXT,
    status          appointment_status      NOT NULL    DEFAULT 'PENDING',
    created_at      TIMESTAMPTZ             NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ             NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    cancelled_at    TIMESTAMPTZ,
    cancelled_by    UUID,

    CONSTRAINT pk_appointment PRIMARY KEY (id),
    CONSTRAINT fk_appointment_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_appointment_cancelled_by FOREIGN KEY (cancelled_by) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX uq_appointment_user_start_at
    ON appointment(user_id, start_at)
    WHERE status <> 'CANCELLED' AND status <> 'REJECTED';

CREATE INDEX idx_appointment_user_id ON appointment(user_id);


COMMENT ON TYPE appointment_status IS
    'Define os possíveis estados de um agendamento.';

COMMENT ON TABLE appointment IS
    'Armazena os agendamentos realizados pelos clientes.';

COMMENT ON COLUMN appointment.user_id IS
    'Identifica o cliente responsável pelo agendamento.';

COMMENT ON COLUMN appointment.start_at IS
    'Data e hora de início do agendamento.';

COMMENT ON COLUMN appointment.user_notes IS
    'Observações informadas pelo cliente no momento do agendamento.';

COMMENT ON COLUMN appointment.admin_notes IS
    'Observações internas da profissional sobre o agendamento.';

COMMENT ON COLUMN appointment.status IS
    'Indica o estado atual do agendamento, como pendente, confirmado, cancelado, recusado ou concluído.';

COMMENT ON COLUMN appointment.cancelled_at IS
    'Data e hora em que o agendamento foi cancelado. NULL indica que o agendamento não foi cancelado.';

COMMENT ON COLUMN appointment.cancelled_by IS
    'Identifica o usuário responsável pelo cancelamento do agendamento.';

COMMENT ON INDEX uq_appointment_user_start_at IS
    'Impede que um mesmo cliente possua mais de um agendamento ativo no mesmo horário.';