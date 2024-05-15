create database quanlychothuecanho
go
use quanlychothuecanho
go
CREATE TABLE Building (
                          buildingID VARCHAR(100) PRIMARY KEY,
                          name NVARCHAR(255),
                          city NVARCHAR(255),
                          district NVARCHAR(255),
                          address NVARCHAR(255),
                          numberOfApartment INT
);

CREATE TABLE BuildingManager (
                                 buildingManagerID VARCHAR(100) PRIMARY KEY,
                                 buildingID VARCHAR(100),
                                 lastName NVARCHAR(255),
                                 firstName NVARCHAR(255),
                                 phoneNumber VARCHAR(255),
                                 dob DATE,
                                 gender NVARCHAR(10),
                                 citizenIdentityCard VARCHAR(100),
                                 salary DECIMAL(20, 2),
                                 FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
);

CREATE TABLE FinancialReport (
                                 financialReportID VARCHAR(100) PRIMARY KEY,
                                 buildingID VARCHAR(100),
                                 buildingManagerID VARCHAR(100),
                                 Date DATE,
                                 monthlyRevenue DECIMAL(20, 2),
                                 monthlyOpex DECIMAL(20, 2),
                                 monthlyProfit DECIMAL(20, 2),
                                 FOREIGN KEY (buildingID) REFERENCES Building(buildingID),
                                 FOREIGN KEY (buildingManagerID) REFERENCES BuildingManager(buildingManagerID)
);

CREATE TABLE Tenant (
                        tenantID VARCHAR(100) PRIMARY KEY,
                        lastName NVARCHAR(255),
                        firstName NVARCHAR(255),
                        phoneNumber NVARCHAR(255),
                        dob DATE,
                        gender NVARCHAR(255),
                        citizenIdentityCard VARCHAR(100)
);

CREATE TABLE Apartment (
                           apartmentID VARCHAR(100) PRIMARY KEY,
                           buildingID VARCHAR(100),
                           roomNumber VARCHAR(255),
                           area float,
                           bedrooms INT,
                           bathrooms INT,
                           furniture NVARCHAR(255),
                           FOREIGN KEY (buildingID) REFERENCES Building(buildingID)
);

CREATE TABLE Cohabitant (
                            cohabitantID VARCHAR(100) PRIMARY KEY,
                            tenantID VARCHAR(100),
                            lastName NVARCHAR(255),
                            firstName NVARCHAR(255),
                            phoneNumber VARCHAR(100),
                            dob DATE,
                            gender NVARCHAR(10),
                            citizenIdentityCard VARCHAR(100),
                            FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE LeaseAgreement (
                                leaseAgreementID VARCHAR(100) PRIMARY KEY,
                                tenantID VARCHAR(100),
                                apartmentID VARCHAR(100),
                                buildingManagerID VARCHAR(100),
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
                           furnitureID VARCHAR(100) PRIMARY KEY,
                           apartmentID VARCHAR(100),
                           name NVARCHAR(255),
                           condition NVARCHAR(255),
                           price DECIMAL(20, 2),
                           FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID)
);

CREATE TABLE MonthlyRentBill (
                                 monthlyRentBillID VARCHAR(100) PRIMARY KEY,
                                 apartmentID VARCHAR(100),
                                 tenantID VARCHAR(100),
                                 date DATE,
                                 repaymentPeriod INT,
                                 totalPayment DECIMAL(20, 2),
                                 status NVARCHAR(255),
                                 FOREIGN KEY (apartmentID) REFERENCES Apartment(apartmentID),
                                 FOREIGN KEY (tenantID) REFERENCES Tenant(tenantID)
);

CREATE TABLE Service (
                         serviceID VARCHAR(100) PRIMARY KEY,
                         name NVARCHAR(255),
                         pricePerUnit DECIMAL(20, 2),
                         unit NVARCHAR(255),
                         type NVARCHAR(255)
);

CREATE TABLE ServiceTicket (
                               serviceTicketID VARCHAR(100),
                               monthlyRentBillID VARCHAR(100),
                               serviceID VARCHAR(100),
                               quantity DECIMAL(10, 2),
                               totalAmount DECIMAL(20, 2),
                               Date DATE,
                               note NVARCHAR(250),
                               PRIMARY KEY (serviceTicketID),
                               FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
                               FOREIGN KEY (serviceID) REFERENCES Service(serviceID)
);

CREATE TABLE Violation (
                           violationID VARCHAR(100) PRIMARY KEY,
                           name NVARCHAR(255),
                           price DECIMAL(20, 2),
);

CREATE TABLE ViolationTicket (
                                 violationTicketID VARCHAR(100),
                                 violationID VARCHAR(100),
                                 monthlyRentBillID VARCHAR(100),
                                 price DECIMAL(20, 2),
                                 Date DATE,
                                 note NVARCHAR(250),
                                 PRIMARY KEY (violationTicketID),
                                 FOREIGN KEY (monthlyRentBillID) REFERENCES MonthlyRentBill(monthlyRentBillID),
                                 FOREIGN KEY (violationID) REFERENCES Violation(violationID)
);

CREATE TABLE StaffsAccount (
                               username VARCHAR(100) PRIMARY KEY,
                               password NVARCHAR(255),
                               id VARCHAR(100),
                               FOREIGN KEY (id) REFERENCES BuildingManager(buildingManagerID)
);
CREATE TABLE CustomersAccount (
                                  username VARCHAR(100) PRIMARY KEY,
                                  password NVARCHAR(255),
                                  id VARCHAR(100),
                                  FOREIGN KEY (id) REFERENCES Tenant(tenantID)
    ,
);


INSERT INTO Building (buildingID, name, city, district, address, numberOfApartment)
VALUES ('B1', N'Tòa nhà A', N'Hà Nội', N'Cầu Giấy', N'Số 123 Đường ABC', 50),
       ('B2', N'Tòa nhà B', N'Hồ Chí Minh', N'Quận 1', N'Số 456 Đường XYZ', 40),
       ('B3', N'Tòa nhà C', N'Đà Nẵng', N'Hải Châu', N'Số 789 Đường LMN', 30),
       ('B4', N'Tòa nhà D', N'Hải Phòng', N'Đồ Sơn', N'Số 321 Đường PQR', 20),
       ('B5', N'Tòa nhà E', N'Cần Thơ', N'Ninh Kiều', N'Số 555 Đường STU', 10);
INSERT INTO BuildingManager (buildingManagerID, buildingID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard, salary)
VALUES
    ('BM1', 'B1', N'Nguyễn', N'Văn A', '0123456789', '1980-01-01', N'Nam', '123456789', 15000000.00),
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
       ('T5', N'Hồ', N'Thị E', '0789456123', '1997-03-05', N'Nữ', '369852147'),
       ('T6', N'Vũ', N'Thị F', '0123456780', '1993-02-15', N'Nữ', '369852741'),
       ('T7', N'Đặng', N'Văn G', '0987654322', '1985-08-20', N'Nam', '1234567890'),
       ('T8', N'Hoàng', N'Thị H', '0369852148', '1998-04-10', N'Nữ', '9876543210'),
       ('T9', N'Lý', N'Văn I', '0932154780', '1994-11-05', N'Nam', '6547893210'),
       ('T10', N'Ngô', N'Thị K', '0789456122', '1996-06-30', N'Nữ', '8529631470'),
       ('T11', N'Phan', N'Thị L', '0123456781', '1993-03-20', N'Nữ', '3698527411'),
       ('T12', N'Trương', N'Văn M', '0987654323', '1985-09-25', N'Nam', '1234567891'),
       ('T13', N'Đỗ', N'Thị N', '0369852149', '1998-05-15', N'Nữ', '9876543211'),
       ('T14', N'Dương', N'Văn O', '0932154781', '1994-12-10', N'Nam', '6547893211'),
       ('T15', N'Bùi', N'Thị P', '0789456123', '1996-07-05', N'Nữ', '8529631471'),
       ('T16', N'Ma', N'Văn Q', '0123456782', '1990-04-20', N'Nam', '3698527412'),
       ('T17', N'Vương', N'Thị R', '0987654324', '1995-10-25', N'Nữ', '1234567892'),
       ('T18', N'Lâm', N'Văn S', '0369852150', '1988-06-15', N'Nam', '9876543212'),
       ('T19', N'Kiều', N'Thị T', '0932154782', '1992-01-10', N'Nữ', '6547893212'),
       ('T20', N'Hà', N'Văn U', '0789456124', '1997-08-05', N'Nam', '8529631472'),
       ('T21', N'Lương', N'Thị V', '0123456783', '1993-05-20', N'Nữ', '3698527413'),
       ('T22', N'Quách', N'Văn X', '0987654325', '1985-11-25', N'Nam', '1234567893'),
       ('T23', N'Nghiêm', N'Thị Y', '0369852151', '1998-07-15', N'Nữ', '9876543213'),
       ('T24', N'Tạ', N'Văn Z', '0932154783', '1994-02-10', N'Nam', '6547893213'),
       ('T25', N'Võ', N'Thị AA', '0789456125', '1996-09-05', N'Nữ', '8529631473'),
       ('T26', N'Ho', N'Văn BB', '0123456784', '1990-06-20', N'Nam', '3698527414'),
       ('T27', N'Tô', N'Thị CC', '0987654326', '1995-12-25', N'Nữ', '1234567894'),
       ('T28', N'Văn', N'Văn DD', '0369852152', '1988-08-15', N'Nam', '9876543214'),
       ('T29', N'Hoàng', N'Thị EE', '0932154784', '1992-03-10', N'Nữ', '6547893214'),
       ('T30', N'Dương', N'Văn FF', '0789456126', '1997-10-05', N'Nam', '8529631474');
INSERT INTO Apartment (apartmentID, buildingID, roomNumber, area, bedrooms, bathrooms, furniture)
VALUES ('APT1', 'B1', '101', 50, 1, 1, N'Cơ bản'),
       ('APT2', 'B1', '102', 60, 2, 1, N'Đầy đủ'),
       ('APT3', 'B2', '201', 50, 1, 1, N'Cơ bản'),
       ('APT4', 'B2', '202', 55.5, 2, 1, N'Cơ bản'),
       ('APT5', 'B3', '301', 70, 3, 2, N'Đầy đủ'),
       ('APT6', 'B3', '302', 75, 3, 2, N'Cơ bản'),
       ('APT7', 'B4', '401', 60, 2, 1, N'Không có'),
       ('APT8', 'B4', '402', 65, 2, 1, N'Cơ bản'),
       ('APT9', 'B5', '501', 80, 3, 2, N'Không có'),
       ('APT10', 'B5', '502', 90, 3, 2, N'Đầy đủ'),
       ('APT11', 'B3', '303', 75, 3, 2, N'Cơ bản'),
       ('APT12', 'B4', '403', 60, 2, 1, N'Đầy đủ'),
       ('APT13', 'B4', '404', 65, 2, 1, N'Cơ bản'),
       ('APT14', 'B5', '503', 80, 3, 2, N'Không có'),
       ('APT15', 'B5', '504', 90, 3, 2, N'Đầy đủ'),
       ('APT16', 'B1', '103', 50, 1, 1, N'Cơ bản'),
       ('APT17', 'B1', '104', 60, 2, 1, N'Đầy đủ'),
       ('APT18', 'B2', '202', 50, 1, 1, N'Cơ bản'),
       ('APT19', 'B2', '203', 55.5, 2, 1, N'Đầy đủ'),
       ('APT20', 'B3', '304', 70, 3, 2, N'Cơ bản'),
       ('APT21', 'B3', '305', 75, 3, 2, N'Không có'),
       ('APT22', 'B4', '405', 60, 2, 1, N'Đầy đủ'),
       ('APT23', 'B4', '406', 65, 2, 1, N'Không có'),
       ('APT24', 'B5', '505', 80, 3, 2, N'Đầy đủ'),
       ('APT25', 'B5', '506', 90, 3, 2, N'Không có'),
       ('APT26', 'B1', '105', 50, 1, 1, N'Cơ bản'),
       ('APT27', 'B1', '106', 60, 2, 1, N'Đầy đủ'),
       ('APT28', 'B2', '204', 50, 1, 1, N'Cơ bản'),
       ('APT29', 'B2', '205', 55.5, 2, 1, N'Đầy đủ'),
       ('APT30', 'B3', '306', 70, 3, 2, N'Không có');

INSERT INTO Cohabitant (cohabitantID, tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard)
VALUES ('CH1', 'T1', N'Nguyễn', N'Thị A', '0123456789', '1990-01-10', N'Nữ', '123456789'),
       ('CH2', 'T1', N'Trần', N'Văn B', '0987654321', '1995-05-20', N'Nam', '987654321'),
       ('CH3', 'T2', N'Lê', N'Thị C', '0369852147', '1988-12-15', N'Nữ', '654789321'),
       ('CH4', 'T3', N'Phạm', N'Văn D', '0932154789', '1992-07-25', N'Nam', '852963147'),
       ('CH5', 'T4', N'Hồ', N'Thị E', '0789456123', '1997-03-05', N'Nữ', '369852147'),
       ('CH6', 'T6', N'Vũ', N'Thị F', '0123456780', '1993-02-15', N'Nữ', '369852741'),
       ('CH7', 'T6', N'Đặng', N'Văn G', '0987654322', '1985-08-20', N'Nam', '1234567890'),
       ('CH8', 'T7', N'Hoàng', N'Thị H', '0369852148', '1998-04-10', N'Nữ', '9876543210'),
       ('CH9', 'T7', N'Lý', N'Văn I', '0932154780', '1994-11-05', N'Nam', '6547893210'),
       ('CH10', 'T7', N'Nguyễn', N'Thị K', '0789456122', '1996-06-30', N'Nữ', '8529631470'),
       ('CH11', 'T8', N'Phan', N'Thị L', '0123456781', '1993-03-20', N'Nữ', '3698527411'),
       ('CH12', 'T9', N'Trương', N'Văn M', '0987654323', '1985-09-25', N'Nam', '1234567891'),
       ('CH13', 'T9', N'Đỗ', N'Thị N', '0369852149', '1998-05-15', N'Nữ', '9876543211'),
       ('CH14', 'T9', N'Dương', N'Văn O', '0932154781', '1994-12-10', N'Nam', '6547893211'),
       ('CH15', 'T10', N'Bùi', N'Thị P', '0789456123', '1996-07-05', N'Nữ', '8529631471'),
       ('CH16', 'T11', N'Ma', N'Văn Q', '0123456782', '1990-04-20', N'Nam', '3698527412'),
       ('CH17', 'T11', N'Vương', N'Thị R', '0987654324', '1995-10-25', N'Nữ', '1234567892'),
       ('CH18', 'T11', N'Lâm', N'Văn S', '0369852150', '1988-06-15', N'Nam', '9876543212'),
       ('CH19', 'T12', N'Kiều', N'Thị T', '0932154782', '1992-01-10', N'Nữ', '6547893212'),
       ('CH20', 'T12', N'Hà', N'Văn U', '0789456124', '1997-08-05', N'Nam', '8529631472'),
       ('CH21', 'T12', N'Lương', N'Thị V', '0123456783', '1993-05-20', N'Nữ', '3698527413'),
       ('CH22', 'T13', N'Quách', N'Văn X', '0987654325', '1985-11-25', N'Nam', '1234567893'),
       ('CH23', 'T13', N'Nghiêm', N'Thị Y', '0369852151', '1998-07-15', N'Nữ', '9876543213'),
       ('CH24', 'T13', N'Tạ', N'Văn Z', '0932154783', '1994-02-10', N'Nam', '6547893213'),
       ('CH25', 'T14', N'Võ', N'Thị AA', '0789456125', '1996-09-05', N'Nữ', '8529631473'),
       ('CH26', 'T14', N'Ho', N'Văn BB', '0123456784', '1990-06-20', N'Nam', '3698527414'),
       ('CH27', 'T14', N'Tô', N'Thị CC', '0987654326', '1995-12-25', N'Nữ', '1234567894'),
       ('CH28', 'T15', N'Văn', N'Văn DD', '0369852152', '1988-08-15', N'Nam', '9876543214'),
       ('CH29', 'T15', N'Hoàng', N'Thị EE', '0932154784', '1992-03-10', N'Nữ', '6547893214'),
       ('CH30', 'T15', N'Dương', N'Văn FF', '0789456126', '1997-10-05', N'Nam', '8529631474'),
       ('CH31', 'T16', N'Thái', N'Thị GG', '0123456785', '1993-07-20', N'Nữ', '3698527415'),
       ('CH32', 'T17', N'Hồ', N'Văn HH', '0987654327', '1985-01-25', N'Nam', '1234567895'),
       ('CH33', 'T17', N'Lê', N'Thị II', '0369852153', '1998-09-15', N'Nữ', '9876543215'),
       ('CH34', 'T17', N'Nguyễn', N'Văn JJ', '0932154785', '1994-04-10', N'Nam', '6547893215'),
       ('CH35', 'T18', N'Phạm', N'Thị KK', '0789456127', '1996-11-05', N'Nữ', '8529631475'),
       ('CH36', 'T18', N'Bùi', N'Văn LL', '0123456786', '1990-08-20', N'Nam', '3698527416'),
       ('CH37', 'T18', N'Lâm', N'Thị MM', '0987654328', '1995-02-25', N'Nữ', '1234567896'),
       ('CH38', 'T19', N'Nguyễn', N'Văn NN', '0369852154', '1988-10-15', N'Nam', '9876543216'),
       ('CH39', 'T19', N'Võ', N'Thị OO', '0932154786', '1992-05-10', N'Nữ', '6547893216'),
       ('CH40', 'T19', N'Hoàng', N'Văn PP', '0789456128', '1997-12-05', N'Nam', '8529631476'),
       ('CH41', 'T20', N'Nguyễn', N'Thị QQ', '0123456787', '1993-09-20', N'Nữ', '3698527417'),
       ('CH42', 'T20', N'Vũ', N'Văn RR', '0987654329', '1985-03-25', N'Nam', '1234567897'),
       ('CH43', 'T20', N'Lê', N'Thị SS', '0369852155', '1998-11-15', N'Nữ', '9876543217'),
       ('CH44', 'T21', N'Hồ', N'Văn TT', '0932154787', '1994-06-10', N'Nam', '6547893217'),
       ('CH45', 'T21', N'Lưu', N'Thị UU', '0789456129', '1996-01-05', N'Nữ', '8529631477'),
       ('CH46', 'T21', N'Đinh', N'Văn VV', '0123456788', '1990-10-20', N'Nam', '3698527418'),
       ('CH47', 'T22', N'Đỗ', N'Thị WW', '0987654330', '1995-04-25', N'Nữ', '1234567898'),
       ('CH48', 'T22', N'Hoàng', N'Văn XX', '0369852156', '1988-12-15', N'Nam', '9876543218'),
       ('CH49', 'T22', N'Đặng', N'Thị YY', '0932154788', '1992-07-10', N'Nữ', '6547893218'),
       ('CH50', 'T23', N'Nguyễn', N'Văn ZZ', '0789456130', '1997-02-05', N'Nam', '8529631478'),
       ('CH51', 'T23', N'Nguyễn', N'Thị AAA', '0123456789', '1993-11-20', N'Nữ', '3698527419'),
       ('CH52', 'T23', N'Lê', N'Văn BBB', '0987654331', '1985-05-25', N'Nam', '1234567899'),
       ('CH53', 'T24', N'Nguyễn', N'Thị CCC', '0369852157', '1998-01-15', N'Nữ', '9876543219'),
       ('CH54', 'T24', N'Lê', N'Văn DDD', '0932154789', '1994-08-10', N'Nam', '6547893219'),
       ('CH55', 'T24', N'Nguyễn', N'Thị EEE', '0789456131', '1996-03-05', N'Nữ', '8529631479'),
       ('CH56', 'T25', N'Lê', N'Văn FFF', '0123456790', '1990-12-20', N'Nam', '3698527420'),
       ('CH57', 'T25', N'Phạm', N'Thị GGG', '0987654332', '1995-06-25', N'Nữ', '1234567800'),
       ('CH58', 'T25', N'Nguyễn', N'Văn HHH', '0369852158', '1988-02-15', N'Nam', '9876543220'),
       ('CH59', 'T26', N'Hoàng', N'Thị III', '0932154790', '1992-09-10', N'Nữ', '6547893220'),
       ('CH60', 'T26', N'Trần', N'Văn JJJ', '0789456132', '1997-04-05', N'Nam', '8529631480');

INSERT INTO LeaseAgreement (leaseAgreementID, tenantID, apartmentID, buildingManagerID, signingDate, LeaseStartDate, LeaseEndDate, LeaseTerm, deposit, monthlyRent)
VALUES ('LA1', 'T1', 'APT1', 'BM1', '2024-01-01', '2024-04-15', '2025-01-15', 9, 11000000, 5500000),
       ('LA2', 'T2', 'APT2', 'BM2', '2024-02-01', '2024-04-15', '2025-04-15', 12, 13400000, 6700000),
       ('LA3', 'T3', 'APT3', 'BM3', '2023-07-01', '2024-04-15', '2025-04-15', 12, 11000000, 5500000),
       ('LA4', 'T4', 'APT4', 'BM4', '2024-04-01', '2024-04-15', '2025-04-15',12, 12000000, 6000000),
       ('LA5', 'T5', 'APT5', 'BM5', '2024-01-01', '2024-04-15', '2025-04-15', 12, 15000000, 7500000),
       ('LA6', 'T6', 'APT6', 'BM1', '2024-01-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA7', 'T7', 'APT7', 'BM2', '2024-02-01', '2024-04-15', '2025-04-15', 12, 26800000, 13400000),
       ('LA8', 'T8', 'APT8', 'BM1', '2024-01-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA9', 'T9', 'APT9', 'BM3', '2024-01-01', '2024-04-15', '2025-04-15', 12, 24000000, 12000000),
       ('LA10', 'T10', 'APT10', 'BM4', '2024-01-01', '2024-04-15', '2025-04-15', 12, 30000000, 15000000),
       ('LA11', 'T11', 'APT11', 'BM5', '2024-04-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA12', 'T12', 'APT12', 'BM1', '2024-02-01', '2024-04-15', '2025-04-15', 12, 26800000, 13400000),
       ('LA13', 'T13', 'APT13', 'BM1', '2024-04-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA14', 'T14', 'APT14', 'BM3', '2024-04-01', '2024-04-15', '2025-04-15', 12, 24000000, 12000000),
       ('LA15', 'T15', 'APT15', 'BM1', '2024-05-01', '2024-04-15', '2025-04-15', 12, 30000000, 15000000),
       ('LA16', 'T16', 'APT16', 'BM1', '2024-04-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA17', 'T17', 'APT17', 'BM1', '2024-03-01', '2024-04-15', '2025-04-15', 12, 26800000, 13400000),
       ('LA18', 'T18', 'APT18', 'BM1', '2024-03-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA19', 'T19', 'APT19', 'BM2', '2024-04-01', '2024-04-15', '2025-04-15', 12, 24000000, 12000000),
       ('LA20', 'T20', 'APT20', 'BM3', '2024-04-01', '2024-04-15', '2025-04-15', 12, 30000000, 15000000),
       ('LA21', 'T21', 'APT21', 'BM4', '2024-03-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA22', 'T22', 'APT22', 'BM4', '2024-04-01', '2024-04-15', '2025-04-15', 12, 26800000, 13400000),
       ('LA23', 'T23', 'APT23', 'BM3', '2024-03-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA24', 'T24', 'APT24', 'BM5', '2024-05-01', '2024-04-15', '2025-04-15', 12, 24000000, 12000000),
       ('LA25', 'T25', 'APT25', 'BM5', '2024-04-01', '2024-04-15', '2025-04-15', 12, 30000000, 15000000),
       ('LA26', 'T26', 'APT26', 'BM2', '2024-01-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA27', 'T27', 'APT27', 'BM1', '2024-04-01', '2024-04-15', '2025-04-15', 12, 26800000, 13400000),
       ('LA28', 'T28', 'APT28', 'BM3', '2024-05-01', '2024-04-15', '2025-04-15', 12, 22000000, 11000000),
       ('LA29', 'T29', 'APT29', 'BM4', '2024-04-01', '2024-04-15', '2025-04-15', 12, 24000000, 12000000),
       ('LA30', 'T30', 'APT30', 'BM5', '2024-02-01', '2024-04-15', '2025-04-15', 12, 30000000, 15000000);

-- Dữ liệu cho bảng Furniture
INSERT INTO Furniture (furnitureID, apartmentID, name, condition, price)
VALUES
    ('FURN1', 'APT1', N'Bàn ăn gỗ', N'Mới', 50000),
    ('FURN2', 'APT1', N'Ghế sofa', N'Cũ', 100000),
    ('FURN3', 'APT2', N'Tủ lạnh', N'Mới', 100000),
    ('FURN4', 'APT3', N'Kệ sách', N'Cũ', 80000),
    ('FURN5', 'APT3', N'Bàn làm việc', N'Mới', 100000),
    ('FURN6', 'APT2', N'Giường ngủ', N'Mới', 200000),
    ('FURN7', 'APT2', N'Tủ quần áo', N'Mới', 150000),
    ('FURN8', 'APT2', N'Bàn trà', N'Mới', 50000),
    ('FURN9', 'APT5', N'Giường ngủ', N'Mới', 300000),
    ('FURN10', 'APT5', N'Tủ quần áo', N'Mới', 200000),
    ('FURN11', 'APT5', N'Sofa', N'Mới', 150000),
    ('FURN12', 'APT5', N'Bàn ăn', N'Mới', 100000),
    ('FURN13', 'APT5', N'Ghế', N'Mới', 50000),
    ('FURN14', 'APT10', N'Giường ngủ', N'Mới', 400000),
    ('FURN15', 'APT10', N'Tủ quần áo', N'Mới', 300000),
    ('FURN16', 'APT10', N'Sofa', N'Mới', 200000),
    ('FURN17', 'APT10', N'Bàn ăn', N'Mới', 150000),
    ('FURN18', 'APT10', N'Ghế', N'Mới', 100000),
    ('FURN19', 'APT12', N'Giường ngủ', N'Mới', 200000),
    ('FURN20', 'APT12', N'Tủ quần áo', N'Mới', 150000),
    ('FURN21', 'APT12', N'Bàn trà', N'Mới', 50000),
    ('FURN22', 'APT15', N'Giường ngủ', N'Mới', 300000),
    ('FURN23', 'APT15', N'Tủ quần áo', N'Mới', 200000),
    ('FURN24', 'APT15', N'Sofa', N'Mới', 150000),
    ('FURN25', 'APT15', N'Bàn ăn', N'Mới', 100000),
    ('FURN26', 'APT15', N'Ghế', N'Mới', 50000),
    ('FURN27', 'APT17', N'Giường ngủ', N'Mới', 200000),
    ('FURN28', 'APT17', N'Tủ quần áo', N'Mới', 150000),
    ('FURN29', 'APT17', N'Bàn trà', N'Mới', 50000),
    ('FURN30', 'APT19', N'Giường ngủ', N'Mới', 200000),
    ('FURN31', 'APT19', N'Tủ quần áo', N'Mới', 150000),
    ('FURN32', 'APT19', N'Bàn trà', N'Mới', 50000),
    ('FURN33', 'APT22', N'Giường ngủ', N'Mới', 200000),
    ('FURN34', 'APT22', N'Tủ quần áo', N'Mới', 150000),
    ('FURN35', 'APT22', N'Bàn trà', N'Mới', 50000),
    ('FURN36', 'APT24', N'Giường ngủ', N'Mới', 300000),
    ('FURN37', 'APT24', N'Tủ quần áo gỗ', N'Mới', 15000000),
    ('FURN38', 'APT24', N'Sofa', N'Mới', 200000),
    ('FURN39', 'APT24', N'Bàn ăn', N'Mới', 150000),
    ('FURN40', 'APT24', N'Ghế', N'Mới', 100000),
    ('FURN41', 'APT27', N'Giường ngủ', N'Mới', 200000),
    ('FURN42', 'APT27', N'Tủ quần áo', N'Mới', 150000),
    ('FURN43', 'APT27', N'Bàn trà', N'Mới', 50000),
    ('FURN44', 'APT29', N'Giường ngủ', N'Mới', 200000),
    ('FURN45', 'APT29', N'Tủ quần áo', N'Mới', 150000),
    ('FURN46', 'APT29', N'Bàn trà', N'Mới', 50000);
-- Dữ liệu cho bảng MonthlyRentBill
INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status)
VALUES
    ('MRB1', 'APT1', 'T1', '2024-05-01', 10, 6240000, N'Paid'),
    ('MRB31', 'APT1', 'T1', '2024-04-01', 5, 400000, N'Overdue'),
    ('MRB33', 'APT1', 'T1', '2024-03-05', 10, 700000, N'Overdue'),
    ('MRB35', 'APT1', 'T1', '2024-02-01', 10, 500000, N'Overdue'),
    ('MRB36', 'APT1', 'T1', '2024-01-24', 20, 500000, N'Unpaid'),
    ('MRB37', 'APT2', 'T2', '2024-01-15', 5, 500000, N'Overdue'),
    ('MRB38', 'APT2', 'T2', '2024-04-12', 5, 1500000, N'Overdue'),
    ('MRB39', 'APT2', 'T2', '2024-02-01', 10, 500000, N'Overdue'),
    ('MRB40', 'APT3', 'T3', '2024-04-01', 10, 2000000, N'Overdue'),
    ('MRB2', 'APT2', 'T2', '2024-05-01', 10, 7270000, N'Paid'),
    ('MRB3', 'APT3', 'T3', '2024-05-01', 10, 5973000, N'Paid'),
    ('MRB4', 'APT4', 'T4', '2024-05-01', 10, 6803000, N'Paid'),
    ('MRB5', 'APT5', 'T5', '2024-05-01', 10, 8250000, N'Paid'),
    ('MRB6', 'APT6', 'T6', '2024-05-01', 10, 11672000, N'Paid'),
    ('MRB7', 'APT7', 'T7', '2024-05-01', 10, 14222000, N'Paid'),
    ('MRB8', 'APT8', 'T8', '2024-05-01', 10, 11672000, N'Paid'),
    ('MRB9', 'APT9', 'T9', '2024-05-01', 10, 13322000, N'Paid'),
    ('MRB10', 'APT10', 'T10', '2024-05-01', 10, 16800000, N'Paid'),
    ('MRB11', 'APT11', 'T11', '2024-05-01', 10, 13232000, N'Paid'),
    ('MRB12', 'APT12', 'T12', '2024-05-01', 10, 16022000, N'Paid'),
    ('MRB13', 'APT13', 'T13', '2024-05-01', 10, 13372000, N'Paid'),
    ('MRB14', 'APT14', 'T14', '2024-05-01', 10, 16832000, N'Paid'),
    ('MRB15', 'APT15', 'T15', '2024-05-01', 10, 19422000, N'Paid'),
    ('MRB16', 'APT16', 'T16', '2024-05-01', 10, 12832000, N'Paid'),
    ('MRB17', 'APT17', 'T17', '2024-05-01', 10, 15622000, N'Paid'),
    ('MRB18', 'APT18', 'T18', '2024-05-01', 10, 12872000, N'Paid'),
    ('MRB19', 'APT19', 'T19', '2024-05-01', 10, 13322000, N'Paid'),
    ('MRB20', 'APT20', 'T20', '2024-05-01', 10, 16800000, N'Paid'),
    ('MRB21', 'APT21', 'T21', '2024-05-01', 10, 13232000, N'Paid'),
    ('MRB22', 'APT22', 'T22', '2024-05-01', 10, 16022000, N'Paid'),
    ('MRB23', 'APT23', 'T23', '2024-05-01', 10, 13372000, N'Paid'),
    ('MRB24', 'APT24', 'T24', '2024-05-01', 10, 16832000, N'Paid'),
    ('MRB25', 'APT25', 'T25', '2024-05-01', 10, 19422000, N'Paid'),
    ('MRB26', 'APT26', 'T26', '2024-05-01', 10, 12832000, N'Paid'),
    ('MRB27', 'APT27', 'T27', '2024-05-01', 10, 15622000, N'Paid'),
    ('MRB28', 'APT28', 'T28', '2024-05-01', 10, 12872000, N'Paid'),
    ('MRB29', 'APT29', 'T29', '2024-05-01', 10, 13322000, N'Paid'),
    ('MRB30', 'APT30', 'T30', '2024-05-01', 10, 16800000, N'Paid');
-- Dữ liệu cho bảng Service
INSERT INTO Service (serviceID, name, pricePerUnit, unit, type)
VALUES
    ('SERV1', N'Dịch vụ vệ sinh', 100000, N'Lần','mobile'),
    ('SERV2', N'Dịch vụ giặt ủi', 100000, N'Lần','mobile'),
    ('SERV3', N'Giữ xe',150000, N'Tháng','fixed'),
    ('SERV4', N'Internet', 200000, N'Tháng','fixed'),
    ('SERV5', N'Phòng gym', 350000, N'Tháng','fixed'),
    ('SERV6', N'Tiền nước', 6000, N'Khối','fixed'),
    ('SERV7', N'Tiền điện', 4000, N'kWh','fixed'),
    ('SERV8', N'Giữ trẻ', 50000, N'Ngày','mobile'),
    ('SERV9', N'Bể bơi', 200000, N'Tháng','fixed'),
    ('SERV10', N'Đặt bàn BBQ', 400000, N'Lần','mobile'),
    ('SERV11', N'Khu vui chơi trẻ em', 200000, N'Tháng','fixed')


-- Dữ liệu cho bảng ServiceTicket
    INSERT INTO ServiceTicket (serviceTicketID, monthlyRentBillID, serviceID, quantity, totalAmount, Date, note)
VALUES
    ('SERVT1', 'MRB1', 'SERV1', 2, 200000, '2024-05-01', NULL),
    ('SERVT2', 'MRB1', 'SERV1', 2, 200000, '2024-05-01', NULL),
    ('SERVT3', 'MRB1', 'SERV3', 1, 150000, '2024-05-01', 'Xe SH'),
    ('SERVT4', 'MRB2', 'SERV2', 1, 100000, '2024-04-12', N'Yêu cầu ủi đồ nhanh'),
    ('SERVT5', 'MRB3', 'SERV3', 1, 150000, '2024-04-12', NULL),
    ('SERVT6', 'MRB4', 'SERV4', 1, 200000, '2024-04-15', N'Yêu cầu kết nối mạng'),
    ('SERVT7', 'MRB5', 'SERV5', 1, 350000, '2024-04-20', NULL),
    ('SERVT8', 'MRB1', 'SERV6', 45, 270000, '2024-05-01', NULL),
    ('SERVT9', 'MRB1', 'SERV7', 120, 270000, '2024-05-01', NULL),
    ('SERVT10', 'MRB6', 'SERV8', 12, 600000, '2024-05-15', NULL),
    ('SERVT11', 'MRB2', 'SERV9', 1, 200000, '2024-04-01', NULL),
    ('SERVT12', 'MRB1', 'SERV9', 1, 200000, '2024-05-01', NULL),
    ('SERVT13', 'MRB1', 'SERV10', 1, 400000, '2024-05-17', N'Yêu cầu nhạc jazz'),
    ('SERVT14', 'MRB1', 'SERV11', 1, 200000, '2024-05-17', NULL),
    ('SERVT15', 'MRB6', 'SERV1', 1, 100000, '2024-04-12', N'Yêu cầu dọn dẹp phòng khách'),
    ('SERVT16', 'MRB3', 'SERV2', 2, 200000, '2024-04-14', N'Yêu cầu giặt ủi áo sơ mi'),
    ('SERVT17', 'MRB4', 'SERV3', 1, 150000, '2024-04-02', NULL),
    ('SERVT18', 'MRB5', 'SERV4', 1, 200000, '2024-04-05', N'Yêu cầu nâng cấp gói internet'),
    ('SERVT19', 'MRB1', 'SERV5', 1, 350000, '2024-04-04', NULL),
    ('SERVT20', 'MRB2', 'SERV6', 45, 270000, '2024-04-09', NULL),
    ('SERVT21', 'MRB3', 'SERV7', 126, 270000, '2024-04-11', NULL),
    ('SERVT22', 'MRB4', 'SERV8', 12, 600000, '2024-04-22', N'Yêu cầu trông trẻ buổi tối'),
    ('SERVT23', 'MRB5', 'SERV9', 1, 200000, '2024-04-24', NULL),
    ('SERVT24', 'MRB6', 'SERV10', 1, 400000, '2024-04-12', N'Đặt bàn BBQ 6 người'),
    ('SERVT25', 'MRB7', 'SERV6', 45, 318000, '2024-04-01', NULL),
    ('SERVT26', 'MRB7', 'SERV7', 120, 504000, '2024-04-01', NULL),
    ('SERVT27', 'MRB8', 'SERV6', 67, 402000, '2024-04-01', NULL),
    ('SERVT28', 'MRB8', 'SERV7', 120, 270000, '2024-04-01', NULL),
    ('SERVT29', 'MRB9', 'SERV6', 42, 252000, '2024-04-01', NULL),
    ('SERVT30', 'MRB9', 'SERV7', 114, 456000, '2024-04-01', NULL),
    ('SERVT31', 'MRB10', 'SERV6', 50, 300000, '2024-04-01', NULL),
    ('SERVT32', 'MRB10', 'SERV7', 125, 500000, '2024-04-01', NULL),
    ('SERVT33', 'MRB11', 'SERV6', 48, 288000, '2024-04-01', NULL),
    ('SERVT34', 'MRB11', 'SERV7', 122, 488000, '2024-04-01', NULL),
    ('SERVT35', 'MRB12', 'SERV6', 55, 330000, '2024-04-01', NULL),
    ('SERVT36', 'MRB12', 'SERV7', 118, 472000, '2024-04-01', NULL),
    ('SERVT37', 'MRB13', 'SERV6', 60, 360000, '2024-04-01', NULL),
    ('SERVT38', 'MRB13', 'SERV7', 130, 520000, '2024-04-01', NULL),
    ('SERVT39', 'MRB14', 'SERV6', 70, 420000, '2024-04-01', NULL),
    ('SERVT40', 'MRB14', 'SERV7', 140, 560000, '2024-04-01', NULL),
    ('SERVT41', 'MRB15', 'SERV6', 65, 390000, '2024-04-01', NULL),
    ('SERVT42', 'MRB15', 'SERV7', 135, 540000, '2024-04-01', NULL),
    ('SERVT43', 'MRB16', 'SERV6', 75, 450000, '2024-04-01', NULL),
    ('SERVT44', 'MRB16', 'SERV7', 145, 580000, '2024-04-01', NULL),
    ('SERVT45', 'MRB17', 'SERV6', 80, 480000, '2024-04-01', NULL),
    ('SERVT46', 'MRB17', 'SERV7', 150, 600000, '2024-04-01', NULL),
    ('SERVT47', 'MRB18', 'SERV6', 90, 540000, '2024-04-01', NULL),
    ('SERVT48', 'MRB18', 'SERV7', 160, 640000, '2024-04-01', NULL),
    ('SERVT49', 'MRB19', 'SERV6', 85, 510000, '2024-04-01', NULL),
    ('SERVT50', 'MRB19', 'SERV7', 155, 620000, '2024-04-01', NULL),
    ('SERVT51', 'MRB20', 'SERV6', 95, 570000, '2024-04-01', NULL),
    ('SERVT52', 'MRB20', 'SERV7', 165, 660000, '2024-04-01', NULL),
    ('SERVT53', 'MRB21', 'SERV6', 100, 600000, '2024-04-01', NULL),
    ('SERVT54', 'MRB21', 'SERV7', 170, 680000, '2024-04-01', NULL),
    ('SERVT55', 'MRB22', 'SERV6', 110, 660000, '2024-04-01', NULL),
    ('SERVT56', 'MRB22', 'SERV7', 180, 720000, '2024-04-01', NULL),
    ('SERVT57', 'MRB23', 'SERV6', 105, 630000, '2024-04-01', NULL),
    ('SERVT58', 'MRB23', 'SERV7', 175, 700000, '2024-04-01', NULL),
    ('SERVT59', 'MRB24', 'SERV6', 115, 690000, '2024-04-01', NULL),
    ('SERVT60', 'MRB24', 'SERV7', 185, 740000, '2024-04-01', NULL),
    ('SERVT61', 'MRB25', 'SERV6', 120, 720000, '2024-04-01', NULL),
    ('SERVT62', 'MRB25', 'SERV7', 190, 760000, '2024-04-01', NULL),
    ('SERVT63', 'MRB26', 'SERV6', 130, 780000, '2024-04-01', NULL),
    ('SERVT64', 'MRB26', 'SERV7', 195, 780000, '2024-04-01', NULL),
    ('SERVT65', 'MRB27', 'SERV6', 125, 750000, '2024-04-01', NULL),
    ('SERVT66', 'MRB27', 'SERV7', 200, 800000, '2024-04-01', NULL),
    ('SERVT67', 'MRB28', 'SERV6', 135, 810000, '2024-04-01', NULL),
    ('SERVT68', 'MRB28', 'SERV7', 205, 820000, '2024-04-01', NULL),
    ('SERVT69', 'MRB29', 'SERV6', 140, 840000, '2024-04-01', NULL),
    ('SERVT70', 'MRB29', 'SERV7', 210, 840000, '2024-04-01', NULL),
    ('SERVT71', 'MRB30', 'SERV6', 150, 900000, '2024-04-01', NULL),
    ('SERVT72', 'MRB30', 'SERV7', 215, 860000, '2024-04-01', NULL);


-- Dữ liệu cho bảng Violation
INSERT INTO Violation (violationID, name, price)
VALUES
    ('V1', N'Quá hạn thanh toán', 200000),
    ('V2', N'Gây ồn', 500000),
    ('V3', N'Cháy nổ', 2000000),
    ('V4', N'Vi phạm an ninh', 1500000)
-- Dữ liệu cho bảng ViolationTicket
    INSERT INTO ViolationTicket (violationTicketID,violationID, monthlyRentBillID, price, Date, note)
VALUES
    ('VT1','V1', 'MRB1',  700000, '2024-04-05', N'Quá hạn 5 ngày'),
    ('VT2','V2', 'MRB2',  500000, '2024-04-15', N'Đề nghị khắc phục ngay'),
    ('VT3','V4', 'MRB2', 1500000, '2024-04-12', N'Đỗ xe sai quy định'),
    ('VT4','V2', 'MRB1', 500000, '2024-04-01', N'Đổ rác bừa bãi'),
    ('VT5','V3', 'MRB3', 2000000, '2024-04-01', N'Sử dụng bếp gas không đảm bảo an toàn'),
    ('VT6','V4', 'MRB1', 1500000, '2024-04-24', N'Mang theo vật nuôi không được phép'),
    ('VT7','V1', 'MRB2', 500000, '2024-02-01', N'Quá hạn thanh toán tiền internet');


INSERT INTO StaffsAccount  (username, id, password)
VALUES
    ('TKBM1', 'BM1', '123'),
    ('TKBM2', 'BM2', '123'),
    ('TKBM3', 'BM3', '123'),
    ('TKBM4', 'BM4', '123'),
    ('TKBM5', 'BM5', '123')
    INSERT INTO CustomersAccount  (username, id, password)
VALUES
    ('TKT1', 'T1', '123'),
    ('TKT2', 'T2', '123'),
    ('TKT3', 'T3', '123'),
    ('TKT4', 'T4', '123'),
    ('TKT5', 'T5', '123');