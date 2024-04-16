CREATE TABLE Building (
    buildingID NVARCHAR(255) PRIMARY KEY,
    name NVARCHAR(255),
    city NVARCHAR(255),
    district NVARCHAR(255),
    address NVARCHAR(255),
    numberOfApartment INT,
    opex DECIMAL(30, 2)
);

CREATE TABLE BuildingManager (
    buildingManagerID INT PRIMARY KEY,
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

CREATE TABLE FinancialReport (
    financialReportID INT PRIMARY KEY,
    buildingID NVARCHAR(255),
    buildingManagerID INT,
    Date DATE,
    monthlyRevenue DECIMAL(20, 2),
    monthlyOpex DECIMAL(20, 2),
    monthlyProfit DECIMAL(20, 2),
    FOREIGN KEY (buildingID) REFERENCES Building(buildingID),
    FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID)
);

CREATE TABLE Tenant (
    tenantID INT PRIMARY KEY,
    lastName NVARCHAR(255),
    firstName NVARCHAR(255),
    phoneNumber NVARCHAR(255),
    dob DATE,
    gender NVARCHAR(255),
    citizenIdentityCard NVARCHAR(255)
);

CREATE TABLE Apartment (
    apartmentID NVARCHAR(255) PRIMARY KEY,
    buildingID NVARCHAR(255),
    roomNumber NVARCHAR(255),
    Area NVARCHAR(255),
    bedrooms INT,
    bathrooms INT,
    furniture NVARCHAR(255),
    FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
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
    LeaseTerm NVARCHAR(255),
    deposit NVARCHAR(255),
    monthlyRent NVARCHAR(255),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID),
    FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID),
    FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE Furniture (
    furnitureID NVARCHAR(255) PRIMARY KEY,
    apartmentID NVARCHAR(255),
    name NVARCHAR(255),
    condition NVARCHAR(255),
    price DECIMAL(20, 2),
    FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE MonthlyRentBill (
    monthlyRentBillID INT PRIMARY KEY,
    apartmentID NVARCHAR(255),
    tenantID INT,
    leaseAgreementID INT,
    date DATE,
    repaymentPeriod INT,
    totalPayment NVARCHAR(255),
    status NVARCHAR(255),
    FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID),
    FOREIGN KEY (leaseAgreementID) REFERENCES LeaseAgreement(leaseAgreementID)
);

CREATE TABLE ServiceUsage (
    serviceID INT PRIMARY KEY,
    monthlyRentBillID INT,
    name NVARCHAR(255),
    quantity NVARCHAR(255),
    pricePerUnit NVARCHAR(255),
    unit NVARCHAR(255),
    totalAmount NVARCHAR(255),
    Date DATE,
    note TEXT,
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);

CREATE TABLE ServiceTicket (
    serviceID INT,
    monthlyRentBillID INT,
    quantity DECIMAL(10, 2),
    totalAmount DECIMAL(10, 2),
    Date DATE,
    Note VARCHAR(255),
    PRIMARY KEY (serviceID, monthlyRentBillID),
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
    FOREIGN KEY (serviceID) REFERENCES ServiceUsage(serviceID)
);

CREATE TABLE Violation (
    violationID INT PRIMARY KEY,
    monthlyRentBillID INT,
    name NVARCHAR(255),
    totalAmount NVARCHAR(255),
    note TEXT,
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);

CREATE TABLE ViolationTicket (
    violationID INT,
    monthlyRentBillID INT,
    name VARCHAR(255),
    totalAmount DECIMAL(10, 2),
    Date DATE,
    note TEXT,
    PRIMARY KEY (violationID, monthlyRentBillID),
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
    FOREIGN KEY (violationID) REFERENCES Violation(violationID)
);

CREATE TABLE TaiKhoan (
    id INT PRIMARY KEY,
    username NVARCHAR(255),
    password NVARCHAR(255),
    loai VARCHAR(255)
);

INSERT INTO Building (buildingID, name, city, district, address, numberOfApartment, opex)
VALUES 
    ('B001', 'Tòa nhà A', 'Thành phố Hồ Chí Minh', 'Quận 1', '123 Đường ABC', 50, 10000.00),
    ('B002', 'Tòa nhà B', 'Thành phố Hà Nội', 'Quận Ba Đình', '456 Đường XYZ', 40, 12000.00),
    ('B003', 'Tòa nhà C', 'Thành phố Đà Nẵng', 'Quận Hải Châu', '789 Đường PQR', 30, 8000.00),
    ('B004', 'Tòa nhà D', 'Thành phố Hồ Chí Minh', 'Quận 7', '246 Đường MNO', 60, 15000.00),
    ('B005', 'Tòa nhà E', 'Thành phố Hà Nội', 'Quận Hai Bà Trưng', '135 Đường UVW', 35, 9000.00);

INSERT INTO BuildingManager (buildingManagerID, buildingID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard, position, salary)
VALUES 
    (1, 'B001', 'Nguyễn', 'Văn A', '0123456789', '1980-01-15', 'Nam', '123456789', 'Quản lý', 2000.00),
    (2, 'B002', 'Trần', 'Thị B', '0987654321', '1985-05-20', 'Nữ', '987654321', 'Trợ lý', 1500.00),
    (3, 'B003', 'Lê', 'Văn C', '0365987412', '1976-11-10', 'Nam', '456123789', 'Giám sát', 1800.00),
    (4, 'B004', 'Phạm', 'Thị D', '0912345678', '1990-09-25', 'Nữ', '369852147', 'Quản lý', 2200.00),
    (5, 'B005', 'Hoàng', 'Văn E', '0789456123', '1988-03-18', 'Nam', '987123654', 'Giám sát', 1900.00);

INSERT INTO FinancialReport (financialReportID, buildingID, buildingManagerID, Date, monthlyRevenue, monthlyOpex, monthlyProfit)
VALUES 
    (101, 'B001', 1, '2024-03-01', 50000.00, 20000.00, 30000.00),
    (102, 'B002', 2, '2024-03-01', 45000.00, 18000.00, 27000.00),
    (103, 'B003', 3, '2024-03-01', 30000.00, 15000.00, 15000.00),
    (104, 'B004', 4, '2024-03-01', 60000.00, 22000.00, 38000.00),
    (105, 'B005', 5, '2024-03-01', 40000.00, 16000.00, 24000.00);

INSERT INTO Tenant (tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard)
VALUES 
    (1001, 'Nguyễn', 'Văn An', '0123456789', '1990-05-10', 'Nam', '0123456789'),
    (1002, 'Trần', 'Thị Bình', '0987654321', '1995-03-20', 'Nữ', '9876543210'),
    (1003, 'Lê', 'Thị Cúc', '0365987412', '1988-11-15', 'Nữ', '4561237890'),
    (1004, 'Phạm', 'Văn Đức', '0912345678', '1992-08-25', 'Nam', '3698521470'),
    (1005, 'Hoàng', 'Thị Dung', '0789456123', '1994-06-18', 'Nữ', '9871236540');

INSERT INTO Apartment (apartmentID, buildingID, roomNumber, Area, bedrooms, bathrooms, furniture)
VALUES 
    ('APT001', 'B001', '101', '50m²', 1, 1, 'Basic'),
    ('APT002', 'B001', '102', '60m²', 2, 1, 'Standard'),
    ('APT003', 'B002', '201', '70m²', 2, 2, 'Deluxe'),
    ('APT004', 'B002', '202', '80m²', 3, 2, 'Premium'),
    ('APT005', 'B003', '301', '60m²', 2, 1, 'Standard');

INSERT INTO Cohabitant (cohabitantID, tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard)
VALUES 
    (2001, 1001, 'Nguyễn', 'Thị Bích', '0123456789', '1992-03-10', 'Nữ', '0123456789'),
    (2002, 1002, 'Trần', 'Văn Bình', '0987654321', '1993-08-20', 'Nam', '9876543210'),
    (2003, 1003, 'Lê', 'Văn Đại', '0365987412', '1994-06-15', 'Nam', '4561237890'),
    (2004, 1004, 'Phạm', 'Thị Đào', '0912345678', '1991-05-25', 'Nữ', '3698521470'),
    (2005, 1005, 'Hoàng', 'Văn Dũng', '0789456123', '1990-11-18', 'Nam', '9871236540');

INSERT INTO LeaseAgreement (leaseAgreementID, tenantID, apartmentID, buildingManagerID, signingDate, LeaseStartDate, LeaseEndDate, LeaseTerm, deposit, monthlyRent)
VALUES 
    (301, 1001, 'APT001', 1, '2024-03-10', '2024-04-01', '2025-04-01', '12 months', '10000000 VND', '5000000 VND'),
    (302, 1002, 'APT002', 2, '2024-03-15', '2024-04-01', '2024-10-01', '6 months', '8000000 VND', '3000000 VND'),
    (303, 1003, 'APT003', 3, '2024-03-20', '2024-04-01', '2025-01-01', '9 months', '12000000 VND', '6000000 VND'),
    (304, 1004, 'APT004', 4, '2024-03-25', '2024-04-01', '2024-12-01', '8 months', '15000000 VND', '7000000 VND'),
    (305, 1005, 'APT005', 5, '2024-03-30', '2024-04-01', '2025-03-01', '12 months', '11000000 VND', '5500000 VND');

INSERT INTO Furniture (furnitureID, apartmentID, name, condition, price)
VALUES 
    ('F001', 'APT001', 'Bàn', 'Mới', 1000000.00),
    ('F002', 'APT002', 'Ghế', 'Mới', 800000.00),
    ('F003', 'APT003', 'Tủ', 'Cũ', 1200000.00),
    ('F004', 'APT004', 'Giường', 'Cũ', 1500000.00),
    ('F005', 'APT005', 'Gối', 'Mới', 500000.00);

INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status)
VALUES 
    (401, 'APT001', 1001, 301, '2024-04-01', 1, '5000000 VND', 'Paid'),
    (402, 'APT002', 1002, 302, '2024-04-01', 1, '3000000 VND', 'Paid'),
    (403, 'APT003', 1003, 303, '2024-04-01', 1, '6000000 VND', 'Pending'),
    (404, 'APT004', 1004, 304, '2024-04-01', 1, '7000000 VND', 'Unpaid'),
    (405, 'APT005', 1005, 305, '2024-04-01', 1, '5500000 VND', 'Paid');

INSERT INTO ServiceUsage (serviceID, monthlyRentBillID, name, quantity, pricePerUnit, unit, totalAmount, Date, note)
VALUES 
    (501, 401, 'Electricity', '100', '500', 'kWh', '500000 VND', '2024-04-10', 'Included in rent'),
    (502, 402, 'Water', '50', '2000', 'liters', '100000 VND', '2024-04-10', 'Included in rent'),
    (503, 403, 'Internet', '1', '200000', 'month', '200000 VND', '2024-04-10', 'Monthly subscription'),
    (504, 404, 'Gas', '10', '50000', 'kg', '500000 VND', '2024-04-10', 'Usage-based billing'),
    (505, 405, 'Cleaning', '1', '100000', 'month', '100000 VND', '2024-04-10', 'Monthly service');

INSERT INTO ServiceTicket (serviceID, monthlyRentBillID, quantity, totalAmount, Date, Note)
VALUES 
    (501, 401, 100, 500000.00, '2024-04-10', 'Resolved'),
    (502, 402, 50, 100000.00, '2024-04-10', 'Resolved'),
    (503, 403, 1, 200000.00, '2024-04-10', 'Open'),
    (504, 404, 10, 500000.00, '2024-04-10', 'Open'),
    (505, 405, 1, 100000.00, '2024-04-10', 'Resolved');

INSERT INTO Violation (violationID, monthlyRentBillID, name, totalAmount, note)
VALUES 
    (601, 401, 'Late Payment', '200000 VND', 'Payment overdue for 7 days'),
    (602, 402, 'Noise Complaint', '100000 VND', 'Multiple warnings issued'),
    (603, 403, 'Damage', '300000 VND', 'Broken window reported'),
    (604, 404, 'Late Payment', '200000 VND', 'Payment overdue for 5 days'),
    (605, 405, 'Late Payment', '200000 VND', 'Payment overdue for 10 days');

INSERT INTO ViolationTicket (violationID, monthlyRentBillID, name, totalAmount, Date, note)
VALUES 
    (601, 401, 'Late Payment', 200000.00, '2024-04-20', 'Resolved'),
    (602, 402, 'Noise Complaint', 100000.00, '2024-04-20', 'Open'),
    (603, 403, 'Damage', 300000.00, '2024-04-20', 'Resolved'),
    (604, 404, 'Late Payment', 200000.00, '2024-04-20', 'Open'),
    (605, 405, 'Late Payment', 200000.00, '2024-04-20', 'Open');