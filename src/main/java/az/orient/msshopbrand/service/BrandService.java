package az.orient.msshopbrand.service;

import az.orient.msshopbrand.dto.ReqBrand;
import az.orient.msshopbrand.dto.RespBrand;
import az.orient.msshopbrand.entity.BrandEntity;
import az.orient.msshopbrand.exception.BrandNotFoundException;
import az.orient.msshopbrand.mapper.BrandMapper;
import az.orient.msshopbrand.repository.BrandRepository;
import az.orient.msshopbrand.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public RespBrand createBrand(ReqBrand brand) {
        BrandEntity brandEntity = BrandMapper.INSTANCE.reqBrandToBrandEntity(brand);
        brandEntity.setStatus(Status.ACTIVE);
        BrandEntity savedEntity = brandRepository.save(brandEntity);
        return BrandMapper.INSTANCE.brandEntityToRespBrand(savedEntity);
    }

    public List<RespBrand> getAllBrands() {
        List<BrandEntity> brandEntities = brandRepository.findAllByStatus(Status.ACTIVE);
        return BrandMapper.INSTANCE.brandEntityListToRespBrandList(brandEntities);
    }

    public RespBrand getBrandById(Long id) {
        BrandEntity brandEntity = brandRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BrandNotFoundException("Brand not found exception with id: " + id));
        return BrandMapper.INSTANCE.brandEntityToRespBrand(brandEntity);
    }

    public void deleteBrandById(Long id) {
        BrandEntity brandEntity = brandRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BrandNotFoundException("Brand not found exception with id: " + id));
        brandEntity.setStatus(Status.DELETED);
        brandRepository.save(brandEntity);
    }
    public RespBrand updateBrandById(Long id, ReqBrand brand) {
        BrandEntity brandEntity=brandRepository.findByIdAndStatus(id,Status.ACTIVE).orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + id));
        BrandEntity savedEntity=BrandMapper.INSTANCE.updateBrandEntity(brand,brandEntity);
        BrandEntity saved=brandRepository.save(savedEntity);
        return BrandMapper.INSTANCE.brandEntityToRespBrand(saved);
    }
}
