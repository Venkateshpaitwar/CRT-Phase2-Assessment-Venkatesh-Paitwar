// ============================================================
// Q5 — JPA Entities: Employee (Many) ↔ Department (One)
// ============================================================

import jakarta.persistence.*;
import java.util.List;

// ============================================================
// Department Entity — the "One" side of the relationship
// One Department can have MANY Employees
// ============================================================

@Entity                                   // Marks this class as a JPA-managed table
@Table(name = "department")               // Maps to the "department" table in the database
public class Department {

    @Id                                   // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    private Long id;

    private String name;                  // Department name column

    // mappedBy = "department" refers to the field name in the Employee class
    // This side does NOT own the foreign key column — Employee does
    // CascadeType.ALL means save/delete operations cascade to employees
    // fetch = LAZY means employees are loaded only when accessed (performance best practice)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}


// ============================================================
// Employee Entity — the "Many" side of the relationship
// Many Employees belong to ONE Department
// ============================================================

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated primary key
    private Long id;

    private String name;

    private double salary;

    // @ManyToOne — this is the owning side; it holds the foreign key column (department_id)
    // @JoinColumn specifies the actual foreign key column name in the employee table
    @ManyToOne(fetch = FetchType.LAZY)       // LAZY: load department only when needed
    @JoinColumn(name = "department_id")      // Creates column "department_id" in "employee" table
    private Department department;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
