CREATE TABLE Building (
<<<<<<< HEAD
    buildingID NVARCHAR(255) PRIMARY KEY,
    name NVARCHAR(255),
    city NVARCHAR(255),
    district NVARCHAR(255),
    address NVARCHAR(255),
    numberOfApartment INT,
  
);

CREATE TABLE BuildingManager (
    buildingManagerID NVARCHAR(255) PRIMARY KEY,
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
    financialReportID NVARCHAR(255) PRIMARY KEY,
    buildingID NVARCHAR(255),
    buildingManagerID NVARCHAR(255),
    Date DATE,
    monthlyRevenue DECIMAL(20, 2),
    monthlyOpex DECIMAL(20, 2),
    monthlyProfit DECIMAL(20, 2),
    FOREIGN KEY (buildingID) REFERENCES Building(buildingID),
    FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID)
);

CREATE TABLE Tenant (
    tenantID NVARCHAR(255) PRIMARY KEY,
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
    cohabitantID NVARCHAR(255) PRIMARY KEY,
    tenantID NVARCHAR(255),
    lastName NVARCHAR(255),
    firstName NVARCHAR(255),
    phoneNumber NVARCHAR(255),
    dob DATE,
    gender NVARCHAR(10),
    citizenIdentityCard NVARCHAR(255),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE LeaseAgreement (
    leaseAgreementID NVARCHAR(255) PRIMARY KEY,
    tenantID NVARCHAR(255),
    apartmentID NVARCHAR(255),
    buildingManagerID NVARCHAR(255),
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
    monthlyRentBillID NVARCHAR PRIMARY KEY,
    apartmentID NVARCHAR(255),
    tenantID NVARCHAR(255),
    date DATE,
    repaymentPeriod INT,
    totalPayment NVARCHAR(255),
    status NVARCHAR(255),
    FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID),
    FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE ServiceUsage (
    serviceID NVARCHAR PRIMARY KEY,
    monthlyRentBillID NVARCHAR(255),
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
    serviceID NVARCHAR(255),
    monthlyRentBillID nVARCHAR(255),
    quantity DECIMAL(10, 2),
    totalAmount DECIMAL(10, 2),
    Date DATE,
    Note VARCHAR(255),
    PRIMARY KEY (serviceID, monthlyRentBillID),
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
    FOREIGN KEY (serviceID) REFERENCES ServiceUsage(serviceID)
);

CREATE TABLE Violation (
    violationID NVARCHAR PRIMARY KEY,
    monthlyRentBillID NVARCHAR(255),
    name NVARCHAR(255),
    totalAmount NVARCHAR(255),
    note TEXT,
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);

CREATE TABLE ViolationTicket (
    violationID NVARCHAR(255),
    monthlyRentBillID NVARCHAR(255),
    name VARCHAR(255),
    totalAmount DECIMAL(10, 2),
    Date DATE,
    note TEXT,
    PRIMARY KEY (violationID, monthlyRentBillID),
    FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
    FOREIGN KEY (violationID) REFERENCES Violation(violationID)
);

CREATE TABLE TaiKhoan (
    id NVARCHAR(255) PRIMARY KEY,
    username NVARCHAR(255),
    password NVARCHAR(255),
    loai VARCHAR(255)
);
INSERT INTO Building (buildingID, name, city, district, address, numberOfApartment)
VALUES ('B1', N'Tòa nhà A', N'Hà Nội', N'Cầu Giấy', N'Số 123 Đường ABC', 50),
       ('B2', N'Tòa nhà B', N'Hồ Chí Minh', N'Quận 1', N'Số 456 Đường XYZ', 40),
       ('B3', N'Tòa nhà C', N'Đà Nẵng', N'Hải Châu', N'Số 789 Đường LMN', 30),
       ('B4', N'Tòa nhà D', N'Hải Phòng', N'Đồ Sơn', N'Số 321 Đường PQR', 20),
       ('B5', N'Tòa nhà E', N'Cần Thơ', N'Ninh Kiều', N'Số 555 Đường STU', 10);
INSERT INTO BuildingManager (buildingManagerID, buildingID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard, position, salary)
VALUES ('BM1', 'B1', N'Nguyễn', N'Văn A', '0123456789', '1980-01-01', N'Nam', '123456789', N'Quản lý', 15000000.00),
       ('BM2', 'B2', N'Trần', N'Thị B', '0987654321', '1985-05-10', N'Nữ', '987654321', N'Quản lý', 12000000.00),
       ('BM3', 'B3', N'Lê', N'Văn C', '0369852147', '1990-12-20', N'Nam', '654789321', N'Quản lý', 13500000.00),
       ('BM4', 'B4', N'Phạm', N'Thị D', '0932154789', '1982-08-15', N'Nữ', '852963147', N'Quản lý', 14000000.00),
       ('BM5', 'B5', N'Hồ', N'Văn E', '0789456123', '1988-04-30', N'Nam', '369852147', N'Quản lý', 13000000.00);
INSERT INTO FinancialReport (financialReportID, buildingID, buildingManagerID, Date, monthlyRevenue, monthlyOpex, monthlyProfit)
VALUES ('FR1', 'B1', 'BM1', '2024-03-31', 50000000.00, 30000000.00, 20000000.00),
       ('FR2', 'B2', 'BM2', '2024-03-31', 60000000.00, 35000000.00, 25000000.00),
       ('FR3', 'B3', 'BM3', '2024-03-31', 70000000.00, 40000000.00, 30000000.00),
       ('FR4', 'B4', 'BM4', '2024-03-31', 55000000.00, 32000000.00, 23000000.00),
       ('FR5', 'B5', 'BM5', '2024-03-31', 65000000.00, 38000000.00, 27000000.00);
INSERT INTO Tenant (tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard)
VALUES ('T1', N'Nguyễn', N'Thị A', '0123456789', '1990-01-10', N'Nữ', '123456789'),
       ('T2', N'Trần', N'Văn B', '0987654321', '1995-05-20', N'Nam', '987654321'),
       ('T3', N'Lê', N'Thị C', '0369852147', '1988-12-15', N'Nữ', '654789321'),
       ('T4', N'Phạm', N'Văn D', '0932154789', '1992-07-25', N'Nam', '852963147'),
       ('T5', N'Hồ', N'Thị E', '0789456123', '1997-03-05', N'Nữ', '369852147');
INSERT INTO Apartment (apartmentID, buildingID, roomNumber, Area, bedrooms, bathrooms, furniture)
VALUES ('APT1', 'B1', '101', N'50m²', 1, 1, N'Cơ bản'),
       ('APT2', 'B1', '102', N'60m²', 2, 1, N'Tiện nghi'),
       ('APT3', 'B2', '201', N'55m²', 1, 1, N'Cơ bản'),
       ('APT4', 'B2', '202', N'65m²', 2, 1, N'Tiện nghi'),
       ('APT5', 'B3', '301', N'70m²', 2, 2, N'Cao cấp');
INSERT INTO Cohabitant (cohabitantID, tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard)
VALUES ('CH1', 'T1', N'Nguyễn', N'Thị A', '0123456789', '1990-01-10', N'Nữ', '123456789'),
       ('CH2', 'T1', N'Trần', N'Văn B', '0987654321', '1995-05-20', N'Nam', '987654321'),
       ('CH3', 'T2', N'Lê', N'Thị C', '0369852147', '1988-12-15', N'Nữ', '654789321'),
       ('CH4', 'T3', N'Phạm', N'Văn D', '0932154789', '1992-07-25', N'Nam', '852963147'),
       ('CH5', 'T4', N'Hồ', N'Thị E', '0789456123', '1997-03-05', N'Nữ', '369852147');
INSERT INTO LeaseAgreement (leaseAgreementID, tenantID, apartmentID, buildingManagerID, signingDate, LeaseStartDate, LeaseEndDate, LeaseTerm, deposit, monthlyRent)
VALUES ('LA1', 'T1', 'APT1', 'BM1', '2024-04-01', '2024-04-15', '2025-04-15', N'1 năm', '10000000', '2000000'),
       ('LA2', 'T2', 'APT2', 'BM2', '2024-04-01', '2024-04-15', '2025-04-15', N'1 năm', '11000000', '2100000'),
       ('LA3', 'T3', 'APT3', 'BM3', '2024-04-01', '2024-04-15', '2025-04-15', N'1 năm', '12000000', '2200000'),
       ('LA4', 'T4', 'APT4', 'BM4', '2024-04-01', '2024-04-15', '2025-04-15', N'1 năm', '13000000', '2300000'),
       ('LA5', 'T5', 'APT5', 'BM5', '2024-04-01', '2024-04-15', '2025-04-15', N'1 năm', '14000000', '2400000');
-- Dữ liệu cho bảng Furniture
INSERT INTO Furniture (furnitureID, apartmentID, name, condition, price)
VALUES 
    ('FURN1', 'APT1', N'Bàn ăn gỗ', N'Mới', 150.50),
    ('FURN2', 'APT1', N'Ghế sofa', N'Cũ', 100.25),
    ('FURN3', 'APT2', N'Tủ lạnh', N'Mới', 350.75),
    ('FURN4', 'APT3', N'Kệ sách', N'Cũ', 80.00),
    ('FURN5', 'APT3', N'Bàn làm việc', N'Mới', 200.00);
-- Dữ liệu cho bảng MonthlyRentBill
INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status)
VALUES 
    ('MRB1', 'APT1', 'TEN1', '2024-04-01', 12, '1200', N'Đã thanh toán'),
    ('MRB2', 'APT2', 'TEN2', '2024-04-01', 12, '1500', N'Chưa thanh toán'),
    ('MRB3', 'APT3', 'TEN3', '2024-04-01', 12, '1800', N'Đã thanh toán'),
    ('MRB4', 'APT4', 'TEN4', '2024-04-01', 12, '2000', N'Chưa thanh toán'),
    ('MRB5', 'APT5', 'TEN5', '2024-04-01', 12, '1400', N'Chưa thanh toán');
-- Dữ liệu cho bảng ServiceUsage
INSERT INTO ServiceUsage (serviceID, monthlyRentBillID, name, quantity, pricePerUnit, unit, totalAmount, Date, note)
VALUES 
    ('SERV1', 'MRB1', N'Dịch vụ vệ sinh', '2', '50', N'Lần', '100', '2024-04-10', NULL),
    ('SERV2', 'MRB2', N'Dịch vụ giặt ủi', '1', '70', N'Lần', '70', '2024-04-12', N'Khách hàng yêu cầu ủi đồ'),
    ('SERV3', 'MRB3', N'Giữ xe', '1', '30', N'Lần', '30', '2024-04-12', NULL),
    ('SERV4', 'MRB4', N'Internet', '1', '50', N'Tháng', '50', '2024-04-15', N'Yêu cầu kết nối mạng'),
    ('SERV5', 'MRB5', N'Tiền nước', '1', '25', N'Tháng', '25', '2024-04-20', NULL);
-- Dữ liệu cho bảng ServiceTicket
INSERT INTO ServiceTicket (serviceID, monthlyRentBillID, quantity, totalAmount, Date, Note)
VALUES 
    ('SERV1', 'MRB1', 2, 100.00, '2024-04-10', NULL),
    ('SERV2', 'MRB2', 1, 70.00, '2024-04-12', N'Yêu cầu ủi đồ nhanh'),
    ('SERV3', 'MRB3', 1, 30.00, '2024-04-12', NULL),
    ('SERV4', 'MRB4', 1, 50.00, '2024-04-15', N'Yêu cầu kết nối mạng'),
    ('SERV5', 'MRB5', 1, 25.00, '2024-04-20', NULL);
-- Dữ liệu cho bảng Violation
INSERT INTO Violation (violationID, monthlyRentBillID, name, totalAmount, note)
VALUES 
    ('V1', 'MRB1', N'Quá hạn thanh toán', '100', N'Phạt quá hạn 10%'),
    ('V2', 'MRB2', N'Gây ồn', '50', N'Đề nghị khắc phục ngay'),
    ('V3', 'MRB3', N'Cháy nổ', '150', N'Báo ngay lập tức'),
    ('V4', 'MRB4', N'Vi phạm an ninh', '200', N'Cảnh báo vi phạm an ninh'),
    ('V5', 'MRB5', N'Quá hạn thanh toán', '100', N'Phạt quá hạn 5%');
-- Dữ liệu cho bảng ViolationTicket
INSERT INTO ViolationTicket (violationID, monthlyRentBillID, name, totalAmount, Date, note)
VALUES 
    ('V1', 'MRB1', N'Quá hạn thanh toán', 100.00, '2024-04-05', N'Phạt quá hạn 10%'),
    ('V2', 'MRB2', N'Gây ồn', 50.00, '2024-04-15', N'Đề nghị khắc phục ngay'),
    ('V3', 'MRB3', N'Cháy nổ', 150.00, '2024-04-17', N'Báo ngay lập tức'),
    ('V4', 'MRB4', N'Vi phạm an ninh', 200.00, '2024-04-20', N'Cảnh báo vi phạm an ninh'),
    ('V5', 'MRB5', N'Quá hạn thanh toán', 100.00, '2024-04-25', N'Phạt quá hạn 5%');
=======
                          buildingID NVARCHAR(255) PRIMARY KEY,
                          name NVARCHAR(255),
                          city NVARCHAR(255),
                          district NVARCHAR(255),
                          address NVARCHAR(255),
                          numberOfApartment INT,

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

INSERT INTO Building (buildingID, name, city, district, address, numberOfApartment)
VALUES
    ('B001', 'Tòa nhà A', 'Thành phố Hồ Chí Minh', 'Quận 1', '123 Đường ABC', 50),
    ('B002', 'Tòa nhà B', 'Thành phố Hà Nội', 'Quận Ba Đình', '456 Đường XYZ', 40),
    ('B003', 'Tòa nhà C', 'Thành phố Đà Nẵng', 'Quận Hải Châu', '789 Đường PQR', 30),
    ('B004', 'Tòa nhà D', 'Thành phố Hồ Chí Minh', 'Quận 7', '246 Đường MNO', 60),
    ('B005', 'Tòa nhà E', 'Thành phố Hà Nội', 'Quận Hai Bà Trưng', '135 Đường UVW', 35);

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
>>>>>>> c9b9f551bf7a43c8916c84fbf2efb9d7eccc48a8
