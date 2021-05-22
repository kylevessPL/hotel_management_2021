package pl.piasta.hotel.dto.discounts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class DiscountDetailsResponse {

    @Schema(description = "Discount code", example = "PROMO5")
    private String code;
    @Schema(description = "Discount percentage value", example = "5")
    private Integer value;
}
