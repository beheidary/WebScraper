package com.example.demo.mapper;

import com.example.demo.dto.OutDto.ExpertiseOutDto;

import com.example.demo.entity.ExpertiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpertiseMapper {

    ExpertiseOutDto entityToDto(ExpertiseEntity expertiseEntity);

}

