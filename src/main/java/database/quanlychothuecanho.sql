

CREATE TABLE Building (
    buildingID NVARCHAR(255) PRIMARY KEY,
    name NVARCHAR(255),
    city NVARCHAR(255),
    district NVARCHAR(255),
    address NVARCHAR(255),
    numberOfApartment INT,
    opex DECIMAL(30, 2)
);

CREATE TABLE Staff (
    staffID INT PRIMARY KEY,
    buildingID NVARCHAR(255),
    lastName NVARCHAR(255),
    firstName NVARCHAR(255),
    phoneNumber NVARCHAR(255),
    dob DATE,
    gender NVARCHAR(10), 
    citizenIdentityCard NVARCHAR(255),
    position NVARCHAR(255),
    salary DECIMAL(20, 2),
    FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
);

create table Tenant (
    tenantID INT PRIMARY KEY,
    lastName VARCHAR(255),
    firstName VARCHAR(255),
    phoneNumber VARCHAR(255),
    dob DATE,
    gender varchar(255),
    citizenIdentityCard VARCHAR(255)
)



CREATE TABLE ManagementContract (
    managementContractID INT  PRIMARY KEY,
    buildingManagerID INT,
    signingDate DATE,
    LeaseStartDate DATE,
    LeaseEndDate DATE,
    FOREIGN KEY (buildingManagerID) REFERENCES Staff(staffID)
);
CREATE TABLE Apartment (
    apartmentID NVARCHAR(255) PRIMARY KEY,
    buildingID NVARCHAR(255),
    roomNumber VARCHAR(255),
    Area nvarchar(255),
    bedrooms INT,
    bathrooms INT,
    furniture nvarchar(255),
    FOREIGN KEY (buildingID) REFERENCES Building (buildingID)

);

CREATE TABLE Cohabitant (
    cohabitantID INT PRIMARY KEY,
    tenantID INT,
    lastName NVARCHAR(255),
    firstName NVARCHAR(255),
    phoneNumber NVARCHAR(255),
    dob DATE,
    gender NVARCHAR(10),
    citizenIdentityCard NVARCHAR(255),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE LeaseAgreement (
    leaseAgreementID INT PRIMARY KEY,
    tenantID INT,
	apartmentID NVARCHAR(255),
    buildingManagerID INT,
    signingDate DATE,
    LeaseStartDate DATE,
    LeaseEndDate DATE,
    LeaseTerm nvarchar(255),
    deposit nvarchar(255),
    monthlyRent nvarchar(255),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID),
    FOREIGN KEY (buildingManagerID) REFERENCES Staff(staffID),
	 FOREIGN KEY (apartmentID) REFERENCES Apartment (apartmentID)
);

CREATE TABLE Furniture (
    furnitureID nVARCHAR(255) PRIMARY KEY,
    apartmentID nVARCHAR(255),
    name nVARCHAR(255),
    condition nVARCHAR(255),
    price DECIMAL(20, 2),
    FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE MonthlyRentBill (
    monthlyRentBillID INT  PRIMARY KEY,
    apartmentID nVARCHAR(255),
    tenantID INT,
    leaseAgreementID INT,
    date DATE,
    repaymentPeriod INT,
    totalPayment nvarchar(255),
    status nvarchar(255),
    FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID),
    FOREIGN KEY (leaseAgreementID) REFERENCES LeaseAgreement(leaseAgreementID)
);
CREATE TABLE ServiceUsage (
    serviceID INT  PRIMARY KEY,
    monthlyRentBillID INT,
    name nVARCHAR(255),
    quantity nvarchar(255),
    pricePerUnit   nvarchar(255),
    unit nVARCHAR(255),
    totalAmount nvarchar(255),
    Date DATE,
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);
CREATE TABLE Violation (
    violationID INT  PRIMARY KEY,
    monthlyRentBillID INT,
    name nVARCHAR(255),
    totalAmount nVARCHAR(255),
    note TEXT,
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);


INSERT INTO Building (buildingID, name, city, district, address, numberOfApartment, opex) 
VALUES 
('B1', 'Building 1', 'City A', 'District X', '123 Main Street', 10, 10000.00),
('B2', 'Building 2', 'City B', 'District Y', '456 Elm Street', 15, 15000.00),
('B3', 'Building 3', 'City C', 'District Z', '789 Oak Street', 20, 20000.00),
('B4', 'Building 4', 'City D', 'District W', '321 Pine Street', 12, 12000.00);



INSERT INTO Staff (staffID, buildingID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard, position, salary) 
VALUES 
(1, 'B1', 'Smith', 'John', '123-456-7890', '1990-05-15', 'male', '1234567890', 'Manager', 50000.00),
(2, 'B2', 'Doe', 'Jane', '987-654-3210', '1995-08-20', 'female', '0987654321', 'Assistant', 40000.00),
(3, 'B3', 'Johnson', 'Michael', '456-789-0123', '1988-12-10', 'male', '4567890123', 'Janitor', 30000.00),
(4, 'B4', 'Williams', 'Emily', '789-012-3456', '1992-03-25', 'female', '7890123456', 'Security', 35000.00);

INSERT INTO Tenant (tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard) 
VALUES 
(1, 'Brown', 'David', '111-222-3333', '1987-07-20', 'male', '1112223333'),
(2, 'Wilson', 'Emily', '444-555-6666', '1990-12-15', 'female', '4445556666'),
(3, 'Anderson', 'Chris', '777-888-9999', '1985-05-10', 'male', '7778889999'),
(4, 'Martinez', 'Maria', '000-999-8888', '1993-09-30', 'female', '0009998888');


INSERT INTO ManagementContract (managementContractID, buildingManagerID, signingDate, LeaseStartDate, LeaseEndDate) 
VALUES 
(1, 1, '2023-01-01', '2023-02-01', '2024-02-01'),
(2, 2, '2023-02-01', '2023-03-01', '2024-03-01'),
(3, 3, '2023-03-01', '2023-04-01', '2024-04-01'),
(4, 4, '2023-04-01', '2023-05-01', '2024-05-01');


INSERT INTO Apartment (apartmentID, buildingID, roomNumber, Area, bedrooms, bathrooms, furniture) 
VALUES 
('A1', 'B1', '101', '100', 2, 1, 'full'),
('A2', 'B1', '102', '110', 3, 2, 'basis'),
('A3', 'B2', '201', '120', 2, 1, 'no'),
('A4', 'B2', '202', '90', 1, 1, 'full');

INSERT INTO Cohabitant (cohabitantID, tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard) 
VALUES 
(1, 1, 'Lee', 'Sarah', '555-666-7777', '1992-08-25', 'female', '5556667777'),
(2, 2, 'Garcia', 'Jose', '666-777-8888', '1991-11-10', 'male', '6667778888'),
(3, 3, 'Nguyen', 'Linh', '777-888-9999', '1990-05-05', 'female', '7778889999'),
(4, 4, 'Chen', 'Wei', '888-999-0000', '1988-12-20', 'male', '8889990000');

INSERT INTO LeaseAgreement (leaseAgreementID, tenantID, apartmentID, buildingManagerID, signingDate, LeaseStartDate, LeaseEndDate, LeaseTerm, deposit, monthlyRent) 
VALUES 
(1, 1, 'A1', 1, '2023-01-01', '2023-02-01', '2024-02-01', '12', '2000', '1000'),
(2, 2, 'A2', 2, '2023-02-01', '2023-03-01', '2024-03-01', '12', '1500', '1200'),
(3, 3, 'A3', 3, '2023-03-01', '2023-04-01', '2024-04-01', '12', '1800', '1400'),
(4, 4, 'A4', 4, '2023-04-01', '2023-05-01', '2024-05-01', '12', '2200', '1800');


INSERT INTO Furniture (furnitureID, apartmentID, name, condition, price) 
VALUES 
('F1', 'A1', 'Sofa', 'Good', 500.00),
('F2', 'A2', 'Bed', 'Excellent', 800.00),
('F3', 'A3', 'Table', 'Fair', 300.00),
('F4', 'A4', 'Chair', 'Excellent', 100.00);


INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status) 
VALUES 
(1, 'A1', 1, 1, '2023-01-01', 30, '1000', 'current'),
(2, 'A2', 2, 2, '2023-02-01', 30, '1200', 'current'),
(3, 'A3', 3, 3, '2023-03-01', 30, '1400', 'current'),
(4, 'A4', 4, 4, '2023-04-01', 30, '1800', 'current');


INSERT INTO ServiceUsage (serviceID, monthlyRentBillID, name, quantity, pricePerUnit, unit, totalAmount, Date) 
VALUES 
(1, 1, 'Electricity', '100', '0.15', 'kWh', '15.00', '2023-01-15'),
(2, 2, 'Water', '50', '0.10', 'm3', '5.00', '2023-02-15'),
(3, 3, 'Internet', '1', '50', 'Mbps', '50.00', '2023-03-15'),
(4, 4, 'Cleaning', '2', '30', 'hours', '60.00', '2023-04-15');


INSERT INTO Violation (violationID, monthlyRentBillID, name, totalAmount, note) 
VALUES 
(1, 1, 'Noise disturbance', '50.00', 'Complaint from neighbors'),
(2, 2, 'Late payment fee', '20.00', 'Payment received after due date'),
(3, 3, 'Unauthorized pet', '100.00', 'Violation of pet policy'),
(4, 4, 'Parking violation', '30.00', 'Vehicle parked in unauthorized area');