package org.sid.apiconsumption_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapFaultException;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;


import java.util.Map;

@Configuration
public class AppConfig {


    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

        // Optionally configure a custom MessageFactory
        // webServiceTemplate.setMessageFactory(new SaajSoapMessageFactory());

        // Optionally configure Marshaller and Unmarshaller
        webServiceTemplate.setMarshaller(new Jaxb2Marshaller());
        webServiceTemplate.setUnmarshaller(new Jaxb2Marshaller());

        return webServiceTemplate;
    }

   /* @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver resolver = new SoapFaultMappingExceptionResolver();
        resolver.setDefaultFault("Server");
        resolver.setExceptionMappings(Map.of(
                SoapFaultException.class.getName(), "Server"
        ));
        return resolver;
    }
*/
}
