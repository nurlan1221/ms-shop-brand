package az.orient.msshopbrand.service;

import az.orient.msshopbrand.dto.ReqBrand;
import az.orient.msshopbrand.dto.RespBrand;
import az.orient.msshopbrand.entity.BrandEntity;
import az.orient.msshopbrand.exception.BrandNotFoundException;
import az.orient.msshopbrand.repository.BrandRepository;
import az.orient.msshopbrand.type.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    BrandRepository brandRepository;

    @InjectMocks
    BrandService brandService;



    @Test
    void updateBrandGivenValidIdThenReturnUpdatedDto(){
        Long brandId = 1L;
        ReqBrand rb = new ReqBrand();
        BrandEntity brandEntity= new BrandEntity();
        brandEntity.setName("Mock");
        when(brandRepository.findByIdAndStatus(brandId, Status.ACTIVE)).thenReturn(Optional.of(brandEntity));
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);

        RespBrand respBrand=brandService.updateBrandById(brandId,rb);
        assertNotNull(respBrand);
        verify(brandRepository, Mockito.times(1)).save(brandEntity);
    }
    @Test
    void updateBrandGivenInvalidIdThenReturn404(){
        Long brandId = 1L;
        when(brandRepository.findByIdAndStatus(brandId, Status.ACTIVE)).thenReturn(Optional.empty());
        BrandNotFoundException brandNotFoundException=assertThrows(BrandNotFoundException.class,()->brandService.updateBrandById(brandId,new ReqBrand()));
        assertNotNull(brandNotFoundException);
    }
    @Test
    void getBrandByIdGivenValidIdThenReturnBrandDto(){
        Long brandId = 1L;
        BrandEntity brandEntity= new BrandEntity();
        brandEntity.setName("Mock");
        when(brandRepository.findByIdAndStatus(brandId, Status.ACTIVE)).thenReturn(Optional.of(brandEntity));
        RespBrand respBrand=brandService.getBrandById(brandId);
        assertNotNull(respBrand);
    }
    @Test
    void getBrandByIdGivenInvalidIdThenReturn404(){
        Long brandId = 1L;
        when(brandRepository.findByIdAndStatus(brandId, Status.ACTIVE)).thenReturn(Optional.empty());
        BrandNotFoundException brandNotFoundException=assertThrows(BrandNotFoundException.class,()->brandService.getBrandById(brandId));
        assertNotNull(brandNotFoundException);
    }
    @Test
    void deleteBrandGivenValidIdThenReturnTrue(){
        Long brandId = 1L;
        BrandEntity brandEntity= new BrandEntity();
        brandEntity.setId(brandId);
        brandEntity.setStatus(Status.ACTIVE);
        when(brandRepository.findByIdAndStatus(brandId, Status.ACTIVE)).thenReturn(Optional.of(brandEntity));
        brandService.deleteBrandById(brandId);
        verify(brandRepository).findByIdAndStatus(brandId, Status.ACTIVE);
        verify(brandRepository).save(brandEntity);
        assertEquals(Status.DELETED, brandEntity.getStatus());
    }
    @Test
    void deleteBrandGivenInvalidIdThenReturn404(){
        Long brandId = 1L;
        when(brandRepository.findByIdAndStatus(brandId, Status.ACTIVE)).thenReturn(Optional.empty());
        BrandNotFoundException brandNotFoundException=assertThrows(BrandNotFoundException.class,()->brandService.deleteBrandById(brandId));
        assertNotNull(brandNotFoundException);
    }



}