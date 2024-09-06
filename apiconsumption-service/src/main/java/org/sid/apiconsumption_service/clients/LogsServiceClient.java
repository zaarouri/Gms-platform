package org.sid.apiconsumption_service.clients;


import org.sid.apiconsumption_service.models.LogEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "logs-service", url = "http://localhost:8084")
public interface LogsServiceClient {

    @PostMapping("/log")
    void log(@RequestBody LogEntry logData); // Changed to LogEntry
}
