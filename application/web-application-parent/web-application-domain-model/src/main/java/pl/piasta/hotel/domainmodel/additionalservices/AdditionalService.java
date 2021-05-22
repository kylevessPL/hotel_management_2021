package pl.piasta.hotel.domainmodel.additionalservices;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public final class AdditionalService {

    private final int id;
    private final String name;
    private final BigDecimal price;
    private final String description;
}
