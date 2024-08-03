package org.sid.apiconsumption_service.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sid.apiconsumption_service.clients.ApiModelRestClient;
import org.sid.apiconsumption_service.models.ApiModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.xml.transform.StringSource;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SoapApiConsumer implements ApiConsumerService {


    private final WebServiceTemplate webServiceTemplate;
    private final ApiModelRestClient apiClient;

    @Override
    public ResponseEntity<String> consumeApi(String apiId, String requestBody, Map<String, String> queryParams, Map<String, String> headers) {
        // Fetch API details from the management service
        ApiModel apiModel = apiClient.getById(apiId);

        // Create request headers
        HttpHeaders requestHeaders = new HttpHeaders();
        headers.forEach(requestHeaders::add);
        requestHeaders.setContentType(MediaType.TEXT_XML);

        // Build the SOAP request body
        String soapBody = buildSoapBody(apiModel, requestBody);

        // Create SOAP callback
        SoapActionCallback callback = new SoapActionCallback(apiModel.getSoapAction());

        // Send SOAP request
        String response = (String) webServiceTemplate.marshalSendAndReceive(apiModel.getUrl(), soapBody, callback);

        return ResponseEntity.ok(response);
    }

    private String buildSoapBody(ApiModel apiModel, String requestBody) {
        // Extract the namespace, method name, and parameters from ApiModel
        String namespace = apiModel.getWsdlUrl(); // This should be parsed to get the namespace
        String methodName = apiModel.getSoapAction(); // Example extraction, might need adjustment

        // Adjust the namespace extraction as necessary, example here assumes a single namespace
        namespace = "http://www.oorsprong.org/websamples.countryinfo"; // Placeholder, replace with actual logic

        // Build dynamic SOAP request body based on ApiModel
        StringBuilder soapBodyBuilder = new StringBuilder();
        soapBodyBuilder.append(String.format(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"%s\">" +
                        "<soapenv:Header/>" +
                        "<soapenv:Body>", namespace
        ));

        // Include method and parameters dynamically
        soapBodyBuilder.append(String.format(
                "<web:%s>", methodName
        ));

        // Example for adding parameters, adjust based on actual ApiModel structure
        // Assuming requestBody contains the parameter or needs to be transformed
        soapBodyBuilder.append(requestBody);

        soapBodyBuilder.append(String.format(
                "</web:%s>" +
                        "</soapenv:Body>" +
                        "</soapenv:Envelope>", methodName
        ));

        return soapBodyBuilder.toString();
    }
}


