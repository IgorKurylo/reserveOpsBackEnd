CREATE TYPE Reserve_Status AS ENUM ('Approved','Waiting','Deleted');
CREATE TYPE Rest_Area AS ENUM ('NORTH','HAIFA','TELAVIV','ASHDOD_ASHKELON','SOUTH','EILAT');
CREATE TABLE IF NOT EXISTS Users
(
    Id          SERIAL PRIMARY KEY,
    firstName   VARCHAR(50),
    lastName    VARCHAR(50),
    phoneNumber VARCHAR(10),
    role        VARCHAR(20)
);


CREATE TABLE IF NOT EXISTS Restaurant
(
    RestId        SERIAL PRIMARY KEY,
    RestName      VARCHAR(100),
    RestArea      Rest_Area,
    WorkTimeStart TIME,
    WorkTimeEnd   TIME,
    Address       Varchar(150),
    ImageURL      VARCHAR(500),
    WebSite       VARCHAR(500),
    PhoneNo       varchar(10)

);

CREATE TABLE IF NOT EXISTS Rest_Table
(
    TblId  INTEGER,
    CONSTRAINT fk_Tables
        FOREIGN KEY (TblId)
            REFERENCES Tables (Id),
    RestId INTEGER,
    CONSTRAINT fk_Restaurant
        FOREIGN KEY (RestId)
            REFERENCES Restaurant (RestId)

);

CREATE TABLE IF NOT EXISTS Rest_Table_Dynamic
(
    Id          SERIAL PRIMARY KEY,
    RestId      INTEGER,
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
    Id          SERIAL PRIMARY KEY,
    UsrId       INTEGER,
    RestId      INTEGER,
    TblId       INTEGER,
    ReserveDate DATE,
    ReserveTime TIME,
    Guests      INTEGER,
    Status      Reserve_Status,
    Comments    VARCHAR(500),
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

CREATE TABLE IF NOT EXISTS Tables
(
    Id    SERIAL PRIMARY KEY,
    TblNo INTEGER,
    Seats INTEGER
);
