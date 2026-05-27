// ============================================================
// Q12 — Main class for product-service
// @EnableDiscoveryClient registers this app with Eureka
// ============================================================

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication          // Combines @Configuration, @EnableAutoConfiguration, @ComponentScan
@EnableDiscoveryClient          // Activates Eureka client — tells Spring to register with Eureka server
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
