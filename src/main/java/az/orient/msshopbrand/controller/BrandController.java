package az.orient.msshopbrand.controller;

import az.orient.msshopbrand.dto.ReqBrand;
import az.orient.msshopbrand.dto.RespBrand;
import az.orient.msshopbrand.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespBrand createBrand(@Valid @RequestBody ReqBrand reqBrand) {
        return brandService.createBrand(reqBrand);
    }

    @GetMapping
    public List<RespBrand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping(path = "{id}")
    public RespBrand getBrandById(Long id) {
        return brandService.getBrandById(id);
    }
}
