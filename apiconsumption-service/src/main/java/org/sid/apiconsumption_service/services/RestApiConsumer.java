package org.sid.apiconsumption_service.services;

import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.models.ApiModel;
import org.sid.apiconsumption_service.models.ApiParameter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RestApiConsumer implements ApiConsumerService {

    private final RestTemplate restTemplate;
    private final ApiModelRestClient apiClient;

    @Override
    public ResponseEntity<String> consumeApi(String apiId, String requestBody, Map<String, String> queryParams, Map<String, String> headers) {
        ApiModel apiModel = apiClient.getById(apiId);
        //todo check if the user has the right to use this api
        HttpHeaders httpHeaders = new HttpHeaders();

        // Add default headers from API model
        httpHeaders.setAll(apiModel.getHeaders());

        // Add or override with provided headers
        headers.forEach(httpHeaders::add);

        // Handle Basic Authentication if username and password are provided
        if (apiModel.getUsername() != null && apiModel.getPassword() != null) {
            String auth = apiModel.getUsername() + ":" + apiModel.getPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            httpHeaders.add("Authorization", "Basic " + new String(encodedAuth));
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiModel.getUrl());

        // Add default parameters from API model
        for (ApiParameter param : apiModel.getParameters()) {
            if (param.getDefaultValue() != null) {
                builder.queryParam(param.getName(), param.getDefaultValue());
            }
        }

        // Add or override with provided query parameters
        queryParams.forEach(builder::queryParam);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);

        HttpMethod httpMethod;
        try {
            httpMethod = HttpMethod.valueOf(apiModel.getHttpMethod().toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid HTTP method: " + apiModel.getHttpMethod(), e);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                httpMethod,
                entity,
                String.class
        );
    }
}