package org.sid.logs_service.repositories;

import org.sid.logs_service.models.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LogsRepo extends JpaRepository<LogEntry, Long> {

    @Query("SELECT l FROM LogEntry l WHERE l.timestamp > :timestamp")
    List<LogEntry> findLogsSince(@Param("timestamp") Date timestamp);

    List<LogEntry> findTopByOrderByTimestampDesc();
}
