package org.sid.logs_service.controllers;

import lombok.RequiredArgsConstructor;
import org.sid.logs_service.models.LogEntry;
import org.sid.logs_service.repositories.LogsRepo;
import org.sid.logs_service.service.LogsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogsController {

    private final LogsService logsService;
    private final LogsRepo logsRepo;

    @PostMapping("/log")
    public ResponseEntity<Void> log(@RequestBody LogEntry logData) {
        logsService.logConsumption(
                logData.getApiId(),
                logData.getUserIp(),
                logData.getResponseStatus(),
                logData.getRequestDuration(),
                logData.getUserId(),
                logData.getAdditionalInfo()
        );
        return ResponseEntity.ok().build();  // Return 200 status
    }

    @GetMapping(value = "/logs/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LogEntry> streamLogs() {
        final Date[] lastTimestamp = {new Date()};

        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(sequence -> {
                    List<LogEntry> logs = logsRepo.findLogsSince(lastTimestamp[0]);
                    if (!logs.isEmpty()) {
                        lastTimestamp[0] = logs.get(logs.size() - 1).getTimestamp(); // Update timestamp
                    }
                    return Flux.fromIterable(logs);
                });
    }

    @GetMapping("/all")
    public ResponseEntity<List<LogEntry>> getAllLogs() {
        return ResponseEntity.ok(logsRepo.findAll());
    }
}
