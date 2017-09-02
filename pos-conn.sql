USE pos;

CREATE TABLE Services(
	Service varchar(50) not null,
    Price decimal not null
);

INSERT INTO Services(Service, Price)
VALUES('Manicure', 15.00);
INSERT INTO Services(Service, Price)
VALUES('Pedicure', 28.00);
INSERT INTO Services(Service, Price)
VALUES('Mani/Pedi', 35.00);
INSERT INTO Services(Service, Price)
VALUES('Pink & White Full', 50.00);
INSERT INTO Services(Service, Price)
VALUES('Pink & White Fill-In', 35.00);
INSERT INTO Services(Service, Price)
VALUES('Nail Repair', 3.00);
INSERT INTO Services(Service, Price)
VALUES('Nails Polish Change', 8.00);
INSERT INTO Services(Service, Price)
VALUES('Toes Polish Change', 10.00);
INSERT INTO Services(Service, Price)
VALUES('No Chip Mani', 35.00);
INSERT INTO Services(Service, Price)
VALUES('No Chip Pedi', 50.00);
INSERT INTO Services(Service, Price)
VALUES('Gel Full-Set', 38.00);
INSERT INTO Services(Service, Price)
VALUES('Gel Fill-In', 25.00);
INSERT INTO Services(Service, Price)
VALUES('Acrylic Full-Set', 28.00);
INSERT INTO Services(Service, Price)
VALUES('Acrylic Fill-In', 20.00);

SELECT * FROM Services;

CREATE TABLE Customers(
	custId int(3) not null,
	lastName varchar(50) not null,
	firstName varchar(50) not null,
    PRIMARY KEY (custId)
);

CREATE TABLE Users(
	userId int(3) not null,
	lastName varchar(50) not null,
	firstName varchar(50) not null,
    password varchar(50) not null,
    PRIMARY KEY (userId)
);

SELECT * FROM Customers;

Drop Table Customers;