// ============================================================
// Q13 — Resilience4j Circuit Breaker for payment-service call
// ============================================================

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;  // Used to call external payment-service HTTP endpoint

    // @CircuitBreaker — wraps this method with a circuit breaker named "paymentService"
    // fallbackMethod — if the circuit opens (too many failures), this fallback method is called instead
    // This prevents cascading failures: if payment-service is down, we don't crash the whole app
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public PaymentResponse processPayment(PaymentRequest request) {
        // Normal path: call external payment-service via REST
        // lb://payment-service uses Eureka-based load balancing to resolve the service
        String url = "http://payment-service/api/pay";
        return restTemplate.postForObject(url, request, PaymentResponse.class);
    }

    // Fallback method — called automatically when the circuit is open or an exception occurs
    // MUST have the same return type and same parameters as the original method + Throwable
    public PaymentResponse paymentFallback(PaymentRequest request, Throwable ex) {
        // Return a safe default response so the caller isn't left with an exception
        PaymentResponse defaultResponse = new PaymentResponse();
        defaultResponse.setStatus("FAILED");
        defaultResponse.setMessage("Payment service unavailable. Please try again later.");
        return defaultResponse;
    }
}
