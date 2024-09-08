package org.sid.apiconsumption_service.services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.clients.LogsServiceClient;
import org.sid.apiconsumption_service.clients.UserClient;
import org.sid.apiconsumption_service.models.ApiModel;
import org.sid.apiconsumption_service.models.LogEntry;
import org.sid.apiconsumption_service.utilities.IpAddressUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Date;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class RestApiConsumer implements ApiConsumerService {

    private final RestTemplate restTemplate;
    private final ApiModelRestClient apiClient;
    private final LogsServiceClient logsServiceClient;
    private final HttpServletRequest httpServletRequest;
    private final UserClient userClient;

    @Override
    public ResponseEntity<String> consumeApi(String apiId, String requestBody, Map<String, String> queryParams, Map<String, String> headers) {

        ApiModel apiModel = apiClient.getById(apiId);

        //todo
        // UserModel userModel = userClient.getUserById("hada 5asni njibo mn keycloack client fach tssali manal ");
        // if (!userModel.getApiModelsIds().contains(apiModel.getId())) throw new RuntimeException("User not allowed to consume this api ");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(apiModel.getHeaders());
        headers.forEach(httpHeaders::add);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiModel.getUrl());
        queryParams.forEach(builder::queryParam);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, httpHeaders);

        long startTime = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.valueOf(apiModel.getHttpMethod().toString().toUpperCase()),
                entity,
                String.class
        );
        long duration = System.currentTimeMillis() - startTime;

        // Retrieve the real user IP address using the utility class
        String userIp = IpAddressUtil.getClientIp(httpServletRequest);

        // Prepare log data
        LogEntry log = LogEntry.builder()
                .apiId(apiId)
                .additionalInfo("API consumed successfully")
                .timestamp(new Date())
                .requestDuration(duration)
                .userId("123") // Replace with actual user ID
                .responseStatus(response.getStatusCode().value())
                .userIp(userIp) // Add user IP
                .build();

        // Send log data to logs service using Feign Client
        logsServiceClient.log(log);

        return response;
    }
}
