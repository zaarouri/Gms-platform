package org.sid.logs_service.service.impls;


import lombok.RequiredArgsConstructor;
import org.sid.logs_service.models.LogEntry;
import org.sid.logs_service.repositories.LogsRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsServiceKafkaConsumer {

    private final LogsRepo logsRepo;

    // Listen to the 'api-consumption-logs' topic and store logs
    @KafkaListener(topics = "api-consumption-logs", groupId = "logs_group")
    public void consumeLog(LogEntry logEntry) {
        logsRepo.save(logEntry);  // Save the log entry in the database
    }
}

