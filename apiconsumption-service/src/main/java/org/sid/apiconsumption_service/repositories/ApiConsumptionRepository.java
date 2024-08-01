package org.sid.apiconsumption_service.repositories;

import org.sid.apiconsumption_service.entities.ApiConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiConsumptionRepository extends JpaRepository<ApiConsumptionEntity, Long> {
    List<ApiConsumptionEntity> findByApiId(Long apiId);
}
