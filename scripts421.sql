ALTER TABLE student
    ADD CONSTRAINT valid_age CHECK ( age >= 16);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT unique_name UNIQUE (name);

ALTER TABLE faculty
    ADD CONSTRAINT unique_name_color UNIQUE (name, color);

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;
