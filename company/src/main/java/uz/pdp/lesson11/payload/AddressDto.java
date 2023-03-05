package uz.pdp.lesson11.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotNull(message = "street bo'sh bo'lmasligi kerak!")
    private String street;
    @NotNull(message = "homeNumber bo'sh bo'lmasligi kerak!")
    private String homeNumber;

}
