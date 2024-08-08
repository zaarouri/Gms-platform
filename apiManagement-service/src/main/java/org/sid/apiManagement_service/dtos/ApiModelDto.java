package org.sid.apiManagement_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiModelDto {
    private String id;
    private String name;
    private String url;
    private ApiType type;
    private String username;
    private String password;
    private Date createdAt;
    private Date archivedAt;
    private Set<String> supportedMethods;
    private Map<String, String> headers;
    private String soapAction;
    private String wsdlUrl;
    private List<ApiParameterDto> parameters;
    private HttpMethod httpMethod;
    private boolean archived;
}