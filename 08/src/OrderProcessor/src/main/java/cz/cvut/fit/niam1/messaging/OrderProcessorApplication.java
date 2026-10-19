package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Queue;
import java.time.LocalDateTime;


@SpringBootApplication
@EnableJms
public class OrderProcessorApplication {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue bookingsQueue;

    @Autowired
    private Queue newTripsQueue;

    private final Logger logger = LoggerFactory.getLogger(OrderProcessorApplication.class);

    @JmsListener(destination = "allOrdersQueue")
    public void readMessage(String message) throws InterruptedException, JMSException {
        LocalDateTime time = LocalDateTime.now();
        logger.info("Received a new message in AllOrders: {}", message);

        // Choose the correct queue
        if (message.contains("Booking")) {
            logger.info("Sending a Booking message to {} at {}", bookingsQueue.getQueueName(), time);
            jmsTemplate.convertAndSend(bookingsQueue, message);
        } else {
            logger.info("Sending a New trip message to {} at {}", newTripsQueue.getQueueName(), time);
            jmsTemplate.convertAndSend(newTripsQueue, message);
        }

        Thread.sleep(2000);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessorApplication.class, args);
    }
}








