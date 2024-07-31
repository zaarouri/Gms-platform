package org.sid.apiconsumption_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiconsumptionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiconsumptionServiceApplication.class, args);
	}

}
