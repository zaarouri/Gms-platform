package org.sid.apiconsumption_service.models;

import lombok.Data;

import java.util.Map;
@Data
public class ApiModel {
    private String id;
    private String name;
    private String url;
    private String username;
    private String method;
    private String type; // SOAP or REST
    private String password;
    private Map<String, String> headers;
}
