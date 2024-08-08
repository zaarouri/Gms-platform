package org.sid.apiManagement_service.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.enums.ApiType;
import org.sid.apiManagement_service.enums.HttpMethod;

import java.util.List;


public interface ApiModelService {
     ApiModelDto createApi(ApiModelDto apiModelDto) throws EntityExistsException;

     ApiModelDto getApiById(String id) throws EntityNotFoundException;

     List<ApiModelDto> getAllApis() throws EntityNotFoundException;

     ApiModelDto updateApi(String id, ApiModelDto apiModelDto) throws EntityNotFoundException;

     ApiModelDto archiveApiModel(String apiId) throws  EntityNotFoundException;

     List<ApiModelDto> getApisByType(ApiType type) throws EntityNotFoundException;

     List<ApiModelDto> getApisSupportingMethod(HttpMethod method) throws EntityNotFoundException;
    List<ApiModelDto> getAllArchivedApis() throws EntityNotFoundException;
}

