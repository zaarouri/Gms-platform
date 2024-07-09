package org.sid.apiconsumption_service.services;

import org.sid.apiconsumption_service.models.ApiModel;


public interface ApiConsumerService {

    String consumeApi(ApiModel apiModel, String requestBody);
    boolean supports(ApiModel apiModel);
}
