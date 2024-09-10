package org.sid.logs_service.service.impls;

import lombok.RequiredArgsConstructor;
import org.sid.logs_service.models.LogEntry;
import org.sid.logs_service.repositories.LogsRepo;
import org.sid.logs_service.service.LogsService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {

    private final KafkaTemplate<String, LogEntry> kafkaTemplate;
    private final LogsRepo logsRepo;
    private static final String TOPIC = "api-consumption-logs";

    @Override
    public LogEntry logConsumption(String apiId, String userIp, int responseStatus, long requestDuration, String userId, String additionalInfo) {
        // Create and save log entry to the database
        LogEntry logEntry = LogEntry.builder()
                .apiId(apiId)
                .userIp(userIp)
                .responseStatus(responseStatus)
                .requestDuration(requestDuration)
                .timestamp(new Date())
                .userId(userId)
                .additionalInfo(additionalInfo)
                .build();

        LogEntry savedLog = logsRepo.save(logEntry);  // Save the log to the database

        // Send the log entry to Kafka for further processing
        kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(), savedLog);

        return savedLog;  // Return the saved log entry
    }
}
