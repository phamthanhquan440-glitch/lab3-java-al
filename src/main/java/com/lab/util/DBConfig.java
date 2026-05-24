package com.lab.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConfig - Kết nối SQL Server
 *
 * ⚠️ SỬA 3 DÒNG NÀY TRƯỚC KHI CHẠY:
 *   DB_NAME : tên database của bạn
 *   DB_USER : tên đăng nhập SQL Server
 *   DB_PASS : mật khẩu SQL Server
 *
 * Nếu dùng Windows Authentication (không cần user/pass), đổi URL thành:
 *   jdbc:sqlserver://localhost:1433;databaseName=lab3db;
 *   integratedSecurity=true;encrypt=false;trustServerCertificate=true
 */
public class DBConfig {

    private static final String DB_HOST = "LAPTOP-G3DPIH59";
    private static final int    DB_PORT = 1433;
    private static final String DB_NAME = "databaselab3javaal";       // ← SỬA tên DB
    private static final String DB_USER = "SA";           // ← SỬA username
    private static final String DB_PASS = "123456";       // ← SỬA password

    private static final String URL =
        "jdbc:sqlserver://" + DB_HOST + ":" + DB_PORT
        + ";databaseName=" + DB_NAME
        + ";encrypt=false;trustServerCertificate=true";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy SQL Server JDBC Driver!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASS);
    }
}
