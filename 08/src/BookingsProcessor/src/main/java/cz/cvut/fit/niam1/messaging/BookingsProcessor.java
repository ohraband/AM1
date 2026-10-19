package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class BookingsProcessor {

    private final Logger logger = LoggerFactory.getLogger(BookingsProcessor.class);

    @JmsListener(destination = "bookingsQueue")
    public void readBookingMessage(String message) throws InterruptedException {
        logger.info("Received a new message in bookings: {}", message);
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingsProcessor.class, args);
    }

}
