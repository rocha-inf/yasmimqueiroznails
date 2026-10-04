CREATE TABLE email_verification_token(
    id          UUID            NOT NULL,
    token       VARCHAR(100)    NOT NULL,
    user_id     UUID            NOT NULL,
    used_at     TIMESTAMPTZ,
    expires_at  TIMESTAMPTZ     NOT NULL,
    created_at  TIMESTAMPTZ     DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ     DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_email_verification_token PRIMARY KEY (id),
    CONSTRAINT fk_email_verification_token_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_email_verification_token_token UNIQUE (token),
    CONSTRAINT ck_email_verification_token_expires_at CHECK (expires_at > created_at),
    CONSTRAINT ck_email_verification_token_used_at CHECK (used_at IS NULL OR (used_at > created_at AND used_at <= expires_at))
);