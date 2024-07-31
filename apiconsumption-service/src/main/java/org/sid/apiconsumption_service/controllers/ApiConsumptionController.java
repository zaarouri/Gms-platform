package org.sid.apiconsumption_service.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.services.ApiConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/consume")
@RequiredArgsConstructor
public class ApiConsumptionController {
    private final ApiConsumerService apiConsumerService;
    @GetMapping("/test")
    String test() {
        return "test";
    }
//    public (ApiConsumerService apiConsumerService) {}

    @PostMapping("/{apiId}")
    public ResponseEntity<String> consumeApi(@PathVariable String apiId, @RequestBody Map<String, String> parameters) {
    String result = apiConsumerService.consumeApi(apiId, parameters);
    return ResponseEntity.ok(result);
}

}
