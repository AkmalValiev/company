package uz.pdp.lesson11.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak!")
    private String name;
    @NotNull(message = "companyId bo'sh bo'lmasligi kerak!")
    private Integer companyId;

}
