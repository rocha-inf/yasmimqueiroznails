DROP INDEX idx_block_user_id;
DROP TABLE block;
DROP TYPE day_of_week;
DROP TYPE block_type;

CREATE TABLE block_once(
    id              UUID            NOT NULL,
    name            VARCHAR(200)    NOT NULL,
    description     TEXT,
    starts_at       TIMESTAMPTZ     NOT NULL,
    ends_at         TIMESTAMPTZ     NOT NULL,
    created_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMPTZ,

    CONSTRAINT pk_block_once PRIMARY KEY (id),
    CONSTRAINT uq_block_once_start_at_end_at UNIQUE (starts_at, ends_at),
    CONSTRAINT ck_block_once_start_before_end CHECK ( starts_at < ends_at )
);


CREATE TABLE block_recurring
(
    id                  UUID            NOT NULL,
    name                VARCHAR(200)    NOT NULL,
    description         TEXT,
    starts_at           TIME            NOT NULL,
    ends_at             TIME            NOT NULL,
    ends_on_next_day    BOOLEAN         NOT NULL    DEFAULT FALSE,
    created_at          TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMPTZ,

    CONSTRAINT pk_block_recurring PRIMARY KEY (id),
    CONSTRAINT uq_block_recurring_start_at_end_at UNIQUE (starts_at, ends_at),
    CONSTRAINT ck_block_recurring_same_day_time_range CHECK ( ends_on_next_day OR starts_at < ends_at  )
);


CREATE TABLE day_of_week(
    value   SMALLINT,
    name    VARCHAR(10),

    CONSTRAINT pk_day_of_week PRIMARY KEY (value),
    CONSTRAINT uq_day_of_week UNIQUE (name)
);


CREATE TABLE block_recurring_day(
    block_recurring_id  UUID        NOT NULl,
    day_of_week_id      SMALLINT    NOT NULL,

    CONSTRAINT pk_block_recurring_day PRIMARY KEY (block_recurring_id, day_of_week_id),
    CONSTRAINT fk_block_recurring_day_block_recurring FOREIGN KEY (block_recurring_id) REFERENCES block_recurring (id) ON DELETE RESTRICT,
    CONSTRAINT fk_block_recurring_day_day_of_week FOREIGN KEY (day_of_week_id) REFERENCES day_of_week(value) ON DELETE RESTRICT
);