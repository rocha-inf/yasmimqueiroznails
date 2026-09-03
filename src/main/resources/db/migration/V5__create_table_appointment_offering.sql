CREATE TABLE appointment_offering(
    id                  UUID                NOT NULL,
    appointment_id      UUID                NOT NULL,
    offering_id         UUID                NOT NULL,
    price               DECIMAL(10, 2)      NOT NULL,
    duration_minutes    INT                 NOT NULL,
    created_at          TIMESTAMPTZ         NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMPTZ         NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMPTZ,

    CONSTRAINT pk_appointment_offering PRIMARY KEY (id),
    CONSTRAINT fk_appointment_offering_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE RESTRICT,
    CONSTRAINT fk_appointment_offering_offering FOREIGN KEY (offering_id) REFERENCES offering(id) ON DELETE RESTRICT
);

CREATE INDEX idx_appointment_offering_appointment_id ON appointment_offering(appointment_id);
CREATE INDEX idx_appointment_offering_offering_id ON appointment_offering(offering_id);

CREATE UNIQUE INDEX uq_appointment_offering_appointment_id_offering_id
    ON appointment_offering(appointment_id, offering_id)
    WHERE deleted_at IS NULL;


COMMENT ON TABLE appointment_offering IS
    'Armazena os serviços vinculados a cada agendamento, mantendo o preço e a duração registrados no momento do agendamento.';

COMMENT ON COLUMN appointment_offering.appointment_id IS
    'Identifica o agendamento ao qual o serviço está vinculado.';

COMMENT ON COLUMN appointment_offering.offering_id IS
    'Identifica o serviço selecionado para o agendamento.';

COMMENT ON COLUMN appointment_offering.price IS
    'Preço do serviço registrado no momento do agendamento.';

COMMENT ON COLUMN appointment_offering.duration_minutes IS
    'Duração do serviço em minutos registrada no momento do agendamento.';

COMMENT ON COLUMN appointment_offering.deleted_at IS
    'Data e hora da exclusão lógica do serviço no agendamento. NULL indica que o vínculo permanece ativo.';

COMMENT ON INDEX uq_appointment_offering_appointment_id_offering_id IS
    'Garante que um mesmo serviço não seja adicionado mais de uma vez ao mesmo agendamento enquanto o vínculo estiver ativo.';