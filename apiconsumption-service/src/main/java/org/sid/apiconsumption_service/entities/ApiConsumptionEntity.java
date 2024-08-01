package org.sid.apiconsumption_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "api_consumption")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiConsumptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id", nullable = false)
    private Long apiId;

    @Column(name = "request_body", columnDefinition = "TEXT")
    private String requestBody;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "status_code", nullable = false)
    @Enumerated(EnumType.STRING)
    private HttpStatus statusCode;

    @ElementCollection
    @CollectionTable(name = "api_consumption_request_headers", joinColumns = @JoinColumn(name = "consumption_id"))
    @MapKeyColumn(name = "header_name")
    @Column(name = "header_value")
    private Map<String, String> requestHeaders;

    @ElementCollection
    @CollectionTable(name = "api_consumption_response_headers", joinColumns = @JoinColumn(name = "consumption_id"))
    @MapKeyColumn(name = "header_name")
    @Column(name = "header_value")
    private Map<String, String> responseHeaders;
}