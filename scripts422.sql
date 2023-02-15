CREATE TABLE people
(
    id      bigserial PRIMARY KEY,
    name    text,
    age     integer,
    license boolean,
    car_id  bigint REFERENCES cars (id)
);

CREATE TABLE cars
(
    id    bigserial PRIMARY KEY,
    brand text,
    model text,
    price money
);
