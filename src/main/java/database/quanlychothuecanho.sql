CREATE TABLE Apartment (
                           apartmentID VARCHAR(50) NOT NULL,
                           buildingID VARCHAR(50) NOT NULL,
                           roomNumber VARCHAR(50) NOT NULL,
                           area float,
                           bedrooms INT,
                           bathrooms INT,
                           furniture VARCHAR(50),
                           PRIMARY KEY (apartmentID)
);

CREATE TABLE Building (
                          buildingID VARCHAR(50) NOT NULL,
                          nameBuilding VARCHAR(50) NOT NULL,
                          cityBuilding VARCHAR(50) NOT NULL,
                          district_Building VARCHAR(50) NOT NULL,
                          address_Building VARCHAR(50) NOT NULL,
                          numberOfApartment_Building INT,
                          PRIMARY KEY (buildingID)
);

CREATE TABLE buildingManager (
                                 buildingManagerId VARCHAR(50) NOT NULL,
                                 buildingId VARCHAR(50) NOT NULL,
                                 lastName_BuildingManager VARCHAR(50),
                                 firstName_BuildingManager VARCHAR(50),
                                 phoneNumber_BuildingManager VARCHAR(50),
                                 dateOfBirthDay DATE,
                                 gender_BuildingManager VARCHAR(50),
                                 citizenIdentityCard_BuildingManager VARCHAR(50),
                                 salary_BuildingManager float,
                                 PRIMARY KEY (buildingManagerId),
                                 FOREIGN KEY (buildingId) REFERENCES building(buildingID)
);

CREATE TABLE tenant (
                        tenantID VARCHAR(50) NOT NULL,
                        lastName VARCHAR(50),
                        firstName VARCHAR(50),
                        phoneNumber VARCHAR(50),
                        dateOfBirthDay DATE,
                        gender VARCHAR(50),
                        citizenIdentityCard VARCHAR(50),
                        PRIMARY KEY (tenantID)
);


CREATE TABLE cohabitant (
                            cohabitantID VARCHAR(50) NOT NULL,
                            tenantID VARCHAR(50) NOT NULL,
                            lastName VARCHAR(50),
                            firstName VARCHAR(50),
                            phoneNumber VARCHAR(50),
                            dateOfBirthDay DATE,
                            gender VARCHAR(50),
                            citizenIdentityCard VARCHAR(50),
                            PRIMARY KEY (cohabitantID),
                            FOREIGN KEY (tenantID) REFERENCES tenant(tenantID)
);

CREATE TABLE Furniture (
                           furnitureID VARCHAR(50) NOT NULL,
                           apartmentID VARCHAR(50) NOT NULL,
                           nameFurniture VARCHAR(50),
                           conditionFurniture VARCHAR(50),
                           price float,
                           PRIMARY KEY (furnitureID),
                           FOREIGN KEY (apartmentID) REFERENCES apartment(apartmentID)
);

CREATE TABLE LeaseAgreement(
                               leaseAgreementID VARCHAR(50) NOT NULL,
                               apartmentID VARCHAR(50) NOT NULL,
                               tenantID VARCHAR(50) NOT NULL,
                               buildingManagerID VARCHAR(50) NOT NULL,
                               signingDate DATE,
                               leaseStartDate DATE,
                               leaseEndDate DATE,
                               leaseTerm float,
                               deposit float,
                               monthlyRent float,
                               PRIMARY KEY (leaseAgreementID),
                               FOREIGN KEY (apartmentID) REFERENCES apartment(apartmentID),
                               FOREIGN KEY (tenantID) REFERENCES tenant(tenantID),
                               FOREIGN KEY (buildingManagerID) REFERENCES buildingManager(buildingManagerID)
);


CREATE TABLE ManagementContract (
                                    managementContract VARCHAR(50) NOT NULL,
                                    buildingManagerId_ManagementContract VARCHAR(50) NOT NULL,
                                    signingDate DATE,
                                    leaseStartDate DATE,
                                    leaseEndDate DATE,
                                    PRIMARY KEY (managementContract),
                                    FOREIGN KEY (buildingManagerId_ManagementContract) REFERENCES buildingManager(buildingManagerId)
);


CREATE TABLE violation (
                           violationID VARCHAR(50),
                           monthlyRentBillID VARCHAR(50),
                           name VARCHAR(50),
                           totalAmount FLOAT,
                           note VARCHAR(50)
);
CREATE TABLE monthlyrentbill (
                                 monthlyRentBillID VARCHAR(50),
                                 apartmentID VARCHAR(50),
                                 tenantID VARCHAR(50),
                                 leaseAgreementID VARCHAR(50),
                                 date DATE,
                                 repaymentPeriod INT,
                                 serviceBillID VARCHAR(50),
                                 violationID VARCHAR(50),
                                 totalPayment FLOAT,
                                 status VARCHAR(50)
);

CREATE TABLE account (
   tendangnhap varchar(50) NOT NULL DEFAULT '',
   matkhau varchar(255) NOT NULL,
   manhomquyen int(3) NOT NULL
);

INSERT INTO account (tendangnhap, matkhau, manhomquyen)
VALUES
('minh', '$2a$12$6GSkiQ05XjTRvCW9MB6MNuf7hOJEbbeQx11Eb8oELil1OrCq6uBXm', 2),
('nghia', '$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6', 0),
('muoi', '$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6', 0),
('nam', '$2a$12$SAlAhcsudMzNEouyBaoHnOKR23ixdH0ZkcoyXUJ5gS/NFt.b4oqw6', 1);


-- Chèn dữ liệu vào bảng Apartment
INSERT INTO Apartment (apartmentID, buildingID, roomNumber, area, bedrooms, bathrooms, furniture)
VALUES
    ('APT12', 'B2', '101', 80.0, 2, 1, 'Partially Furnished'),
    ('APT13', 'B3', '102', 85.5, 3, 2, 'Fully Furnished'),
    ('APT14', 'B4', '103', 90.0, 2, 1, 'Unfurnished'),
    ('APT15', 'B5', '104', 95.5, 2, 2, 'Partially Furnished'),
    ('APT16', 'B6', '105', 100.0, 3, 2, 'Fully Furnished'),
    ('APT17', 'B7', '106', 105.5, 2, 1, 'Unfurnished'),
    ('APT18', 'B8', '107', 110.0, 2, 2, 'Partially Furnished'),
    ('APT19', 'B9', '108', 115.5, 3, 2, 'Fully Furnished'),
    ('APT20', 'B10', '109', 120.0, 2, 1, 'Unfurnished'),
    ('APT21', 'B11', '110', 125.5, 2, 2, 'Partially Furnished');


INSERT INTO Building (buildingID, nameBuilding, cityBuilding, district_Building, address_Building, numberOfApartment_Building)
VALUES
    ('B2', 'Building B', 'City B', 'District B', '456 XYZ Street', 30),
    ('B3', 'Building C', 'City A', 'District A', '789 QRS Street', 40),
    ('B4', 'Building D', 'City C', 'District C', '101 LMN Street', 25),
    ('B5', 'Building E', 'City D', 'District D', '111 PQR Street', 35),
    ('B6', 'Building F', 'City B', 'District B', '222 UVW Street', 45),
    ('B7', 'Building G', 'City A', 'District A', '333 EFG Street', 20),
    ('B8', 'Building H', 'City C', 'District C', '444 HIJ Street', 15),
    ('B9', 'Building I', 'City D', 'District D', '555 RST Street', 50),
    ('B10', 'Building J', 'City A', 'District A', '666 MNO Street', 60),
    ('B11', 'Building K', 'City B', 'District B', '777 XYZ Street', 55);


INSERT INTO buildingManager (buildingManagerId, buildingId, lastName_BuildingManager, firstName_BuildingManager, phoneNumber_BuildingManager, dateOfBirthDay, gender_BuildingManager, citizenIdentityCard_BuildingManager, salary_BuildingManager)
VALUES
    ('BM2', 'B2', 'Johnson', 'Robert', '987654321', '1985-02-15', 'Male', '789ABC', 2500.00),
    ('BM3', 'B3', 'Williams', 'Mary', '123456789', '1978-08-20', 'Female', '456DEF', 2200.00),
    ('BM4', 'B4', 'Brown', 'Michael', '555555555', '1990-05-10', 'Male', '123GHI', 2300.00),
    ('BM5', 'B5', 'Jones', 'Jennifer', '777777777', '1982-11-30', 'Female', '789JKL', 2700.00),
    ('BM6', 'B6', 'Miller', 'David', '999999999', '1975-04-25', 'Male', '456MNO', 2600.00),
    ('BM7', 'B7', 'Davis', 'Sarah', '111111111', '1988-10-05', 'Female', '789PQR', 2400.00),
    ('BM8', 'B8', 'Garcia', 'Daniel', '333333333', '1980-07-12', 'Male', '123STU', 2800.00),
    ('BM9', 'B9', 'Rodriguez', 'Jessica', '222222222', '1983-03-18', 'Female', '456VWX', 2100.00),
    ('BM10', 'B10', 'Martinez', 'Christopher', '444444444', '1972-06-08', 'Male', '789YZA', 2900.00),
    ('BM11', 'B11', 'Hernandez', 'Kimberly', '666666666', '1977-09-22', 'Female', '123BCD', 3000.00);


INSERT INTO tenant (tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard)
VALUES
    ('T2', 'Taylor', 'William', '123123123', '1993-03-01', 'Male', '123EFG'),
    ('T3', 'Anderson', 'Emily', '456456456', '1996-07-10', 'Female', '456HIJ'),
    ('T4', 'Thomas', 'James', '789789789', '1990-11-15', 'Male', '789KLM'),
    ('T5', 'Jackson', 'Linda', '321321321', '1988-04-20', 'Female', '123NOP'),
    ('T6', 'White', 'Daniel', '654654654', '1994-09-05', 'Male', '456QRS'),
    ('T7', 'Harris', 'Karen', '987987987', '1985-01-30', 'Female', '789TUV'),
    ('T8', 'Martin', 'Eric', '147147147', '1992-06-25', 'Male', '123WXY'),
    ('T9', 'Thompson', 'Michelle', '258258258', '1983-08-12', 'Female', '456ZAB'),
    ('T10', 'Garcia', 'Matthew', '369369369', '1991-12-03', 'Male', '789CDE'),
    ('T11', 'Martinez', 'Amanda', '753753753', '1980-05-18', 'Female', '123FGH');


INSERT INTO cohabitant (cohabitantID, tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard)
VALUES
    ('C2', 'T2', 'Taylor', 'Sarah', '123123123', '1990-08-12', 'Female', '789IJK'),
    ('C3', 'T3', 'Anderson', 'Michael', '456456456', '1995-02-25', 'Male', '123LMN'),
    ('C4', 'T4', 'Thomas', 'Emily', '789789789', '1988-06-20', 'Female', '456OPQ'),
    ('C5', 'T5', 'Jackson', 'Jason', '321321321', '1992-09-15', 'Male', '789RST'),
    ('C6', 'T6', 'White', 'Rebecca', '654654654', '1987-12-30', 'Female', '123UVW'),
    ('C7', 'T7', 'Harris', 'Ryan', '987987987', '1993-04-05', 'Male', '456XYZ'),
    ('C8', 'T8', 'Martin', 'Nicole', '147147147', '1984-07-10', 'Female', '789ABC');


INSERT INTO Furniture (furnitureID, apartmentID, nameFurniture, conditionFurniture, price)
VALUES
    ('F2', 'APT12', 'Bed', 'Good', 300.00),
    ('F3', 'APT13', 'Desk', 'Fair', 150.00),
    ('F4', 'APT14', 'Chair', 'Excellent', 50.00),
    ('F5', 'APT15', 'Table', 'Fair', 200.00),
    ('F6', 'APT16', 'Lamp', 'Good', 30.00),
    ('F7', 'APT17', 'Wardrobe', 'Excellent', 400.00),
    ('F8', 'APT18', 'Couch', 'Fair', 350.00),
    ('F9', 'APT19', 'TV', 'Good', 500.00),
    ('F10', 'APT20', 'Dining Table', 'Excellent', 250.00),
    ('F11', 'APT21', 'Bookshelf', 'Good', 100.00);


INSERT INTO LeaseAgreement (leaseAgreementID, apartmentID, tenantID, buildingManagerID, signingDate, leaseStartDate, leaseEndDate, leaseTerm, deposit, monthlyRent)
VALUES
    ('LA2', 'APT12', 'T3', 'BM3', '2024-03-25', '2024-04-01', '2025-04-01', 12, 1000.00, 700.00),
    ('LA3', 'APT13', 'T4', 'BM4', '2024-03-26', '2024-04-01', '2025-04-01', 12, 1000.00, 750.00),
    ('LA4', 'APT14', 'T5', 'BM5', '2024-03-27', '2024-04-01', '2025-04-01', 12, 1000.00, 800.00),
    ('LA5', 'APT15', 'T6', 'BM6', '2024-03-28', '2024-04-01', '2025-04-01', 12, 1000.00, 850.00),
    ('LA6', 'APT16', 'T7', 'BM7', '2024-03-29', '2024-04-01', '2025-04-01', 12, 1000.00, 900.00),
    ('LA7', 'APT17', 'T8', 'BM8', '2024-03-30', '2024-04-01', '2025-04-01', 12, 1000.00, 950.00),
    ('LA8', 'APT18', 'T9', 'BM9', '2024-03-31', '2024-04-01', '2025-04-01', 12, 1000.00, 1000.00),
    ('LA9', 'APT19', 'T10', 'BM10', '2024-04-01', '2024-04-01', '2025-04-01', 12, 1000.00, 1050.00),
    ('LA10', 'APT20', 'T11', 'BM11', '2024-04-02', '2024-04-01', '2025-04-01', 12, 1000.00, 1100.00),
    ('LA11', 'APT21', 'T2', 'BM2', '2024-04-03', '2024-04-01', '2025-04-01', 12, 1000.00, 1150.00);



INSERT INTO ManagementContract (managementContract, buildingManagerId_ManagementContract, signingDate, leaseStartDate, leaseEndDate)
VALUES
    ('MC2', 'BM3', '2024-03-25', '2024-04-01', '2026-04-01'),
    ('MC3', 'BM4', '2024-03-26', '2024-04-01', '2026-04-01'),
    ('MC4', 'BM5', '2024-03-27', '2024-04-01', '2026-04-01'),
    ('MC5', 'BM6', '2024-03-28', '2024-04-01', '2026-04-01'),
    ('MC6', 'BM7', '2024-03-29', '2024-04-01', '2026-04-01'),
    ('MC7', 'BM8', '2024-03-30', '2024-04-01', '2026-04-01'),
    ('MC8', 'BM9', '2024-03-31', '2024-04-01', '2026-04-01'),
    ('MC9', 'BM10', '2024-04-01', '2024-04-01', '2026-04-01'),
    ('MC10', 'BM11', '2024-04-02', '2024-04-01', '2026-04-01'),
    ('MC11', 'BM2', '2024-04-03', '2024-04-01', '2026-04-01');
