package com.lab.bai2;

import com.lab.dao.EmployeeDAO;
import com.lab.model.Employee;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * BÀI 2: Console CRUD Employee
 * Dùng PreparedStatement + ResultSet
 *
 * AI Validation (được AI gợi ý):
 * - emp_code, full_name, email bắt buộc
 * - email phải chứa @
 * - salary không âm
 * - birth_date đúng định dạng YYYY-MM-DD
 * - gender chỉ chấp nhận Male/Female/Other
 */
public class Bai2Main {

    static final EmployeeDAO dao = new EmployeeDAO();
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     BÀI 2 - CRUD Employee (Console)       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        while (true) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Danh sách nhân viên");
            System.out.println("3. Xem chi tiết nhân viên");
            System.out.println("4. Cập nhật nhân viên");
            System.out.println("5. Xóa nhân viên");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1": doCreate(); break;
                    case "2": doList();   break;
                    case "3": doDetail(); break;
                    case "4": doUpdate(); break;
                    case "5": doDelete(); break;
                    case "6":
                        System.out.println("Tạm biệt!");
                        return;
                    default:
                        System.out.println("⚠ Lựa chọn không hợp lệ.");
                }
            } catch (Exception ex) {
                System.err.println("❌ Lỗi: " + ex.getMessage());
            }
        }
    }

    // ===== CREATE =====
    static void doCreate() throws SQLException {
        System.out.println("\n--- THÊM NHÂN VIÊN ---");
        String code = readRequired("Mã NV (emp_code): ");
        String name = readRequired("Họ tên: ");
        String email = readEmail("Email: ");
        String phone = readLine("Phone: ");
        String gender = readGender("Gender (Male/Female/Other): ");
        String bd = readDate("Ngày sinh (YYYY-MM-DD, Enter bỏ qua): ");
        String dept = readLine("Phòng ban: ");
        String pos  = readLine("Vị trí: ");
        BigDecimal salary = readSalary("Lương: ");

        Employee e = new Employee();
        e.setEmpCode(code);
        e.setFullName(name);
        e.setEmail(email);
        e.setPhone(phone);
        e.setGender(gender);
        if (!bd.isEmpty()) e.setBirthDate(LocalDate.parse(bd));
        e.setDepartment(dept);
        e.setPosition(pos);
        e.setSalary(salary);

        boolean ok = dao.insert(e);
        System.out.println(ok ? "✅ Thêm thành công." : "❌ Thêm thất bại.");
    }

    // ===== LIST =====
    static void doList() throws SQLException {
        System.out.print("Tìm kiếm (Enter để xem tất cả): ");
        String search = sc.nextLine().trim();
        List<Employee> list = dao.list(search.isEmpty() ? null : search);

        System.out.println("\n--- DANH SÁCH NHÂN VIÊN ---");
        System.out.printf("%-8s %-20s %-25s %-10s %-12s %12s%n",
            "Code", "Họ tên", "Email", "PB", "Vị trí", "Lương");
        System.out.println("─".repeat(95));
        if (list.isEmpty()) {
            System.out.println("  (Không có nhân viên nào)");
        } else {
            list.forEach(System.out::println);
        }
        System.out.println("─".repeat(95));
        System.out.println("Tổng: " + list.size() + " nhân viên.");
    }

    // ===== DETAIL =====
    static void doDetail() throws SQLException {
        System.out.print("Nhập emp_code: ");
        String code = sc.nextLine().trim();
        Employee e = dao.findByCode(code);
        if (e == null) {
            System.out.println("⚠ Không tìm thấy nhân viên: " + code);
            return;
        }
        System.out.println("\n--- CHI TIẾT ---");
        System.out.println("Mã NV    : " + e.getEmpCode());
        System.out.println("Họ tên   : " + e.getFullName());
        System.out.println("Email    : " + e.getEmail());
        System.out.println("Phone    : " + e.getPhone());
        System.out.println("Gender   : " + e.getGender());
        System.out.println("Ngày sinh: " + e.getBirthDate());
        System.out.println("Phòng ban: " + e.getDepartment());
        System.out.println("Vị trí   : " + e.getPosition());
        System.out.printf ("Lương    : %,.0f đ%n", e.getSalary().doubleValue());
    }

    // ===== UPDATE =====
    static void doUpdate() throws SQLException {
        System.out.print("Nhập emp_code cần sửa: ");
        String code = sc.nextLine().trim();
        Employee e = dao.findByCode(code);
        if (e == null) {
            System.out.println("⚠ Không tìm thấy: " + code);
            return;
        }
        System.out.println("(Enter để giữ nguyên giá trị cũ)");
        String name   = readOrKeep("Họ tên [" + e.getFullName() + "]: ", e.getFullName());
        String email  = readOrKeep("Email [" + e.getEmail() + "]: ", e.getEmail());
        String phone  = readOrKeep("Phone [" + e.getPhone() + "]: ", e.getPhone());
        String gender = readOrKeep("Gender [" + e.getGender() + "]: ", e.getGender());
        String dept   = readOrKeep("PB [" + e.getDepartment() + "]: ", e.getDepartment());
        String pos    = readOrKeep("Vị trí [" + e.getPosition() + "]: ", e.getPosition());
        System.out.print("Lương [" + e.getSalary() + "]: ");
        String salStr = sc.nextLine().trim();

        e.setFullName(name);
        e.setEmail(email);
        e.setPhone(phone);
        e.setGender(gender);
        e.setDepartment(dept);
        e.setPosition(pos);
        if (!salStr.isEmpty()) {
            try { e.setSalary(new BigDecimal(salStr)); }
            catch (NumberFormatException ex) { System.out.println("⚠ Lương không hợp lệ, giữ nguyên."); }
        }

        boolean ok = dao.update(e);
        System.out.println(ok ? "✅ Cập nhật thành công." : "❌ Cập nhật thất bại.");
    }

    // ===== DELETE =====
    static void doDelete() throws SQLException {
        System.out.print("Nhập emp_code cần xóa: ");
        String code = sc.nextLine().trim();
        Employee e = dao.findByCode(code);
        if (e == null) {
            System.out.println("⚠ Không tìm thấy: " + code);
            return;
        }
        System.out.println("Bạn có chắc muốn xóa [" + e.getFullName() + "]? (yes/no): ");
        String confirm = sc.nextLine().trim();
        if (!"yes".equalsIgnoreCase(confirm)) {
            System.out.println("Đã hủy.");
            return;
        }
        boolean ok = dao.delete(code);
        System.out.println(ok ? "✅ Đã xóa." : "❌ Xóa thất bại.");
    }

    // ===== AI VALIDATION HELPERS =====

    static String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("⚠ Trường này bắt buộc nhập.");
        }
    }

    static String readEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (val.contains("@") && val.contains(".")) return val;
            System.out.println("⚠ Email không hợp lệ (phải có @ và dấu chấm).");
        }
    }

    static String readGender(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (val.isEmpty()) return "Other";
            if (val.equalsIgnoreCase("Male") || val.equalsIgnoreCase("Female") || val.equalsIgnoreCase("Other"))
                return val;
            System.out.println("⚠ Chỉ chấp nhận: Male / Female / Other");
        }
    }

    static String readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (val.isEmpty()) return "";
            try { LocalDate.parse(val); return val; }
            catch (DateTimeParseException e) {
                System.out.println("⚠ Định dạng ngày phải là YYYY-MM-DD");
            }
        }
    }

    static BigDecimal readSalary(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (val.isEmpty()) return BigDecimal.ZERO;
            try {
                BigDecimal sal = new BigDecimal(val);
                if (sal.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("⚠ Lương không được âm.");
                    continue;
                }
                return sal;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Lương phải là số.");
            }
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static String readOrKeep(String prompt, String defaultVal) {
        System.out.print(prompt);
        String val = sc.nextLine().trim();
        return val.isEmpty() ? defaultVal : val;
    }
}
