package santorini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for the Santorini web application.
 * Replaces the original Swing-based Application.java entry point.
 */
@SpringBootApplication
public class SantoriniApplication {

    public static void main(String[] args) {
        SpringApplication.run(SantoriniApplication.class, args);
    }
}
