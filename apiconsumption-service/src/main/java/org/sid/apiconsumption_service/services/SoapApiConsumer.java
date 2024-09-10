package org.sid.apiconsumption_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.models.ApiModel;
import org.sid.apiconsumption_service.models.LogEntry;
import org.sid.apiconsumption_service.clients.LogsServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SoapApiConsumer implements ApiConsumerService {

    private final ApiModelRestClient apiClient;
    private final LogsServiceClient logsServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(SoapApiConsumer.class);

    @Override
    public ResponseEntity<String> consumeApi(String apiId, String requestBody, Map<String, String> queryParams, Map<String, String> headers) {
        // Fetch API details from the management service
        ApiModel apiModel = apiClient.getById(apiId);

        // Ensure the URL is not empty
        String url = apiModel.getUrl();
        if (url == null || url.isEmpty()) {
            logError("The endpoint URL is not defined for the API.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The endpoint URL is not defined for the API.");
        }

        // Build the SOAP request body dynamically
        String soapBody = buildSoapBody(apiModel, requestBody);
        long startTime = System.currentTimeMillis();
        int responseStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();

        try {
            // Use HttpClient to send the SOAP request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "text/xml; charset=utf-8");

            // Set SOAPAction if available
            if (apiModel.getSoapAction() != null && !apiModel.getSoapAction().isEmpty()) {
                requestBuilder.header("SOAPAction", apiModel.getSoapAction());
            }

            // Add additional headers if provided
            if (headers != null) {
                headers.forEach((key, value) -> {
                    if (!"content-length".equalsIgnoreCase(key) && !"host".equalsIgnoreCase(key)) {
                        requestBuilder.header(key, value);
                    }
                });
            }

            // Build the POST request with the SOAP body
            HttpRequest request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(soapBody)).build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseStatus = response.statusCode();

            // Log the response
            logSoapResponse(response.body());

            long duration = System.currentTimeMillis() - startTime;

            // Log API consumption to logs service
            logConsumption(apiId, "user-ip-placeholder", responseStatus, duration, "user123", "SOAP API consumed successfully");

            return ResponseEntity.status(response.statusCode()).body(response.body());
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            // Log error during API consumption
            logError(e);
            logConsumption(apiId, "user-ip-placeholder", responseStatus, duration, "user123", "Error consuming SOAP API: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error consuming SOAP API: " + e.getMessage());
        }
    }

    private String buildSoapBody(ApiModel apiModel, String requestBody) {
        // Extract the correct namespace and method name dynamically from ApiModel
        String namespace = "http://webservice.soap_server.sid.org/"; // Use the correct namespace
        String methodName = extractMethodName(apiModel.getSoapAction()); // Extract the method name from SOAP action

        if (methodName == null || methodName.isEmpty()) {
            methodName = "getAllProducts"; // Fallback method name; replace with an appropriate default if necessary
            logError("Method name could not be determined from SOAP action. Using default method: " + methodName);
        }

        // Build the SOAP request body dynamically
        return String.format(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"%s\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body><web:%s>%s</web:%s></soapenv:Body></soapenv:Envelope>",
                namespace, methodName, requestBody, methodName
        );
    }

    private String extractMethodName(String soapAction) {
        // Extract method name from the SOAPAction, assuming format like "http://.../methodNameRequest"
        if (soapAction != null && !soapAction.isEmpty()) {
            int lastSlashIndex = soapAction.lastIndexOf('/');
            if (lastSlashIndex != -1) {
                String methodNameWithRequest = soapAction.substring(lastSlashIndex + 1);
                if (methodNameWithRequest.endsWith("Request")) {
                    return methodNameWithRequest.replace("Request", "");
                } else {
                    return methodNameWithRequest;
                }
            }
        }
        return null;
    }

    private void logSoapResponse(String response) {
        logger.info("SOAP Response: {}", response);
        logger.info("Timestamp: {}", System.currentTimeMillis());
    }

    private void logError(Exception e) {
        logger.error("Error: {}", e.getMessage(), e);
        logger.error("Timestamp: {}", System.currentTimeMillis());
    }

    private void logError(String message) {
        logger.error("Error: {}", message);
        logger.error("Timestamp: {}", System.currentTimeMillis());
    }

    private void logConsumption(String apiId, String userIp, int responseStatus, long requestDuration, String userId, String additionalInfo) {
        LogEntry log = LogEntry.builder()
                .apiId(apiId)
                .userIp(userIp)
                .responseStatus(responseStatus)
                .requestDuration(requestDuration)
                .timestamp(new Date())
                .userId(userId)
                .additionalInfo(additionalInfo)
                .build();

        // Send log to LogsServiceClient
        logsServiceClient.log(log);
    }
}
