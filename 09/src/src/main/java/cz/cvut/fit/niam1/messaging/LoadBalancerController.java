package cz.cvut.fit.niam1.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.hawt.jmx.RBACRegistry.LOG;

@WebServlet("/load-balancer")
public class LoadBalancerController extends HttpServlet {

    private final String[] serviceUrls = {
            "http://ni-am.fit.cvut.cz:8888/MI-MDW-LastMinute1/list",
            "http://ni-am.fit.cvut.cz:8888/MI-MDW-LastMinute2/list",
            "http://ni-am.fit.cvut.cz:8888/MI-MDW-LastMinute3/list"
    };

    private final List<String> healthyServices = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void initialize() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::monitorServices, 0, 10, TimeUnit.SECONDS);
    }

    private void monitorServices() {
        for (String serviceUrl : serviceUrls) {
            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(new URI(serviceUrl), HttpMethod.GET, null, String.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    if (!healthyServices.contains(serviceUrl)) {
                        healthyServices.add(serviceUrl);
                    }
                } else {
                    healthyServices.remove(serviceUrl);
                }
            } catch (Exception e) {
                healthyServices.remove(serviceUrl);
            }
        }
        LOG.info("Healthy services: " + healthyServices);
    }

    // curl http://localhost:8080/proxy-servlet/load-balancer
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Choose a healthy service
        String selectedService = getNextHealthyService();
        LOG.info("Forwarding to selected URL: " + selectedService);

        // Forward the request
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URI.create(selectedService), String.class);

        // Write the response
        resp.getWriter().write(responseEntity.getBody());
    }

    private String getNextHealthyService() {
        if (healthyServices.isEmpty()) {
            throw new IllegalStateException("No healthy services available");
        }

        String selectedService = healthyServices.remove(0);
        healthyServices.add(selectedService);
        return selectedService;
    }
}
