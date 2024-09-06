package org.sid.logs_service.service.impls;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.sid.logs_service.models.LogEntry;
import org.sid.logs_service.service.LogsService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {

    private final KafkaTemplate<String, LogEntry> kafkaTemplate;
    private static final String TOPIC = "api-consumption-logs";

    @Override
    public void logConsumption(String apiId, String userIp, int responseStatus, long requestDuration, String userId, String additionalInfo) {
        LogEntry logEntry = LogEntry.builder()
                .apiId(apiId)
                .userIp(userIp)
                .responseStatus(responseStatus)
                .requestDuration(requestDuration)
                .timestamp(new Date())
                .userId(userId)
                .additionalInfo(additionalInfo)
                .build();

        // Send log entry to Kafka topic
        kafkaTemplate.send(new ProducerRecord<>(TOPIC, logEntry));
    }
}
