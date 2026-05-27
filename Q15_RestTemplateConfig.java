// ============================================================
// Q15 — @LoadBalanced RestTemplate Bean
// Enables Eureka-based service name resolution
// ============================================================

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration       // Marks this class as a source of Spring bean definitions
public class AppConfig {

    @Bean            // Registers this RestTemplate as a Spring-managed bean
    @LoadBalanced    // This is the KEY annotation — it intercepts URLs like "http://order-service/..."
                     // and uses Eureka to look up the real IP:port of "order-service"
                     // WITHOUT @LoadBalanced, "order-service" would be treated as a hostname and fail
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

// Usage example in another service:
// @Autowired RestTemplate restTemplate;
// restTemplate.getForObject("http://order-service/api/orders", List.class);
// ↑ Eureka resolves "order-service" to actual address automatically
