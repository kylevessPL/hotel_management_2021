package pl.piasta.hotel.domain.discounts;

import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;

import java.util.List;
import java.util.Optional;

public interface DiscountsRepository {

    List<DiscountDetails> getDiscounts();
    Optional<DiscountDetails> getDiscountDetails(String code);
    void addDiscount(String code, Integer value);
    int removeDiscount(String code);
    boolean discountExists(String code);
}
