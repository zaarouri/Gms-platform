package org.sid.apiconsumption_service.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.models.ApiModel;
import org.sid.apiconsumption_service.services.ApiConsumerService;
import org.sid.apiconsumption_service.services.ApiConsumerServiceFactory;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/consume")
@RequiredArgsConstructor
public class ApiConsumptionController {

    private final ApiConsumerServiceFactory serviceFactory;
    private final ApiModelRestClient apiModelRestClient;

    @RequestMapping(value = "/{apiId}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<String> consumeApi(
            @PathVariable String apiId,
            @RequestBody(required = false) String requestBody,
            @RequestParam Map<String, String> queryParams,
            @RequestHeader Map<String, String> headers) {

        // Fetch API details from the management service
        ApiModel apiModel = apiModelRestClient.getById(apiId);

        // Determine the service implementation based on the API type
        ApiConsumerService service = serviceFactory.getService(apiModel);

        // Use the selected service to process the request
        return service.consumeApi(apiId, requestBody, queryParams, headers);
    }
}
