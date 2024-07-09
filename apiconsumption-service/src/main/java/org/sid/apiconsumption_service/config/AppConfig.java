package org.sid.apiconsumption_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class AppConfig {
    @Configuration
    public class ApplicationConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

//        @Bean
//        public WebServiceTemplate webServiceTemplate() {
//            return new WebServiceTemplateBuilder();
//        }
    }
}
