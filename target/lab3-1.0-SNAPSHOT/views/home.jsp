<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; margin: 0; }
        .navbar { background: #2563eb; color: #fff; padding: 14px 32px;
                  display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 20px; }
        .navbar a { color: #fff; text-decoration: none; background: rgba(255,255,255,0.2);
                    padding: 6px 14px; border-radius: 4px; font-size: 14px; }
        .navbar a:hover { background: rgba(255,255,255,0.35); }
        .container { max-width: 700px; margin: 60px auto; text-align: center; }
        .welcome-card { background: #fff; border-radius: 10px; padding: 40px;
                        box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        .badge { display: inline-block; background: #dbeafe; color: #1d4ed8;
                 padding: 4px 12px; border-radius: 20px; font-size: 13px; margin-bottom: 16px; }
        h2 { font-size: 26px; color: #222; margin-bottom: 10px; }
        p { color: #666; font-size: 15px; }
    </style>
</head>
<body>
<div class="navbar">
    <h1>🏠 Lab3 - MVC Login</h1>
    <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
</div>

<div class="container">
    <div class="welcome-card">
        <div class="badge">${user.role}</div>
        <h2>Xin chào, ${user.fullName}! 👋</h2>
        <p>Bạn đã đăng nhập thành công với tài khoản: <strong>${user.username}</strong></p>
        <br>
        <p style="color:#aaa;font-size:13px;">
            Bài 3: JDBC + Servlet + JSP theo mô hình MVC
        </p>
    </div>
</div>
</body>
</html>
