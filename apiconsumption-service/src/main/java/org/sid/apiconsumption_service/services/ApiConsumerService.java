package org.sid.apiconsumption_service.services;


import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface ApiConsumerService {

    public ResponseEntity<String> consumeApi(String apiId, String requestBody, Map<String, String> queryParams, Map<String, String> headers) ;
}
