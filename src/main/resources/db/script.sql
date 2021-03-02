CREATE TABLE "User"
(
    Id SERIAL primary key
);
CREATE TABLE Orders
(
    Id     SERIAL primary key,
    UserId INT,
    CONSTRAINT FK_User_Id
        FOREIGN KEY (UserId)
            REFERENCES "User"
)