// ============================================================
// Q3 — Spring Boot REST Endpoint: POST /api/students
// Accepts JSON with 'name' (not blank) and 'age' (>= 18)
// Returns 400 Bad Request on validation failure, 201 on success
// ============================================================

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// DTO (Data Transfer Object) — represents the incoming JSON body
// Annotations define the validation rules
public class StudentDTO {

    @NotBlank(message = "Name must not be blank")   // rejects null, empty, or whitespace-only strings
    private String name;

    @Min(value = 18, message = "Age must be 18 or above")  // rejects any value less than 18
    private int age;

    // Getters and setters (required by Jackson for JSON deserialization)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}


// ============================================================
// Controller — handles the HTTP POST request
// ============================================================

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController                             // Marks this as a REST controller (combines @Controller + @ResponseBody)
@RequestMapping("/api/students")            // Base URL for all endpoints in this class
public class StudentController {

    @PostMapping                             // Maps HTTP POST /api/students to this method
    public ResponseEntity<String> createStudent(
            @Valid @RequestBody StudentDTO dto,   // @Valid triggers the bean validation annotations on StudentDTO
            BindingResult result) {               // BindingResult captures any validation errors

        if (result.hasErrors()) {
            // Build a readable error message from all validation failures
            String errors = result.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(java.util.stream.Collectors.joining("; "));

            // Return 400 Bad Request with the error message as the response body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + errors);
        }

        // If validation passes, return 201 Created with a success message
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student created: " + dto.getName());
    }
}
