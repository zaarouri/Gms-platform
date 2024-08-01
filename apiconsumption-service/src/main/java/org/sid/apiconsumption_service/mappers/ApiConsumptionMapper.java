package org.sid.apiconsumption_service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sid.apiconsumption_service.dtos.ApiConsumptionDto;
import org.sid.apiconsumption_service.entities.ApiConsumptionEntity;


@Mapper(componentModel = "spring")
public interface ApiConsumptionMapper {
    ApiConsumptionDto entityToDto(ApiConsumptionEntity entity);
}