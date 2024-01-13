package com.example.demo.mapper;

import com.example.demo.dto.OutDto.CityOutDto;
import com.example.demo.entity.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {

    CityOutDto entityToDto(CityEntity cityEntity);

}

