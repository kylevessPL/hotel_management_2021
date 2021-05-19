package pl.piasta.hotel.domain.discounts;

import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;

import java.util.Optional;

public interface DiscountsRepository {

    Optional<DiscountDetails> getDiscountDetails(String code);
    void addDiscount(String code, Integer value);
    boolean removeDiscount(String code);
    boolean discountExists(String code);
}
