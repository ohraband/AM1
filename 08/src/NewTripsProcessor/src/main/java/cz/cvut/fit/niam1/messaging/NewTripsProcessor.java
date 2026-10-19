package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication
@EnableJms
public class NewTripsProcessor {

    private final Logger logger = LoggerFactory.getLogger(NewTripsProcessor.class);

    @JmsListener(destination = "newTripsQueue")
    public void readNewTripsMessage(String message) throws InterruptedException {
        logger.info("Received a new message in NewTrips: {}", message);
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        SpringApplication.run(NewTripsProcessor.class, args);
    }
}
