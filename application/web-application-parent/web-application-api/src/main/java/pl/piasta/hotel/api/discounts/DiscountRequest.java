package pl.piasta.hotel.api.discounts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.api.utils.ValidateDates;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema
@ValidateDates
@NoArgsConstructor
@Getter
@Setter
public class DiscountRequest implements Serializable {

    @Schema(description = "Discount code")
    @NotBlank
    private String code;
    @Schema(description = "Discount value")
    @NotNull
    @Min(1)
    private Integer value;
}
