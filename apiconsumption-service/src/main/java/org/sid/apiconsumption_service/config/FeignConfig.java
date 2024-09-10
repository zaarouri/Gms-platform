package org.sid.apiconsumption_service.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        // Set a retryer that will retry failed requests
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(5000, 30000); // 5s connection, 30s read timeout
    }
}
