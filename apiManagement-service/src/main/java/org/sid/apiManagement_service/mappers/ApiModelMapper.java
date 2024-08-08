package org.sid.apiManagement_service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sid.apiManagement_service.dtos.ApiModelDto;
import org.sid.apiManagement_service.dtos.ApiParameterDto;
import org.sid.apiManagement_service.models.ApiModel;
import org.sid.apiManagement_service.models.ApiParameter;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiModelMapper {
   ApiModel toEntity(ApiModelDto apiModelDto);
   ApiModelDto toDto(ApiModel apiModel);

   @Mapping(source = "apiParameterDto.name", target = "name")
   @Mapping(source = "apiParameterDto.description", target = "description")
   @Mapping(source = "apiParameterDto.required", target = "required")
   @Mapping(source = "apiParameterDto.defaultValue", target = "defaultValue")
   @Mapping(source = "apiParameterDto.type", target = "type")
   ApiParameter map(ApiParameterDto apiParameterDto);

   @IterableMapping(elementTargetType = ApiParameter.class)
   List<ApiParameter> dtoListToParameterList(List<ApiParameterDto> apiParameterDtos);

   @IterableMapping(elementTargetType = ApiParameterDto.class)
   List<ApiParameterDto> parameterListToDto(List<ApiParameter> apiParameters);
}