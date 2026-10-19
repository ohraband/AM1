package cz.cvut.fit.niam1.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static io.hawt.jmx.RBACRegistry.LOG;

@SpringBootApplication
public class Proxy {
    public static void main(String[] args) {
        SpringApplication.run(Proxy.class, args);
    }
}
