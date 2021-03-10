CREATE TABLE "User"
(
    UsrId       SERIAL primary key,
    firstName   VARCHAR(50),
    lastName    VARCHAR(50),
    phoneNumber VARCHAR(10)
);

CREATE TABLE "Order"
(
    OrdId       SERIAL primary key,
    UsrId       VARCHAR(10),
    RestId      VARCHAR(10),
    TblId       VARCHAR(10),
    OrdDate     DATE,
    OrdTime     TIME,
    Guests      INTEGER,
    OrdSts      VARCHAR(1),
    CONSTRAINT fk_User
        FOREIGN KEY(UsrId)
            REFERENCES "User"(UsrId),
    CONSTRAINT fk_Restaurant
        FOREIGN KEY(RestId)
            REFERENCES Restaurant(RestId),
    CONSTRAINT fk_Table
        FOREIGN KEY(TblId)
            REFERENCES Rest_Table(TblId)
);

CREATE TABLE Restaurant
(
    RestId       SERIAL primary key,
    RestNo       INTEGER,
    RestArea     VARCHAR(15),
    WorkTimeSt    TIME,
    WorkTimeEn    TIME
);

CREATE TABLE Rest_Table
(
    TblId       SERIAL primary key,
    RestId      VARCHAR(10),
    TblNo       INTEGER,
    SeatsNo     INTEGER,
    MinGuests   INTEGER,
    CONSTRAINT fk_Restaurant
        FOREIGN KEY(RestId)
            REFERENCES Restaurant(RestId)
);

INSERT INTO "User"(firstName, lastName, phoneNumber)
VALUES ('Lena', 'Kurylo', '0547941740');

SELECT *
FROM "User"