Create Database QuanLyChoThueCanHo

Use QuanLyChoThueCanHo

CREATE TABLE Building (
                          buildingID VARCHAR(20) PRIMARY KEY,
                          name NVARCHAR(255),
                          city NVARCHAR(255),
                          district NVARCHAR(255),
                          address NVARCHAR(255),
                          numberOfApartment INT
);

CREATE TABLE BuildingManager (
                                 buildingManagerID VARCHAR(20) PRIMARY KEY,
                                 buildingID VARCHAR(20),
                                 lastName NVARCHAR(255),
                                 firstName NVARCHAR(255),
                                 phoneNumber VARCHAR(255),
                                 dob DATE,
                                 gender NVARCHAR(10),
                                 citizenIdentityCard VARCHAR(255),
                                 salary DECIMAL(20, 2),
                                 FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
);

CREATE TABLE FinancialReport (
                                 financialReportID VARCHAR(20) PRIMARY KEY,
                                 buildingID VARCHAR(20),
                                 buildingManagerID VARCHAR(20),
                                 Date DATE,
                                 monthlyRevenue DECIMAL(20, 2),
                                 monthlyOpex DECIMAL(20, 2),
                                 monthlyProfit DECIMAL(20, 2),
                                 FOREIGN KEY (buildingID) REFERENCES Building(buildingID),
                                 FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID)
);

CREATE TABLE Tenant (
                        tenantID VARCHAR(20) PRIMARY KEY,
                        lastName NVARCHAR(255),
                        firstName NVARCHAR(255),
                        phoneNumber VARCHAR(255),
                        dob DATE,
                        gender NVARCHAR(255),
                        citizenIdentityCard VARCHAR(255)
);

CREATE TABLE Apartment (
                           apartmentID VARCHAR(20) PRIMARY KEY,
                           buildingID VARCHAR(20),
                           roomNumber VARCHAR(255),
                           Area NVARCHAR(255),
                           bedrooms INT,
                           bathrooms INT,
                           furniture NVARCHAR(255),
                           FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
);

CREATE TABLE Cohabitant (
                            cohabitantID VARCHAR(20) PRIMARY KEY,
                            tenantID VARCHAR(20),
                            lastName VARCHAR(255),
                            firstName NVARCHAR(255),
                            phoneNumber VARCHAR(255),
                            dob DATE,
                            gender NVARCHAR(10),
                            citizenIdentityCard VARCHAR(255),
                            FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE LeaseAgreement (
                                leaseAgreementID VARCHAR(20) PRIMARY KEY,
                                tenantID VARCHAR(20),
                                apartmentID VARCHAR(20),
                                buildingManagerID VARCHAR(20),
                                signingDate DATE,
                                LeaseStartDate DATE,
                                LeaseEndDate DATE,
                                LeaseTerm INT,
                                deposit DECIMAL(20, 2),
                                monthlyRent DECIMAL(20, 2),
                                FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID),
                                FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID),
                                FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE Furniture (
                           furnitureID VARCHAR(20) PRIMARY KEY,
                           apartmentID VARCHAR(20),
                           name NVARCHAR(255),
                           condition NVARCHAR(255),
                           price DECIMAL(20, 2),
                           FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE MonthlyRentBill (
                                 monthlyRentBillID VARCHAR(20) PRIMARY KEY,
                                 apartmentID VARCHAR(20),
                                 tenantID VARCHAR(20),
                                 date DATE,
                                 repaymentPeriod INT,
                                 totalPayment DECIMAL(20, 2),
                                 status NVARCHAR(255),
                                 FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID),
                                 FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE ServiceUsage (
                              serviceID VARCHAR(20) PRIMARY KEY,
                              name NVARCHAR(255),
                              quantity INT,
                              pricePerUnit DECIMAL(20, 2),
                              unit NVARCHAR(255),
                              totalAmount DECIMAL(20, 2),
                              Date DATE,
                              note TEXT,
                             

CREATE TABLE ServiceTicket (
                               serviceID VARCHAR(20),
                               monthlyRentBillID VARCHAR(20),
                               quantity DECIMAL(10, 2),
                               totalAmount DECIMAL(20, 2),
                               Date DATE,
                               Note TEXT,
                               PRIMARY KEY (serviceID, monthlyRentBillID),
                               FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
                               FOREIGN KEY (serviceID) REFERENCES ServiceUsage(serviceID)
);

CREATE TABLE Violation (
                           violationID VARCHAR(20) PRIMARY KEY,
                           name NVARCHAR(255),
                           totalAmount DECIMAL(20, 2),
                           note TEXT,
                           
);

CREATE TABLE ViolationTicket (
                                 violationID VARCHAR(20),
                                 monthlyRentBillID VARCHAR(20),
                                 name VARCHAR(255),
                                 totalAmount DECIMAL(20, 2),
                                 Date DATE,
                                 note TEXT,
                                 PRIMARY KEY (violationID, monthlyRentBillID),
                                 FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
                                 FOREIGN KEY (violationID) REFERENCES Violation(violationID)
);

CREATE TABLE TaiKhoan (
                          id VARCHAR(20) PRIMARY KEY,
                          username VARCHAR(255),
                          password VARCHAR(255),
                          role VARCHAR(255)
);



INSERT INTO TaiKhoan  (Id, username, password, role)
VALUES ('admin', 'admin', '123', 'admin'),
       ('BM1', 'BM1', '123', 'manager'),
       ('BM2', 'BM2', '123', 'manager'),
       ('BM3', 'BM3', '123', 'manager'),
       ('BM4', 'BM4', '123', 'manager'),
       ('BM5', 'BM5', '123', 'manager'),
       ('T1', 'T1', '123', 'customer'),
       ('T2', 'T2', '123', 'customer'),
       ('T3', 'T3', '123', 'customer'),
       ('T4', 'T4', '123', 'customer'),
       ('T5', 'T5', '123', 'customer');

INSERT INTO Building (buildingID, name, city, district, address, numberOfApartment)
VALUES ('B1', N'Tòa nhà A', N'Hà Nội', N'Cầu Giấy', N'Số 123 Đường ABC', 50),
       ('B2', N'Tòa nhà B', N'Hồ Chí Minh', N'Quận 1', N'Số 456 Đường XYZ', 40),
       ('B3', N'Tòa nhà C', N'Đà Nẵng', N'Hải Châu', N'Số 789 Đường LMN', 30),
       ('B4', N'Tòa nhà D', N'Hải Phòng', N'Đồ Sơn', N'Số 321 Đường PQR', 20),
       ('B5', N'Tòa nhà E', N'Cần Thơ', N'Ninh Kiều', N'Số 555 Đường STU', 10);
INSERT INTO BuildingManager (buildingManagerID, buildingID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard, salary)
VALUES ('BM1', 'B1', N'Nguyễn', N'Văn A', '0123456789', '1980-01-01', N'Nam', '123456789', 15000000.00),
       ('BM2', 'B2', N'Trần', N'Thị B', '0987654321', '1985-05-10', N'Nữ', '987654321', 12000000.00),
       ('BM3', 'B3', N'Lê', N'Văn C', '0369852147', '1990-12-20', N'Nam', '654789321', 13500000.00),
       ('BM4', 'B4', N'Phạm', N'Thị D', '0932154789', '1982-08-15', N'Nữ', '852963147', 14000000.00),
       ('BM5', 'B5', N'Hồ', N'Văn E', '0789456123', '1988-04-30', N'Nam', '369852147', 13000000.00);
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
VALUES ('LA1', 'T1', 'APT1', 'BM1', '2024-04-01', '2024-04-15', '2025-04-15', 12, '10000000', '2000000'),
       ('LA2', 'T2', 'APT2', 'BM2', '2024-04-01', '2024-04-15', '2025-04-15', 12, '11000000', '2100000'),
       ('LA3', 'T3', 'APT3', 'BM3', '2024-04-01', '2024-04-15', '2025-04-15', 24, '12000000', '2200000'),
       ('LA4', 'T4', 'APT4', 'BM4', '2024-04-01', '2024-04-15', '2025-04-15',36, '13000000', '2300000'),
       ('LA5', 'T5', 'APT5', 'BM5', '2024-04-01', '2024-04-15', '2025-04-15', 24, '14000000', '2400000');
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
    ('MRB1', 'APT1', 'T1', '2024-03-01', 5, '1200', N'Unpaid'),
    ('MRB2', 'APT2', 'T2', '2024-04-13', 5, '1500', N'Unpaid'),
    ('MRB3', 'APT3', 'T3', '2024-01-16', 5, '1800', N'Paid'),
    ('MRB4', 'APT4', 'T4', '2024-11-24', 5, '2000', N'Overdue'),
    ('MRB5', 'APT5', 'T5', '2024-12-21', 5, '1400', N'Overdue');

-- Dữ liệu cho bảng ServiceUsage
INSERT INTO ServiceUsage (serviceID, name, quantity, pricePerUnit, unit, totalAmount, Date, note)
VALUES
    ('SERV1', N'Dịch vụ vệ sinh', '2', '50', N'Lần', '100', '2024-04-10', NULL),
    ('SERV2', N'Dịch vụ giặt ủi', '1', '70', N'Lần', '70', '2024-04-12', N'Khách hàng yêu cầu ủi đồ'),
    ('SERV3', N'Giữ xe', '1', '30', N'Lần', '30', '2024-04-12', NULL),
    ('SERV4', N'Internet', '1', '50', N'Tháng', '50', '2024-04-15', N'Yêu cầu kết nối mạng'),
    ('SERV5', N'Tiền nước', '1', '25', N'Tháng', '25', '2024-04-20', NULL);
-- Dữ liệu cho bảng ServiceTicket
INSERT INTO ServiceTicket (serviceID, monthlyRentBillID, quantity, totalAmount, Date, Note)
VALUES
    ('SERV1', 'MRB1', 2, 100.00, '2024-04-10', NULL),
    ('SERV2', 'MRB2', 1, 70.00, '2024-04-12', N'Yêu cầu ủi đồ nhanh'),
    ('SERV3', 'MRB3', 1, 30.00, '2024-04-12', NULL),
    ('SERV4', 'MRB4', 1, 50.00, '2024-04-15', N'Yêu cầu kết nối mạng'),
    ('SERV5', 'MRB5', 1, 25.00, '2024-04-20', NULL);
-- Dữ liệu cho bảng Violation
INSERT INTO Violation (violationID, name, totalAmount, note)
VALUES
    ('V1', N'Quá hạn thanh toán', '100', N'Phạt quá hạn 10%'),
    ('V2', N'Gây ồn', '50', N'Đề nghị khắc phục ngay'),
    ('V3', N'Cháy nổ', '150', N'Báo ngay lập tức'),
    ('V4', N'Vi phạm an ninh', '200', N'Cảnh báo vi phạm an ninh'),
    ('V5', N'Quá hạn thanh toán', '100', N'Phạt quá hạn 5%');
-- Dữ liệu cho bảng ViolationTicket
INSERT INTO ViolationTicket (violationID, monthlyRentBillID, name, totalAmount, Date, note)
VALUES
    ('V1', 'MRB1', N'Quá hạn thanh toán', 100.00, '2024-04-05', N'Phạt quá hạn 10%'),
    ('V2', 'MRB2', N'Gây ồn', 50.00, '2024-04-15', N'Đề nghị khắc phục ngay'),
    ('V3', 'MRB3', N'Cháy nổ', 150.00, '2024-04-17', N'Báo ngay lập tức'),
    ('V4', 'MRB4', N'Vi phạm an ninh', 200.00, '2024-04-20', N'Cảnh báo vi phạm an ninh'),
    ('V5', 'MRB5', N'Quá hạn thanh toán', 100.00, '2024-04-25', N'Phạt quá hạn 5%');

INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status)
VALUES
    ('MRB6', 'APT1', 'T1', '2024-05-05', 5, '1600', N'Overdue'),
    ('MRB7', 'APT1', 'T1', '2024-06-10', 5, '1800', N'Paid'),
    ('MRB8', 'APT1', 'T1', '2024-07-15', 5, '2000', N'Unpaid'),
    ('MRB9', 'APT1', 'T1', '2024-08-20', 5, '2200', N'Overdue'),
    ('MRB10', 'APT1', 'T1', '2024-09-25', 5, '2400', N'Paid'),
    ('MRB11', 'APT1', 'T1', '2024-10-30', 5, '2600', N'Unpaid'),
    ('MRB12', 'APT1', 'T1', '2024-11-04', 5, '2800', N'Overdue'),
    ('MRB13', 'APT1', 'T1', '2024-12-09', 5, '3000', N'Paid'),
    ('MRB14', 'APT1', 'T1', '2025-01-14', 5, '3200', N'Unpaid'),
    ('MRB15', 'APT1', 'T1', '2025-02-19', 5, '3400', N'Overdue'),
    ('MRB16', 'APT1', 'T1', '2025-03-25', 5, '3600', N'Paid'),
    ('MRB17', 'APT1', 'T1', '2025-04-30', 5, '3800', N'Unpaid'),
    ('MRB18', 'APT1', 'T1', '2025-05-05', 5, '4000', N'Overdue'),
    ('MRB19', 'APT1', 'T1', '2025-06-10', 5, '4200', N'Paid'),
    ('MRB20', 'APT1', 'T1', '2025-07-15', 5, '4400', N'Unpaid'),
    ('MRB21', 'APT1', 'T1', '2025-08-20', 5, '4600', N'Overdue'),
    ('MRB22', 'APT1', 'T1', '2025-09-25', 5, '4800', N'Paid'),
    ('MRB23', 'APT1', 'T1', '2025-10-30', 5, '5000', N'Unpaid'),
    ('MRB24', 'APT1', 'T1', '2025-11-04', 5, '5200', N'Overdue'),
    ('MRB25', 'APT1', 'T1', '2025-12-09', 5, '5400', N'Paid');