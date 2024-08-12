package az.orient.msshopbrand.mapper;


import az.orient.msshopbrand.dto.ReqBrand;
import az.orient.msshopbrand.dto.RespBrand;
import az.orient.msshopbrand.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BrandMapper {
    BrandMapper INSTANCE= Mappers.getMapper(BrandMapper.class);
    BrandEntity reqBrandToBrandEntity(ReqBrand reqBrand);
    RespBrand brandEntityToRespBrand(BrandEntity brandEntity);
    List<RespBrand> brandEntityListToRespBrandList(List<BrandEntity> brandEntityList);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "status", ignore = true)
    BrandEntity updateBrandEntity(ReqBrand reqBrand, @MappingTarget BrandEntity brandEntity);






}
