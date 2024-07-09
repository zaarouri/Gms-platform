package org.sid.apiManagement_service.services;

import jakarta.persistence.EntityNotFoundException;
import org.sid.apiManagement_service.dtos.ApiModelDto;

import java.util.List;


public interface ApiModelService {
    public ApiModelDto addApiModel(ApiModelDto apiModelDto) ;
    public ApiModelDto updateApiModel(ApiModelDto apiModelDto)throws EntityNotFoundException;
    public ApiModelDto getApiModelById(String id) throws EntityNotFoundException;
    public List<ApiModelDto> getAllApiModels() throws EntityNotFoundException;
    public void deleteApiModelById(String id)throws EntityNotFoundException;
    public ApiModelDto getApiModelByName(String name)throws EntityNotFoundException;

}
