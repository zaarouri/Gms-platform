package org.sid.logs_service.service;



public interface LogsService {

     void logConsumption(String apiId, String userIp, int responseStatus, long requestDuration, String userId, String additionalInfo);

}
