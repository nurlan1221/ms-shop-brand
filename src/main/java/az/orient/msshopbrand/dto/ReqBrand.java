package az.orient.msshopbrand.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqBrand {
    @NotNull(message = "Model name is required")
    private String name;
    private String description;
}