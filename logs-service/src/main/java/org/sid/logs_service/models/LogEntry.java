package org.sid.logs_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "consumption_logs")
public class LogEntry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private String userId;

  private String additionalInfo;
}
