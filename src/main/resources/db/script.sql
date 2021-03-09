CREATE TABLE "User"
(
    UsrId       SERIAL primary key,
    firstName   VARCHAR(50),
    lastName    VARCHAR(50),
    phoneNumber VARCHAR(10)
);

CREATE TABLE Restaurant
(
    RestId       SERIAL primary key,
    RestNo       INTEGER,
    RestArea     VARCHAR(15),
    WorkTimeStart   TIME,
    WorkTimeEnd     TIME
);

CREATE TABLE Rest_Table
(
    TblId       SERIAL primary key,
    RestId      INTEGER,
    TblNo       INTEGER,
    SeatsNo     INTEGER,
    CONSTRAINT fk_Restaurant
        FOREIGN KEY(RestId)
            REFERENCES Restaurant(RestId)
);

CREATE TABLE Rest_Table_Dynamic
(
    TblId       SERIAL primary key,
    RestId      INTEGER,
    TblNo       INTEGER,
    SeatsNo     INTEGER,
    CurrentDate   DATE,
    StartTime     TIME,
    EndTime       TIME,
    CONSTRAINT fk_Restaurant
        FOREIGN KEY(RestId)
            REFERENCES Restaurant(RestId)
);

CREATE TYPE Order_Status AS ENUM
    ('Approved','Waiting','Deleted');

CREATE TABLE "Order"
(
    OrdId       SERIAL primary key,
    UsrId      INTEGER,
    RestId      INTEGER,
    TblId       INTEGER,
    OrdDate     DATE,
    OrdTime     TIME,
    Guests      INTEGER,
    OrdSts      Order_Status,
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


-- INSERT INTO "User"(firstName, lastName, phoneNumber)
-- VALUES ('Lena', 'Kurylo', '0547941740');
