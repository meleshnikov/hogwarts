-- liquibase formatted sql

-- changeset andrew:1
CREATE INDEX student_name_idx ON student (name);

-- changeset andrew:2
CREATE INDEX faculty_name_color_idx ON faculty (name, color);