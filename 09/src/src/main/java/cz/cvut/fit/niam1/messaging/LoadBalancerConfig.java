package cz.cvut.fit.niam1.messaging;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
@ServletComponentScan
public class LoadBalancerConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
