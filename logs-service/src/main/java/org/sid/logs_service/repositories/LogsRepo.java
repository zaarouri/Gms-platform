package org.sid.logs_service.repositories;


import org.sid.logs_service.models.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LogsRepo extends JpaRepository<LogEntry, String> {
    // Fetch logs after a specific timestamp (or you can use ID-based if needed)
    @Query("SELECT l FROM LogEntry l WHERE l.timestamp > :since")
    List<LogEntry> findLogsSince(Date since);
}
