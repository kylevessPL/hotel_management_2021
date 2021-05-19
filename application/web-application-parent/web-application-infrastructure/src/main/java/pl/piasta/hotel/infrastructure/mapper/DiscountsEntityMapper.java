package pl.piasta.hotel.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.infrastructure.model.DiscountsEntity;

import java.util.Optional;

@Component
public class DiscountsEntityMapper {

    public Optional<DiscountDetails> mapToDiscountDetails(Optional<DiscountsEntity> discountsEntity) {
        return discountsEntity.map(e -> new DiscountDetails(e.getCode(), e.getValue()));
    }
}
