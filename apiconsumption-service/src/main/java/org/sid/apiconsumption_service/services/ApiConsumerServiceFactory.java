package org.sid.apiconsumption_service.services;

import org.sid.apiconsumption_service.models.ApiModel;
import org.sid.apiconsumption_service.services.ApiConsumerService;
import org.sid.apiconsumption_service.services.RestApiConsumer;
import org.sid.apiconsumption_service.services.SoapApiConsumer;
import org.springframework.stereotype.Component;

@Component
public class ApiConsumerServiceFactory {

    private final SoapApiConsumer soapApiConsumer;
    private final RestApiConsumer restApiConsumer;

    public ApiConsumerServiceFactory(SoapApiConsumer soapApiConsumer, RestApiConsumer restApiConsumer) {
        this.soapApiConsumer = soapApiConsumer;
        this.restApiConsumer = restApiConsumer;
    }

    public ApiConsumerService getService(ApiModel apiModel) {
        if ("SOAP".equalsIgnoreCase(apiModel.getType().toString())) {
            return soapApiConsumer;
        } else if ("REST".equalsIgnoreCase(apiModel.getType().toString())) {
            return restApiConsumer;
        } else {
            throw new IllegalArgumentException("Unknown API type: " + apiModel.getType());
        }
    }
}
