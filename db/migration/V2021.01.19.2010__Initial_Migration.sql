CREATE TABLE service_company
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR(255),
    senior_capacity INT,
    junior_capacity INT
);

INSERT INTO service_company(name, senior_capacity, junior_capacity)
VALUES ('Service Company One', 10, 6),
       ('Service Company Two', 11, 6);

CREATE TABLE office
(
    id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(255),
    room_amount INT
);

INSERT INTO office(name, room_amount)
VALUES ('Office One', 35),
       ('Office Two', 21),
       ('Office Three', 17),
       ('Office Four', 24),
       ('Office Five', 28);
