<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f2f5;
               display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .card { background: #fff; border-radius: 10px; padding: 36px 40px;
                width: 400px; box-shadow: 0 4px 20px rgba(0,0,0,0.12); }
        h2 { text-align: center; font-size: 24px; margin-bottom: 24px; color: #222; }
        .error-box { background: #ffe0e0; border: 1px solid #f5a5a5; border-radius: 6px;
                     padding: 10px 14px; color: #c0392b; margin-bottom: 16px; font-size: 14px; }
        label { display: block; margin-bottom: 6px; font-size: 14px; color: #444; }
        input[type=text], input[type=password] {
            width: 100%; padding: 10px 12px; border: 1px solid #ccc;
            border-radius: 6px; font-size: 15px; margin-bottom: 18px;
            outline: none; transition: border-color 0.2s;
        }
        input:focus { border-color: #3b82f6; }
        button {
            width: 100%; padding: 12px; background: #2563eb; color: #fff;
            border: none; border-radius: 6px; font-size: 16px; font-weight: bold;
            cursor: pointer; letter-spacing: 1px; transition: background 0.2s;
        }
        button:hover { background: #1d4ed8; }
        .hint { text-align: center; font-size: 12px; color: #888; margin-top: 16px; }
    </style>
</head>
<body>
<div class="card">
    <h2>Đăng Nhập</h2>

    <%-- Hiển thị lỗi nếu có --%>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error-box">${error}</div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Tên đăng nhập</label>
        <input type="text" name="username"
               value="${username != null ? username : ''}"
               placeholder="admin" required autofocus>

        <label>Mật khẩu</label>
        <input type="password" name="password" placeholder="••••••" required>

        <button type="submit">Đăng nhập</button>
    </form>

    <p class="hint">Demo: admin / admin123 &nbsp;|&nbsp; user1 / user123</p>
</div>
</body>
</html>
