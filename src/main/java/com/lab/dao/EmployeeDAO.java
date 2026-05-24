package com.lab.dao;

import com.lab.model.Employee;
import com.lab.util.DBConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeDAO - Dùng PreparedStatement (chống SQL Injection)
 */
public class EmployeeDAO {

    // ===== CREATE =====
    public boolean insert(Employee e) throws SQLException {
        String sql = "INSERT INTO employees " +
                     "(emp_code, full_name, email, phone, gender, birth_date, department, position, salary) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getEmpCode());
            ps.setString(2, e.getFullName());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getPhone());
            ps.setString(5, e.getGender());
            if (e.getBirthDate() != null)
                ps.setDate(6, Date.valueOf(e.getBirthDate()));
            else
                ps.setNull(6, Types.DATE);
            ps.setString(7, e.getDepartment());
            ps.setString(8, e.getPosition());
            ps.setBigDecimal(9, e.getSalary() != null ? e.getSalary() : BigDecimal.ZERO);

            return ps.executeUpdate() == 1;
        }
    }

    // ===== READ LIST (có hỗ trợ tìm kiếm LIKE) =====
    public List<Employee> list(String search) throws SQLException {
        List<Employee> result = new ArrayList<>();

        String sql = "SELECT emp_code, full_name, email, department, position, salary FROM employees";
        boolean useSearch = search != null && !search.isBlank();
        if (useSearch)
            sql += " WHERE emp_code LIKE ? OR full_name LIKE ?";
        sql += " ORDER BY emp_code";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (useSearch) {
                String q = "%" + search.trim() + "%";
                ps.setString(1, q);
                ps.setString(2, q);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setEmpCode(rs.getString("emp_code"));
                    e.setFullName(rs.getString("full_name"));
                    e.setEmail(rs.getString("email"));
                    e.setDepartment(rs.getString("department"));
                    e.setPosition(rs.getString("position"));
                    e.setSalary(rs.getBigDecimal("salary"));
                    result.add(e);
                }
            }
        }
        return result;
    }

    // ===== READ DETAIL theo emp_code =====
    public Employee findByCode(String code) throws SQLException {
        String sql = "SELECT * FROM employees WHERE emp_code = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.setId(rs.getInt("id"));
                    e.setEmpCode(rs.getString("emp_code"));
                    e.setFullName(rs.getString("full_name"));
                    e.setEmail(rs.getString("email"));
                    e.setPhone(rs.getString("phone"));
                    e.setGender(rs.getString("gender"));
                    Date bd = rs.getDate("birth_date");
                    if (bd != null) e.setBirthDate(bd.toLocalDate());
                    e.setDepartment(rs.getString("department"));
                    e.setPosition(rs.getString("position"));
                    e.setSalary(rs.getBigDecimal("salary"));
                    return e;
                }
            }
        }
        return null;
    }

    // ===== UPDATE =====
    public boolean update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET " +
                     "full_name=?, email=?, phone=?, gender=?, birth_date=?, " +
                     "department=?, position=?, salary=?, updated_at=GETDATE() " +
                     "WHERE emp_code=?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getFullName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPhone());
            ps.setString(4, e.getGender());
            if (e.getBirthDate() != null)
                ps.setDate(5, Date.valueOf(e.getBirthDate()));
            else
                ps.setNull(5, Types.DATE);
            ps.setString(6, e.getDepartment());
            ps.setString(7, e.getPosition());
            ps.setBigDecimal(8, e.getSalary() != null ? e.getSalary() : BigDecimal.ZERO);
            ps.setString(9, e.getEmpCode());

            return ps.executeUpdate() == 1;
        }
    }

    // ===== DELETE =====
    public boolean delete(String code) throws SQLException {
        String sql = "DELETE FROM employees WHERE emp_code = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            return ps.executeUpdate() == 1;
        }
    }
}
