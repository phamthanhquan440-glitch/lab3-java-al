package com.lab.servlet;

import com.lab.dao.UserDAO;
import com.lab.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * BÀI 3: LoginServlet - Controller trong MVC
 * GET  /login -> hiển thị form đăng nhập
 * POST /login -> kiểm tra DB -> redirect home hoặc báo lỗi
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    // GET: Hiển thị trang đăng nhập
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Nếu đã login rồi thì redirect home
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loggedUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    // POST: Xử lý đăng nhập
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validation cơ bản (AI gợi ý)
        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userDAO.login(username.trim(), password.trim());
            if (user != null) {
                // Đăng nhập thành công -> lưu session
                HttpSession session = req.getSession(true);
                session.setAttribute("loggedUser", user);
                session.setMaxInactiveInterval(30 * 60); // 30 phút
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                // Sai thông tin
                req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
