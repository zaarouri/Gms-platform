package org.sid.apiconsumption_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {


    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
   /* @Bean
    public WebServiceTemplate webServiceTemplate() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.afterPropertiesSet();

        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
        // No need to set marshaller/unmarshaller if working with raw XML
        return webServiceTemplate;
    }   */

}
