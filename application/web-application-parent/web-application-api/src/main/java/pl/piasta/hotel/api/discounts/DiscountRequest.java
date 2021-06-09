package pl.piasta.hotel.api.discounts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class DiscountRequest implements Serializable {

    @Schema(description = "Discount code", example = "PROMO5")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Size(min = 2, max = 20)
    private String code;
    @Schema(description = "Discount value", example = "5")
    @NotNull
    @Min(1)
    private Integer value;
}
