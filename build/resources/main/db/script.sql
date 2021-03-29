
CREATE TYPE Reserve_Status AS ENUM ('Approved','Waiting','Deleted');
CREATE TYPE Rest_Area AS ENUM ('NORTH','HAIFA','TELAVIV','ASHDOD_ASHKELON','SOUTH','EILAT');
CREATE TABLE IF NOT EXISTS  "User"
(
    Id          SERIAL PRIMARY KEY,
    firstName   VARCHAR(50),
    lastName    VARCHAR(50),
    phoneNumber VARCHAR(10),
    password    VARCHAR(8) UNIQUE,
    role        VARCHAR(8)
);


CREATE TABLE IF NOT EXISTS Restaurant
(
    RestId        SERIAL PRIMARY KEY,
    RestName      VARCHAR(100),
    RestArea      Rest_Area,
    WorkTimeStart TIME,
    WorkTimeEnd   TIME,
    Address  Varchar(150) ,
    ImageURL  VARCHAR(500),
    WebSite VARCHAR(500),
    PhoneNo varchar(10)

);

CREATE TABLE IF NOT EXISTS Rest_Table
(
    TblId   SERIAL PRIMARY KEY,
    RestId  INTEGER,
    TblNo   INTEGER,
    SeatsNo INTEGER,
    CONSTRAINT fk_Restaurant
        FOREIGN KEY (RestId)
            REFERENCES Restaurant (RestId)
);

CREATE TABLE IF NOT EXISTS Rest_Table_Dynamic
(
    TblId       SERIAL PRIMARY KEY,
    RestId      INTEGER,
    TblNo       INTEGER,
    SeatsNo     INTEGER,
    CurrentDate DATE,
    StartTime   TIME,
    EndTime     TIME,
    CONSTRAINT fk_Restaurant
        FOREIGN KEY (RestId)
            REFERENCES Restaurant (RestId)
);


CREATE TABLE IF NOT EXISTS Reserve
(
    Id   SERIAL PRIMARY KEY,
    UsrId   INTEGER,
    RestId  INTEGER,
    TblId   INTEGER,
    ReserveDate DATE,
    ReserveTime TIME,
    Guests  INTEGER,
    Status  Reserve_Status,
    CONSTRAINT fk_User
        FOREIGN KEY (UsrId)
            REFERENCES "User" (Id),
    CONSTRAINT fk_Restaurant
        FOREIGN KEY (RestId)
            REFERENCES Restaurant (RestId),
    CONSTRAINT fk_Table
        FOREIGN KEY (TblId)
            REFERENCES Rest_Table (TblId)
);


-- INSERT INTO "User"(firstName, lastName, phoneNumber)
-- VALUES ('Lena', 'Kurylo', '0547941740');
