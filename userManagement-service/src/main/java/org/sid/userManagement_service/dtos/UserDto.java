package org.sid.userManagement_service.dtos;


import lombok.Data;
import org.sid.userManagement_service.models.ApiModel;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String role;
    private List<String> apiModelsIds = new ArrayList<>();
    private List<ApiModel> apiModels = new ArrayList<>();
}
