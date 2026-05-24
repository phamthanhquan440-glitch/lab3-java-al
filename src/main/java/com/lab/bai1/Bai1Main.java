package com.lab.bai1;

import com.lab.util.DBConfig;

import java.sql.*;

/**
 * BÀI 1: JDBC + Statement
 * - Truy vấn toàn bộ nhân viên
 * - Lọc phòng ban có số nhân viên >= 2
 * - Thống kê tên phòng ban | số lượng nhân viên
 *
 * AI Validation (yêu cầu AI):
 * - Kiểm tra kết nối DB trước khi truy vấn
 * - Kiểm tra ResultSet không null trước khi đọc
 * - Validate dữ liệu số (salary không âm)
 */
public class Bai1Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         BÀI 1 - JDBC + Statement          ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // 1. Truy vấn toàn bộ nhân viên
        queryAllEmployees();

        // 2. Phòng ban có số nhân viên >= 2
        queryDeptWithMinEmployees(2);

        // 3. Thống kê tên phòng ban | số lượng nhân viên
        statsByDepartment();
    }

    /** 1. Lấy toàn bộ dữ liệu từ bảng employees */
    static void queryAllEmployees() {
        System.out.println("\n📋 1. DANH SÁCH NHÂN VIÊN");
        System.out.println("─".repeat(70));
        System.out.printf("%-6s %-20s %15s %-10s%n", "ID", "Tên", "Lương", "Mã PB");
        System.out.println("─".repeat(70));

        // Dùng Statement (không có tham số động)
        String sql = "SELECT id, full_name, salary, department FROM employees ORDER BY id";

        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // AI Validation: kiểm tra có dữ liệu không
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int id         = rs.getInt("id");
                String name    = rs.getString("full_name");
                double salary  = rs.getDouble("salary");
                String dept    = rs.getString("department");

                // Validate: salary không được âm
                String salaryStr = salary < 0 ? "⚠ LỖI" : String.format("%,.0f đ", salary);
                System.out.printf("%-6d %-20s %15s %-10s%n", id, name, salaryStr, dept);
            }
            if (!hasData) System.out.println("  (Không có dữ liệu)");
            System.out.println("─".repeat(70));

        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối/truy vấn: " + e.getMessage());
        }
    }

    /** 2. Lọc phòng ban có số nhân viên >= minCount */
    static void queryDeptWithMinEmployees(int minCount) {
        System.out.println("\n📊 2. PHÒNG BAN CÓ SỐ NHÂN VIÊN >= " + minCount);
        System.out.println("─".repeat(40));
        System.out.printf("%-15s %-10s%n", "Mã phòng ban", "Số NV");
        System.out.println("─".repeat(40));

        // Dùng Statement với GROUP BY + HAVING
        String sql = "SELECT department, COUNT(*) AS so_nv " +
                     "FROM employees " +
                     "GROUP BY department " +
                     "HAVING COUNT(*) >= " + minCount + " " +
                     "ORDER BY so_nv DESC";

        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String dept = rs.getString("department");
                int count   = rs.getInt("so_nv");
                System.out.printf("%-15s %-10d%n", dept, count);
            }
            System.out.println("─".repeat(40));

        } catch (SQLException e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
        }
    }

    /** 3. Thống kê: JOIN employees + departments */
    static void statsByDepartment() {
        System.out.println("\n📈 3. THỐNG KÊ THEO PHÒNG BAN");
        System.out.println("─".repeat(55));
        System.out.printf("%-30s %-10s %15s%n", "Tên phòng ban", "Số NV", "Lương TB");
        System.out.println("─".repeat(55));

        // JOIN bảng departments để lấy tên đầy đủ
        String sql = "SELECT d.dept_name, COUNT(e.id) AS so_nv, AVG(e.salary) AS luong_tb " +
                     "FROM departments d " +
                     "LEFT JOIN employees e ON e.department = d.dept_code " +
                     "GROUP BY d.dept_name " +
                     "ORDER BY so_nv DESC";

        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String deptName = rs.getString("dept_name");
                int count       = rs.getInt("so_nv");
                double avgSal   = rs.getDouble("luong_tb");

                // Validate: nếu không có nhân viên thì avgSal = 0
                String avgStr = count == 0 ? "N/A" : String.format("%,.0f đ", avgSal);
                System.out.printf("%-30s %-10d %15s%n", deptName, count, avgStr);
            }
            System.out.println("─".repeat(55));

        } catch (SQLException e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
        }

        System.out.println("\n✅ Bài 1 hoàn thành.");
    }
}
