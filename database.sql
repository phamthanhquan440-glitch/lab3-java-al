-- ============================================
-- LAB 3 - SQL Server Script
-- Chạy script này trong SQL Server Management Studio (SSMS)
-- hoặc Azure Data Studio trước khi chạy project
-- ============================================

-- Tạo database (nếu chưa có)
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'lab3db')
    CREATE DATABASE lab3db;
GO

USE lab3db;
GO

-- ============================================
-- Bài 1: Bảng departments và employees (Statement)
-- ============================================
IF OBJECT_ID('employees', 'U') IS NOT NULL DROP TABLE employees;
IF OBJECT_ID('departments', 'U') IS NOT NULL DROP TABLE departments;

CREATE TABLE departments (
    id          INT IDENTITY(1,1) PRIMARY KEY,
    dept_code   VARCHAR(10) NOT NULL UNIQUE,
    dept_name   VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
    id            INT IDENTITY(1,1) PRIMARY KEY,
    emp_code      VARCHAR(10)  NOT NULL UNIQUE,
    full_name     VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    phone         VARCHAR(20),
    gender        VARCHAR(10)  DEFAULT 'Other',
    birth_date    DATE,
    department    VARCHAR(50),
    position      VARCHAR(50),
    salary        DECIMAL(12,2) DEFAULT 0.00,
    created_at    DATETIME DEFAULT GETDATE(),
    updated_at    DATETIME DEFAULT GETDATE()
);

-- ============================================
-- Bảng users - dùng cho Bài 3 (Login)
-- ============================================
IF OBJECT_ID('users', 'U') IS NOT NULL DROP TABLE users;
CREATE TABLE users (
    id        INT IDENTITY(1,1) PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,  -- lưu plain text để demo, thực tế nên hash
    full_name VARCHAR(100),
    role      VARCHAR(20) DEFAULT 'user'
);

-- ============================================
-- Dữ liệu mẫu - departments
-- ============================================
INSERT INTO departments (dept_code, dept_name) VALUES
('IT',  N'Phòng Công nghệ thông tin'),
('HR',  N'Phòng Nhân sự'),
('MKT', N'Phòng Marketing'),
('ACC', N'Phòng Kế toán');

-- ============================================
-- Dữ liệu mẫu - employees
-- ============================================
INSERT INTO employees (emp_code, full_name, email, phone, gender, birth_date, department, position, salary) VALUES
('E001', N'Nguyễn Văn An',    'an.nv@lab.com',     '0901111111', 'Male',   '1995-03-15', 'IT',  N'Developer',  15000000),
('E002', N'Trần Thị Bình',    'binh.tt@lab.com',   '0902222222', 'Female', '1997-07-20', 'IT',  N'Tester',     12000000),
('E003', N'Lê Văn Cường',     'cuong.lv@lab.com',  '0903333333', 'Male',   '1993-11-05', 'HR',  N'HR Manager', 18000000),
('E004', N'Phạm Thị Dung',    'dung.pt@lab.com',   '0904444444', 'Female', '1998-02-28', 'HR',  N'HR Staff',   10000000),
('E005', N'Hoàng Văn Em',     'em.hv@lab.com',     '0905555555', 'Male',   '1996-09-12', 'MKT', N'Designer',   13000000),
('E006', N'Vũ Thị Phương',    'phuong.vt@lab.com', '0906666666', 'Female', '1994-06-30', 'ACC', N'Accountant', 14000000);

-- ============================================
-- Dữ liệu mẫu - users (Bài 3 Login)
-- admin/admin123   user/user123
-- ============================================
INSERT INTO users (username, password, full_name, role) VALUES
('admin', 'admin123', N'Quản trị viên', 'admin'),
('user1', 'user123',  N'Người dùng 1',  'user'),
('user2', 'user123',  N'Người dùng 2',  'user');

SELECT 'Database setup hoàn tất!' AS Result;
GO
