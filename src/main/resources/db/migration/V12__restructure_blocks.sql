DROP TABLE block_recurring_day;
DROP TABLE day_of_week;
DROP TABLE block_recurring;

ALTER TABLE block_once RENAME TO block;
ALTER TABLE block RENAME CONSTRAINT pk_block_once TO pk_block;
ALTER TABLE block RENAME CONSTRAINT uq_block_once_start_at_end_at TO uq_block_start_at_end_at;
ALTER TABLE block RENAME CONSTRAINT ck_block_once_start_before_end TO ck_block_start_before_end;