package org.sid.apiManagement_service.services;

import jakarta.persistence.EntityNotFoundException;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;

import java.util.List;


public interface ApiModelService {
    public ApiModelDto createApi(ApiModelDto apiModelDto) throws EntityNotFoundException;

    public ApiModelDto getApiById(String id) throws EntityNotFoundException;

    public List<ApiModelDto> getAllApis() throws EntityNotFoundException;

    public ApiModelDto updateApi(String id, ApiModelDto apiModelDto) throws EntityNotFoundException;

    public void deleteApi(String id) throws EntityNotFoundException;

    public List<ApiModelDto> getApisByType(ApiType type) throws EntityNotFoundException;

    public List<ApiModelDto> getApisSupportingMethod(HttpMethod method) throws EntityNotFoundException;
}

