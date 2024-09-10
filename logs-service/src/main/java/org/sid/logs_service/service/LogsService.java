package org.sid.logs_service.service;

import org.sid.logs_service.models.LogEntry;

public interface LogsService {
     LogEntry logConsumption(String apiId, String userIp, int responseStatus, long requestDuration, String userId, String additionalInfo);
}
