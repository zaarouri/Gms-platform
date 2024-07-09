package org.sid.apiManagement_service.mappers;


import org.mapstruct.Mapper;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.models.ApiModel;

@Mapper(componentModel = "spring")
public interface ApiModelMapper {

   ApiModel toEntity(ApiModelDto apiModelDto);

   ApiModelDto toDto(ApiModel apiModel);
}

