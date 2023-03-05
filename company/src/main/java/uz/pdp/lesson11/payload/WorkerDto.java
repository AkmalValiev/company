package uz.pdp.lesson11.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak!")
    private String name;
    @NotNull(message = "phoneNumber bo'sh bo'lmasligi kerak!")
    private String phoneNumber;
    @NotNull(message = "addressId bo'sh bo'lmasligi kerak!")
    private Integer addressId;
    @NotNull(message = "departmentId bo'sh bo'lmasligi kerak!")
    private Integer departmentId;

}
