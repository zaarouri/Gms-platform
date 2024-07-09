package org.sid.userManagement_service.clients;

import org.sid.userManagement_service.entities.UserModel;
import org.sid.userManagement_service.models.ApiModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "apiManagement-service", url = "http://localhost:8082")
public interface ApiModelRestClient {

    @GetMapping("/apis")
    List<ApiModel> getAll();

    @GetMapping("/apis/{id}")
    ApiModel getById(@PathVariable String id);

}

