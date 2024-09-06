package org.sid.logs_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "consumption_logs")
public class LogEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) // This will auto-generate the ID
  private Long id;

    @Column(nullable = false)
    private String apiId;

    @Column(nullable = false)
    private String userIp;

    @Column(nullable = false)
    private int responseStatus;

    @Column(nullable = false)
    private long requestDuration; // in milliseconds

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(nullable = true)
    private String userId; // Optional: Store the user who made the API request

    // Other optional fields you may want to add:
    private String additionalInfo; // Any additional info, such as user agent, etc.
}

