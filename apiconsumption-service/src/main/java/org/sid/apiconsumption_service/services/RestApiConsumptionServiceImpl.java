package org.sid.apiconsumption_service.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.models.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
@Transactional

@RequiredArgsConstructor
public class RestApiConsumptionServiceImpl implements ApiConsumerService {

    private final RestTemplate restTemplate;

    @Override
    public String consumeApi(ApiModel apiModel, String requestBody) {
        return restTemplate.postForObject(apiModel.getUrl(), requestBody, String.class);
    }

    @Override
    public boolean supports(ApiModel apiModel) {
        return "REST".equalsIgnoreCase(apiModel.getType());
    }
}
