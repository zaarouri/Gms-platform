package org.sid.apiconsumption_service.models;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Embeddable
@Data
public class ApiParameter {
    private String name;
    private String description;
    private boolean required;
    private String defaultValue;

    @Enumerated(EnumType.STRING)
    private ParameterType type;

    // Getters and setters

    public enum ParameterType {
        STRING, NUMBER, BOOLEAN, OBJECT, ARRAY
    }
}

