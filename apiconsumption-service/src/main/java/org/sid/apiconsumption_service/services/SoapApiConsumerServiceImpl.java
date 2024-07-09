//package org.sid.apiconsumption_service.services;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//
//import org.sid.apiconsumption_service.models.ApiModel;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class SoapApiConsumerServiceImpl implements ApiConsumerService {
//
//    private final WebServiceTemplate webServiceTemplate;
//
//
//    @Override
//    public String consumeApi(ApiModel apiModel, String requestBody) {
//        return (String) webServiceTemplate();
//    }
//
//    @Override
//    public boolean supports(ApiModel apiModel) {
//        return "SOAP".equalsIgnoreCase(apiModel.getType());
//    }
//}
