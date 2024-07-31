package org.sid.apiconsumption_service.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.models.ApiModel;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
@Service
@Transactional
@RequiredArgsConstructor
public class RestApiConsumptionServiceImpl implements ApiConsumerService {

    private final RestTemplate restTemplate;
    private final ApiModelRestClient apiModelRestClient;



    @Override
    public String consumeApi(String apiId, Map<String, String> parameters) {
        ApiModel api = apiModelRestClient.getById(apiId);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        api.getHeaders().forEach(headers::set);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api.getUrl());
        parameters.forEach(builder::queryParam);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.valueOf(api.getMethod()),
                entity,
                String.class
        );

        return response.getBody();
    }

    @Override
    public boolean supports(ApiModel apiModel) {
        return "REST".equalsIgnoreCase(apiModel.getType());
    }
}
