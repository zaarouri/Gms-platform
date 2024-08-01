package org.sid.apiconsumption_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiConsumptionDto {
    private Long id;
    private Long apiId;
    private String requestBody;
    private String responseBody;
    private LocalDateTime timestamp;
    private HttpStatus statusCode;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
}