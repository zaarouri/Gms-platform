package org.sid.apiconsumption_service.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {

    private Long id;

    private String apiId;

    private String userIp;

    private int responseStatus;

    private long requestDuration; // in milliseconds

    private Date timestamp;

    private String userId; // Optional: Store the user who made the API request

    // Other optional fields you may want to add:
    private String additionalInfo; // Any additional info, such as user agent, etc.
}


