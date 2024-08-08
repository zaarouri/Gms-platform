package org.sid.apiManagement_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.apiManagement_service.models.ApiParameter;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiParameterDto {
    private String name;
    private String description;
    private boolean required;
    private String defaultValue;
    private ApiParameter.ParameterType type;
}
