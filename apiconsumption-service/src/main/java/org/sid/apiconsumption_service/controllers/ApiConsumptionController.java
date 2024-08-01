package org.sid.apiconsumption_service.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.services.ApiConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/consume")
@RequiredArgsConstructor
public class ApiConsumptionController {

    private final ApiConsumerService apiConsumptionService;



    @RequestMapping(value = "/{apiId}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<String> consumeApi(
            @PathVariable String apiId,
            @RequestBody(required = false) String requestBody,
            @RequestParam Map<String, String> queryParams,
            @RequestHeader Map<String, String> headers) {

        return apiConsumptionService.consumeApi(apiId, requestBody, queryParams, headers);
    }
}