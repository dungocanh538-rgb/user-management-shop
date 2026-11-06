-- Database for UserManagement_T4S1_JSP (SQL Server)
-- Run this whole file in SQL Server Management Studio.
-- Note: demo accounts use simple passwords for testing only.

USE [master]
GO
IF DB_ID('UserManagement') IS NOT NULL
    DROP DATABASE UserManagement;
GO
CREATE DATABASE [UserManagement]
GO
USE [UserManagement]
GO

CREATE TABLE [dbo].[tblUsers] (
    [uID]      NVARCHAR(50)  NOT NULL PRIMARY KEY,
    [name]     NVARCHAR(50)  NULL,
    [email]    NVARCHAR(50)  NULL,
    [password] NVARCHAR(50)  NULL,
    [roleID]   NVARCHAR(50)  NULL,   -- 'AD' = Admin, 'US' = User
    [status]   BIT           NULL    -- 1 = active, 0 = locked
);
GO

CREATE TABLE [dbo].[Product] (
    [id]       NVARCHAR(50)  NOT NULL PRIMARY KEY,
    [name]     NVARCHAR(100) NOT NULL,
    [price]    FLOAT         NOT NULL,
    [quantity] INT           NOT NULL   -- stock
);
GO

CREATE TABLE [dbo].[Cart] (
    [uID]      NVARCHAR(50) NOT NULL REFERENCES tblUsers(uID),
    [pID]      NVARCHAR(50) NOT NULL REFERENCES Product(id),
    [quantity] INT          NOT NULL,
    PRIMARY KEY (uID, pID)
);
GO

CREATE TABLE [dbo].[Orders] (
    [oID]       INT IDENTITY(1,1) PRIMARY KEY,
    [uID]       NVARCHAR(50) NOT NULL REFERENCES tblUsers(uID),
    [total]     FLOAT        NOT NULL,
    [orderDate] DATETIME     NOT NULL DEFAULT GETDATE()
);
GO

CREATE TABLE [dbo].[OrderDetail] (
    [id]       INT IDENTITY(1,1) PRIMARY KEY,
    [oID]      INT          NOT NULL REFERENCES Orders(oID),
    [pID]      NVARCHAR(50) NOT NULL REFERENCES Product(id),
    [price]    FLOAT        NOT NULL,
    [quantity] INT          NOT NULL
);
GO

-- Demo accounts
INSERT INTO tblUsers (uID, name, password, roleID, status, email) VALUES
 (N'admin',    N'Admin',             N'1',   N'AD', 1, NULL),
 (N'Hoadnt',   N'Hoa Doan',          N'1',   N'US', 1, NULL),
 (N'SE130363', N'Ngo Ha Tri Bao',    N'1',   N'AD', 1, NULL),
 (N'SE140103', N'Phuoc Ha',          N'1',   N'US', 1, NULL),
 (N'SE140969', N'Nguyen Gia Tin',    N'123', N'US', 1, NULL),
 (N'SE150443', N'LE MINH KHOA',      N'1',   N'US', 1, NULL);
GO

-- Demo drinks
INSERT INTO Product (id, name, price, quantity) VALUES
 (N'P01', N'Espresso',          2.50, 50),
 (N'P02', N'Cappuccino',        3.20, 40),
 (N'P03', N'Latte',             3.50, 40),
 (N'P04', N'Vietnamese Iced Coffee', 2.80, 60),
 (N'P05', N'Peach Tea',         3.00, 30),
 (N'P06', N'Matcha Latte',      3.80, 25),
 (N'P07', N'Orange Juice',      2.90, 20),
 (N'P08', N'Mango Smoothie',    3.60, 20),
 (N'P09', N'Hot Chocolate',     3.10, 30),
 (N'P10', N'Lemonade',          2.40, 35),
 (N'P11', N'Milk Tea',          3.30, 45),
 (N'P12', N'Mineral Water',     1.00, 100);
GO
