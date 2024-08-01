package org.sid.apiconsumption_service.clients;
import org.sid.apiconsumption_service.models.ApiModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "apiManagement-service", url = "http://localhost:8082")
public interface ApiModelRestClient {

    @GetMapping("/apis/all")
    List<ApiModel> getAll();

    @GetMapping("/apis/{id}")
    ApiModel getById(@PathVariable String id);

}

