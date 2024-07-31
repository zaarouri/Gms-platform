package org.sid.apiManagement_service.repositories;


import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;
import org.sid.apiManagement_service.models.ApiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiModelRepo extends JpaRepository<ApiModel, String> {
    public boolean existsById(String id);
    List<ApiModel> findByType(ApiType type);
    List<ApiModel> findBySupportedMethodsContaining(HttpMethod method);

}
