package org.sid.apiManagement_service.repositories;


import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;
import org.sid.apiManagement_service.models.ApiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiModelRepo extends JpaRepository<ApiModel, String> {
    boolean existsById(String id);

    boolean existsByName(String name);

    List<ApiModel> findByType(ApiType type);

    List<ApiModel> findBySupportedMethodsContaining(HttpMethod method);

    List<ApiModel> findByArchivedFalse();

    List<ApiModel> findByArchivedTrue();
}
