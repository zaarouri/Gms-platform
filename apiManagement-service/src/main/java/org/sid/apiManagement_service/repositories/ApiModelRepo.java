package org.sid.apiManagement_service.repositories;


import org.sid.apiManagement_service.models.ApiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiModelRepo extends JpaRepository<ApiModel, String> {
    public boolean existsById(String id);
    public ApiModel findByName(String name);

}
