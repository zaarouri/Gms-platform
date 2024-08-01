package org.sid.apiconsumption_service;

import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.models.ApiModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ApiconsumptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiconsumptionServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ApiModelRestClient client) {
        return args -> {
            for (ApiModel apiModel : client.getAll()) {
                System.out.println(apiModel.getUrl());
                System.out.println(apiModel.getHttpMethod());
                System.out.println(apiModel.getId());

            }

        };
    }
}
