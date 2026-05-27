// ============================================================
// Q10 — Spring Data JPA Derived Query Methods
// No @Query needed — Spring generates SQL from the method names
// Entity: Student(name, email, city)
// ============================================================

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // (a) Find all students who live in a specific city
    // Spring parses "findBy" + "City" → WHERE city = ?1
    List<Student> findByCity(String city);

    // (b) Find students matching BOTH name AND city
    // "And" in the method name generates AND in the SQL WHERE clause
    List<Student> findByNameAndCity(String name, String city);

    // (c) Find students whose email contains a given keyword (case-sensitive by default)
    // "Containing" → WHERE email LIKE %keyword%
    List<Student> findByEmailContaining(String keyword);

    // (d) Count how many students are in a specific city
    // Spring uses COUNT(*) instead of SELECT * because method starts with "count"
    long countByCity(String city);
}
