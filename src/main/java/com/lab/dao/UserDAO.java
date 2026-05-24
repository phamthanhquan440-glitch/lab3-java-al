package com.lab.dao;

import com.lab.model.User;
import com.lab.util.DBConfig;

import java.sql.*;

public class UserDAO {

    /**
     * Kiểm tra đăng nhập bằng PreparedStatement (tránh SQL Injection)
     * Trả về User nếu đúng, null nếu sai
     */
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT id, username, full_name, role FROM users " +
                     "WHERE username = ? AND password = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("role")
                    );
                }
            }
        }
        return null;
    }
}
