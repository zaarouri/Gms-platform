package org.sid.apiconsumption_service.controllers;

import org.sid.apiconsumption_service.services.ApiConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/consume")
public class ApiConsumptionController {
    private ApiConsumerService apiConsumerService;
    @GetMapping("/test")
    String test() {
        return "test";
    }

}
