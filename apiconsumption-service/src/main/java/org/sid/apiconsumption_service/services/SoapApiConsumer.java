package org.sid.apiconsumption_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.clients.LogsServiceClient;
import org.sid.apiconsumption_service.models.ApiModel;
import org.sid.apiconsumption_service.models.LogEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SoapApiConsumer implements ApiConsumerService {

    private final ApiModelRestClient apiClient;
    private final LogsServiceClient logsServiceClient;

    @Override
    public ResponseEntity<String> consumeApi(String apiId, String requestBody, Map<String, String> queryParams, Map<String, String> headers) {
        ApiModel apiModel = apiClient.getById(apiId);

        //todo
        // UserModel userModel = userClient.getUserById("hada 5asni njibo mn keycloack client fach tssali manal ");
        // if (!userModel.getApiModelsIds().contains(apiModel.getId())) throw new RuntimeException("User not allowed to consume this api ");

        // Ensure the URL is not empty
        String url = apiModel.getUrl();
        if (url == null || url.isEmpty()) {
            logError("The endpoint URL is not defined for the API.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The endpoint URL is not defined for the API.");
        }

        String soapBody = buildSoapBody(apiModel, requestBody);
        long startTime = System.currentTimeMillis();
        int responseStatus = 500; // Default status if there is an exception

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

            // Add additional headers, excluding restricted ones like Host, Content-Length, etc.
            if (headers != null) {
                headers.forEach((key, value) -> {
                    if (!"host".equalsIgnoreCase(key) && !"content-length".equalsIgnoreCase(key) && !"transfer-encoding".equalsIgnoreCase(key)) {
                        requestBuilder.header(key, value);
                    }
                });
            }

            // Build the POST request with the SOAP body
            HttpRequest request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(soapBody)).build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseStatus = response.statusCode();  // Update status with actual response status

            // Log the response
            logSoapResponse(response.body());

            long duration = System.currentTimeMillis() - startTime;

            // Log successful API consumption
            logConsumption(apiId, "user-ip-placeholder", responseStatus, duration, "user123", "SOAP API consumed successfully");

            return ResponseEntity.status(responseStatus).body(response.body());
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
        String namespace = "http://webservice.soap_server.sid.org/";
        String methodName = extractMethodName(apiModel.getSoapAction());

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
        System.out.println("Logging SOAP Response:");
        System.out.println("SOAP Response: " + response);
        System.out.println("Timestamp: " + System.currentTimeMillis());
    }

    private void logError(Exception e) {
        System.err.println("Error: " + e.getMessage());
        System.err.println("Timestamp: " + System.currentTimeMillis());
    }

    private void logError(String message) {
        System.err.println("Error: " + message);
        System.err.println("Timestamp: " + System.currentTimeMillis());
    }

    // Method to log API consumption
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
