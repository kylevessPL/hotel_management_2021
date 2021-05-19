package pl.piasta.hotel.domainmodel.discounts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DiscountCommand {

    private String code;
    private Integer value;
}
