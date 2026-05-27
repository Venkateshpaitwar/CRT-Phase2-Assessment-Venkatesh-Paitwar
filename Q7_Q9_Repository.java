// ============================================================
// Q7 — Spring Data JPA Repository: Find Products by Price & Category
// Q9 — Find Second Highest Salary (SQL + JPQL)
// ============================================================

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


// ============================================================
// Q7 — ProductRepository
// @Query with named parameters (:price, :category)
// "p" is an alias for the Product entity in JPQL
// ============================================================
public interface ProductRepository extends JpaRepository<Product, Long> {

    // JPQL query — uses entity/field names (not SQL table/column names)
    // :price and :category are named parameters bound using @Param
    @Query("SELECT p FROM Product p WHERE p.price < :price AND p.category = :category")
    List<Product> findByPriceLessThanAndCategory(
            @Param("price") double price,           // binds method arg to :price placeholder
            @Param("category") String category      // binds method arg to :category placeholder
    );
}


// ============================================================
// Q9 — EmployeeRepository
// Second highest salary via subquery (SQL and JPQL)
// ============================================================
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // --- SQL version (for reference) ---
    // SELECT MAX(salary) FROM employees
    // WHERE salary < (SELECT MAX(salary) FROM employees);

    // --- JPQL version using @Query ---
    // MAX() inside WHERE: excludes the absolute maximum, then finds the new max
    // nativeQuery = false (default) means this is JPQL, not raw SQL
    @Query("SELECT MAX(e.salary) FROM Employee e WHERE e.salary < " +
           "(SELECT MAX(e2.salary) FROM Employee e2)")
    Optional<Double> findSecondHighestSalary();
    // Returns Optional<Double> to handle the case where fewer than 2 employees exist
}
