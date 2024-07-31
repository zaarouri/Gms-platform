package org.sid.apiconsumption_service.services;

import org.sid.apiconsumption_service.models.ApiModel;

import java.util.Map;


public interface ApiConsumerService {

    String consumeApi(String apiId, Map<String, String> parameters);
    boolean supports(ApiModel apiModel);
}
