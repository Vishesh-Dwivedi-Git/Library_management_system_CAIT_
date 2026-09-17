package model;

import java.util.Objects;

/**
 * Represents a librarian who manages the library operations.
 * Extends Person to inherit common person attributes.
 */
public class Librarian extends Person {

    private String employeeId;
    private String department;
    private double salary;

    // Default constructor
    public Librarian() {
        super();
    }

    // Parameterized constructor
    public Librarian(String id, String name, String email, String phoneNumber,
                     String employeeId, String department, double salary) {
        super(id, name, email, phoneNumber);
        this.employeeId = employeeId;
        this.department = department;
        this.salary = salary;
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String getRole() {
        return "LIBRARIAN";
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Librarian librarian = (Librarian) o;
        return Objects.equals(employeeId, librarian.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employeeId);
    }
}
