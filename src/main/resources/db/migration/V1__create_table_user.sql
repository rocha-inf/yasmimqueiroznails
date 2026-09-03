CREATE TYPE user_status AS ENUM(
    'ACTIVE',
    'DELETED',
    'PENDING_VERIFICATION'
    );

CREATE TYPE user_role AS ENUM(
    'PROFESSIONAL',
    'CLIENT'
    );

CREATE TABLE users (
    id                  UUID            NOT NULL,
    nickname            VARCHAR(200),
    role                user_role       NOT NULL    DEFAULT 'CLIENT',
    first_name          VARCHAR(200)    NOT NULL,
    last_name           VARCHAR(200)    NOT NULL,
    phone_number        VARCHAR(20)     NOT NULL,
    email               VARCHAR(300)    NOT NULL,
    password_hash       VARCHAR(200)    NOT NULL,
    booking_blocked     BOOLEAN         NOT NULL    DEFAULT FALSE,
    status              user_status     NOT NULL    DEFAULT 'PENDING_VERIFICATION',
    created_at          TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMPTZ,

    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uq_user_nickname_active
    ON users(nickname)
    WHERE status <> 'DELETED';

CREATE UNIQUE INDEX uq_user_email_active
    ON users(email)
    WHERE status <> 'DELETED';

CREATE UNIQUE INDEX uq_user_phone_number_active
    ON users(phone_number)
    WHERE status <> 'DELETED';


COMMENT ON TABLE users IS
    'Armazena os usuários do sistema, incluindo clientes e a profissional responsável pelos atendimentos.';

COMMENT ON COLUMN users.nickname IS
    'Apelido opcional usado principalmente para facilitar a identificação do cliente pela profissional.';

COMMENT ON TYPE user_role IS
    'Define o tipo de usuário do sistema, podendo ser profissional ou cliente.';

COMMENT ON COLUMN users.role IS
    'Indica se o usuário possui o papel de profissional ou cliente no sistema.';

COMMENT ON COLUMN users.password_hash IS
    'Senha do usuário armazenada na forma de hash. A senha original nunca deve ser armazenada no banco de dados.';

COMMENT ON COLUMN users.booking_blocked IS
    'Indica se o usuário está impedido de criar novos agendamentos. O bloqueio não impede o acesso à conta.';

COMMENT ON COLUMN users.status IS
    'Indica o estado atual da conta do usuário, como aguardando confirmação do e-mail, ativa ou excluída.';

COMMENT ON COLUMN users.deleted_at IS
    'Data e hora da exclusão lógica do usuário. NULL indica que o usuário não foi excluído.';

COMMENT ON INDEX uq_user_nickname_active IS
    'Garante que dois usuários não deletados não possuam o mesmo apelido.';

COMMENT ON INDEX uq_user_email_active IS
    'Garante que dois usuários não deletados não utilizem o mesmo endereço de e-mail.';

COMMENT ON INDEX uq_user_phone_number_active IS
    'Garante que dois usuários não deletados não utilizem o mesmo número de telefone.';

