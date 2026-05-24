package com.lab.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {
    private int id;
    private String empCode;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private LocalDate birthDate;
    private String department;
    private String position;
    private BigDecimal salary;

    public Employee() {}

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    @Override
    public String toString() {
        return String.format("%-8s %-20s %-25s %-10s %-12s %,12.0f đ",
            empCode, fullName, email, department, position,
            salary != null ? salary.doubleValue() : 0);
    }
}
