package pl.piasta.hotel.domainmodel.discounts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class DiscountDetails {

    private final String code;
    private final Integer value;
}
