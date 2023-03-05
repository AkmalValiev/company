package uz.pdp.lesson11.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    @NotNull(message = "compName bo'sh bo'lamasligi kerak!")
    private String compName;
    @NotNull(message = "directorName bo'sh bo'lamasligi kerak!")
    private String directorName;
    @NotNull(message = "addressId bo'sh bo'lamasligi kerak!")
    private Integer addressId;

}
