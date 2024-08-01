package org.sid.apiconsumption_service.models;

import lombok.Builder;
import lombok.Data;
import org.sid.apiconsumption_service.enums.ApiType;
import org.sid.apiconsumption_service.enums.HttpMethod;
import org.sid.apiconsumption_service.models.ApiParameter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ApiModel {
    private String id;
    private String name;
    private String url;
    private ApiType type;
    private String username;
    private String password;
    private Date createdAt;
    private Set<String> supportedMethods;
    private Map<String, String> headers;
    private String soapAction;
    private String wsdlUrl;
    private List<ApiParameter> parameters;
    private HttpMethod httpMethod;
}