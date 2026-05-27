// ============================================================
// Q17 — JUnit 5 + Mockito Unit Test for ProductService.getById()
// ============================================================

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class) — activates Mockito annotations in this test class
// Without this, @Mock and @InjectMocks would not be processed
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    // @Mock — creates a fake (mock) ProductRepository
    // It does not hit any real database; we control what it returns in each test
    @Mock
    private ProductRepository repo;

    // @InjectMocks — creates a real ProductService instance
    // and automatically injects the @Mock repo into it
    @InjectMocks
    private ProductService productService;

    @Test
    void testGetById_returnsCorrectProduct() {
        // --- Arrange ---
        // Create a sample Product with name "Laptop"
        Product sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Laptop");

        // Stub the mock: when repo.findById(1L) is called, return our sample product
        // Optional.of() wraps it because findById returns Optional<Product>
        when(repo.findById(1L)).thenReturn(Optional.of(sampleProduct));

        // --- Act ---
        // Call the real service method (which internally calls the mocked repo)
        Product result = productService.getById(1L);

        // --- Assert ---
        // Verify the returned product name is exactly "Laptop"
        assertEquals("Laptop", result.getName(), "Product name should be Laptop");
    }
}
