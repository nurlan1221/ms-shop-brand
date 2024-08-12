package az.orient.msshopbrand.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespBrand {
    private String name;
    private String description;
}
