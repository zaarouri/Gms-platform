package org.sid.apiManagement_service.dtos;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Data
public class ApiModelDto {
    private String id;
    private String name;
    private String url;
    private String username;
    private String method;
    private String type; // SOAP or REST
    private String password;
    private Date createdAt;
    private Map<String, String> headers = new HashMap<>();
}
