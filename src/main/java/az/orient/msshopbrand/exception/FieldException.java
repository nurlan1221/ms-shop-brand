package az.orient.msshopbrand.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldException {
    private String fieldName;
    private String errorMessage;

}
