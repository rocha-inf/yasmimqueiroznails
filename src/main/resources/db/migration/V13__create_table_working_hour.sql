CREATE TYPE day_of_week AS ENUM(
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY',
    'SATURDAY',
    'SUNDAY'
    );

CREATE TABLE working_hour(
    id              UUID            NOT NULL,
    day_of_week     day_of_week     NOT NULL,
    starts_at       TIME            NOT NULL,
    ends_at         TIME            NOT NULL,
    created_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ     NOT NULL    DEFAULT CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMPTZ,

    CONSTRAINT pk_working_hour PRIMARY KEY (id),
    CONSTRAINT uq_working_hour UNIQUE (starts_at, ends_at, day_of_week),
    CONSTRAINT ck_working_hour_start_before_end CHECK ( starts_at < ends_at )
);