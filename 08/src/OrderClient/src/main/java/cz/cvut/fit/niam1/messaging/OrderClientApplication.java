package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableJms
public class OrderClientApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(OrderClientApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    public int messagetype = 0;


    public static void main(String[] args) {
        SpringApplication.run(OrderClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Thread.sleep(5000);
            LocalDateTime time = LocalDateTime.now();
            messagetype++;
            if (messagetype % 2 == 0){
                logger.info("Booking order to {} at {}", queue.getQueueName(), time);
                jmsTemplate.convertAndSend(queue, "Booking order at " + time);
            }
            else{
                logger.info("New trip order to {} at {}", queue.getQueueName(), time);
                jmsTemplate.convertAndSend(queue, "New trip order at " + time);
            }

        }
    }

}
