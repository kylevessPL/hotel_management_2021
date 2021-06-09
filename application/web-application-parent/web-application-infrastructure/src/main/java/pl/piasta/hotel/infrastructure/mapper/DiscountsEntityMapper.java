package pl.piasta.hotel.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.infrastructure.model.DiscountsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiscountsEntityMapper {

    public Optional<DiscountDetails> mapToDiscountDetails(Optional<DiscountsEntity> discountsEntity) {
        return discountsEntity.map(this::createDiscountDetails);
    }

    public List<DiscountDetails> mapToDiscount(List<DiscountsEntity> discounts) {
        return discounts.stream()
                .map(this::createDiscountDetails)
                .collect(Collectors.toList());
    }

    private DiscountDetails createDiscountDetails(DiscountsEntity discountsEntity) {
        return new DiscountDetails(discountsEntity.getCode(), discountsEntity.getValue());
    }
}
