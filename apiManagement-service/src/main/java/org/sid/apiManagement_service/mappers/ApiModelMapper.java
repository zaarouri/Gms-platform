package org.sid.apiManagement_service.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.dtos.ApiParameterDto;
import org.sid.apiManagement_service.models.ApiModel;
import org.sid.apiManagement_service.models.ApiParameter;

@Mapper(componentModel = "spring")
public interface ApiModelMapper {
   @Mapping(target = "password", ignore = true)
   ApiModel toEntity(ApiModelDto apiModelDto);

   @Mapping(target = "password", ignore = true)
   ApiModelDto toDto(ApiModel apiModel);

   ApiParameterDto parameterToDto(ApiParameter apiParameter);
   ApiParameter dtoToParameter(ApiParameterDto apiParameterDto);
}

