package az.orient.msshopbrand.controller;

import az.orient.msshopbrand.dto.ReqBrand;
import az.orient.msshopbrand.exception.BrandNotFoundException;
import az.orient.msshopbrand.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BrandService brandService;

    ObjectMapper mapper = new ObjectMapper();


    @Test
    void createBrandGivenValidBrandAndThenReturn201() throws Exception {
        ReqBrand reqBrand = new ReqBrand();
        reqBrand.setName("Mock");
        reqBrand.setDescription("Mock");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                .content(mapper.writeValueAsString(reqBrand))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 201);
    }

    @Test
    void createBrandGivenInvalidReqBrandAndThenReturn400() throws Exception {
        ReqBrand reqBrand = new ReqBrand();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                .content(mapper.writeValueAsString(reqBrand))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Test
    void getBrandGivenValidBrandIdAndThenReturn200() throws Exception {
        Long brandId = 1L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/brands/" + brandId)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
        Mockito.verify(brandService).getBrandById(brandId);
    }

    @Test
    void getBrandGivenInvalidIdAndThenReturn404() throws Exception {
        Long brandId = 1L;
        when(brandService.getBrandById(brandId)).thenThrow(new BrandNotFoundException("Brand not found with id: 1"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/brands/" + brandId)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 404);
        verify(brandService).getBrandById(brandId);
    }

    @Test
    void updateBrandGivenValidBrandIdAndThenReturn200() throws Exception {
        Long brandId = 1L;
        ReqBrand reqBrand = new ReqBrand();
        reqBrand.setName("Mock");
        reqBrand.setDescription("Mock");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/brands/" + brandId)
                .content(mapper.writeValueAsString(reqBrand))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    void updateBrandGivenInvalidBrandIdAndThenReturn404() throws Exception {
        Long brandId = 1L;
        ReqBrand reqBrand = new ReqBrand();
        reqBrand.setName("Mock");
        reqBrand.setDescription("Mock");

        when(brandService.updateBrandById(brandId, reqBrand)).thenThrow(new BrandNotFoundException("Brand not found with id: 1"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/brands/" + brandId)
                .content(mapper.writeValueAsString(reqBrand))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 404);
    }

    @Test
    void deleteBrandGivenValidIdAndThenReturn200() throws Exception {
        Long id = 1L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/brands/" + id)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 200);
        verify(brandService).deleteBrandById(id);
    }

    @Test
    void deleteBrandGivenInvalidIdAndThenReturn404() throws Exception {
        Long id = 1L;
        doThrow(new BrandNotFoundException("Brand not found with id: 1")).when(brandService).deleteBrandById(id);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/brands/" + id)).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), 404);
        verify(brandService).deleteBrandById(id);
    }


}