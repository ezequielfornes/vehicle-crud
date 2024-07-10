package org.vehicles.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.vehicles.dto.VehicleDto;
import org.vehicles.entity.Vehicle;

@Mapper
public interface VehicleMapper {

   VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

   Vehicle dtoToEntity(VehicleDto vehicleDto);

   VehicleDto entityToDto(Vehicle vehicle);
}
