

CREATE TABLE Building (
<<<<<<< HEAD
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
=======
                          buildingID NVARCHAR(20) PRIMARY KEY,
>>>>>>> a864d066a1b41ab18d70fa8c2a6e2b883c31cfde
                          name NVARCHAR(255),
                          city NVARCHAR(255),
                          district NVARCHAR(255),
                          address NVARCHAR(255),
                          numberOfApartment INT
);

CREATE TABLE BuildingManager (
                                 buildingManagerID NVARCHAR(20) PRIMARY KEY,
                                 buildingID NVARCHAR(20),
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
                                 financialReportID NVARCHAR(20) PRIMARY KEY,
                                 buildingID NVARCHAR(20),
                                 buildingManagerID NVARCHAR(20),
                                 Date DATE,
                                 monthlyRevenue DECIMAL(20, 2),
                                 monthlyOpex DECIMAL(20, 2),
                                 monthlyProfit DECIMAL(20, 2),
                                 FOREIGN KEY (buildingID) REFERENCES Building(buildingID),
                                 FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID)
);

CREATE TABLE Tenant (
                        tenantID NVARCHAR(20) PRIMARY KEY,
                        lastName NVARCHAR(255),
                        firstName NVARCHAR(255),
                        phoneNumber NVARCHAR(255),
                        dob DATE,
                        gender NVARCHAR(255),
                        citizenIdentityCard NVARCHAR(255)
);

CREATE TABLE Apartment (
                           apartmentID NVARCHAR(20) PRIMARY KEY,
                           buildingID NVARCHAR(20),
                           roomNumber NVARCHAR(255),
                           Area NVARCHAR(255),
                           bedrooms INT,
                           bathrooms INT,
                           furniture NVARCHAR(255),
                           FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
);

CREATE TABLE Cohabitant (
                            cohabitantID NVARCHAR(20) PRIMARY KEY,
                            tenantID NVARCHAR(20),
                            lastName NVARCHAR(255),
                            firstName NVARCHAR(255),
                            phoneNumber NVARCHAR(255),
                            dob DATE,
                            gender NVARCHAR(10),
                            citizenIdentityCard NVARCHAR(255),
                            FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE LeaseAgreement (
                                leaseAgreementID NVARCHAR(20) PRIMARY KEY,
                                tenantID NVARCHAR(20),
                                apartmentID NVARCHAR(20),
                                buildingManagerID NVARCHAR(20),
                                signingDate DATE,
                                LeaseStartDate DATE,
                                LeaseEndDate DATE,
                                LeaseTerm NVARCHAR(255),
                                deposit DECIMAL(20, 2),
                                monthlyRent DECIMAL(20, 2),
                                FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID),
                                FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID),
                                FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE Furniture (
                           furnitureID NVARCHAR(20) PRIMARY KEY,
                           apartmentID NVARCHAR(20),
                           name NVARCHAR(255),
                           condition NVARCHAR(255),
                           price DECIMAL(20, 2),
                           FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE MonthlyRentBill (
                                 monthlyRentBillID NVARCHAR(20) PRIMARY KEY,
                                 apartmentID NVARCHAR(20),
                                 tenantID NVARCHAR(20),
                                 date DATE,
                                 repaymentPeriod INT,
                                 totalPayment DECIMAL(20, 2),
                                 status NVARCHAR(255),
                                 FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID),
                                 FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE ServiceUsage (
                              serviceID NVARCHAR(20) PRIMARY KEY,
                              monthlyRentBillID NVARCHAR(20),
                              name NVARCHAR(255),
                              quantity INT,
                              pricePerUnit DECIMAL(20, 2),
                              unit NVARCHAR(255),
                              totalAmount DECIMAL(20, 2),
                              Date DATE,
                              note TEXT,
                              FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);

CREATE TABLE ServiceTicket (
                               serviceID NVARCHAR(20),
                               monthlyRentBillID NVARCHAR(20),
                               quantity DECIMAL(10, 2),
                               totalAmount DECIMAL(20, 2),
                               Date DATE,
                               Note VARCHAR(255),
                               PRIMARY KEY (serviceID, monthlyRentBillID),
                               FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
                               FOREIGN KEY (serviceID) REFERENCES ServiceUsage(serviceID)
);

CREATE TABLE Violation (
                           violationID NVARCHAR(20) PRIMARY KEY,
                           monthlyRentBillID NVARCHAR(20),
                           name NVARCHAR(255),
                           totalAmount DECIMAL(20, 2),
                           note TEXT,
                           FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID)
);

CREATE TABLE ViolationTicket (
                                 violationID NVARCHAR(20),
                                 monthlyRentBillID NVARCHAR(20),
                                 name VARCHAR(255),
                                 totalAmount DECIMAL(20, 2),
                                 Date DATE,
                                 note TEXT,
                                 PRIMARY KEY (violationID, monthlyRentBillID),
                                 FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
                                 FOREIGN KEY (violationID) REFERENCES Violation(violationID)
);

CREATE TABLE TaiKhoan (
                          id NVARCHAR(20) PRIMARY KEY,
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
VALUES ('FR1', 'B1', 'BM1', '2024-04-24', 50000000.00, 30000000.00, 20000000.00),
       ('FR2', 'B2', 'BM2', '2024-02-12', 60000000.00, 35000000.00, 25000000.00),
       ('FR3', 'B3', 'BM3', '2024-09-02', 70000000.00, 40000000.00, 30000000.00),
       ('FR4', 'B4', 'BM4', '2024-01-12', 55000000.00, 32000000.00, 23000000.00),
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
    ('MRB1', 'APT1', 'T1', '2024-03-01', 12, '1200', N'Unpaid'),
    ('MRB2', 'APT2', 'T2', '2024-04-13', 12, '1500', N'Unpaid'),
    ('MRB3', 'APT3', 'T3', '2024-01-16', 12, '1800', N'Paid'),
    ('MRB4', 'APT4', 'T4', '2024-11-24', 12, '2000', N'Pending'),
    ('MRB5', 'APT5', 'T5', '2024-12-21', 12, '1400', N'Pending');
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
<<<<<<< HEAD
    (601, 401, 'Late Payment', 200000.00, '2024-04-20', 'Resolved'),
    (602, 402, 'Noise Complaint', 100000.00, '2024-04-20', 'Open'),
    (603, 403, 'Damage', 300000.00, '2024-04-20', 'Resolved'),
    (604, 404, 'Late Payment', 200000.00, '2024-04-20', 'Open'),
    (605, 405, 'Late Payment', 200000.00, '2024-04-20', 'Open');
>>>>>>> c9b9f551bf7a43c8916c84fbf2efb9d7eccc48a8
=======
    ('V1', 'MRB1', N'Quá hạn thanh toán', 100.00, '2024-04-05', N'Phạt quá hạn 10%'),
    ('V2', 'MRB2', N'Gây ồn', 50.00, '2024-04-15', N'Đề nghị khắc phục ngay'),
    ('V3', 'MRB3', N'Cháy nổ', 150.00, '2024-04-17', N'Báo ngay lập tức'),
    ('V4', 'MRB4', N'Vi phạm an ninh', 200.00, '2024-04-20', N'Cảnh báo vi phạm an ninh'),
    ('V5', 'MRB5', N'Quá hạn thanh toán', 100.00, '2024-04-25', N'Phạt quá hạn 5%');
>>>>>>> a864d066a1b41ab18d70fa8c2a6e2b883c31cfde
