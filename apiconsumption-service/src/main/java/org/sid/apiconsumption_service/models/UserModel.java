package org.sid.apiconsumption_service.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserModel {
    private String keycloakId;
    private List<String> apiModelsIds = new ArrayList<>();

}
