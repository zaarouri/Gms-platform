package org.sid.apiManagement_service.dtos;

import lombok.Builder;
import lombok.Data;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;

import java.util.*;

@Data
@Builder
public class ApiModelDto {
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
    private List<ApiParameterDto> parameters;
    private HttpMethod httpMethod;
}