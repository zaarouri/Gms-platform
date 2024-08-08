package org.sid.apiManagement_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;

import java.time.Instant;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "api_models")
public class ApiModel {
    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String name;
    private String url;
    @Enumerated(EnumType.STRING)
    private ApiType type;
    private String username;
    private String password;
    private Date createdAt;
    private Date archivedAt;
    @ElementCollection
    @CollectionTable(name = "api_supported_methods", joinColumns = @JoinColumn(name = "api_id"))
    @Column(name = "supported_method")
    private Set<String> supportedMethods = new HashSet<>();
    @ElementCollection
    @CollectionTable(name = "api_headers", joinColumns = @JoinColumn(name = "api_id"))
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    private Map<String, String> headers = new HashMap<>();
    private String soapAction;
    private String wsdlUrl;
    @ElementCollection
    @CollectionTable(name = "api_parameters", joinColumns = @JoinColumn(name = "api_id"))
    private List<ApiParameter> parameters = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;
    private boolean archived;
}