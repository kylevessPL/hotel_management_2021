package pl.piasta.hotel.domain.discounts;

import pl.piasta.hotel.domainmodel.discounts.DiscountCommand;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;

import java.util.List;

public interface DiscountsService {

    void addDiscount(DiscountCommand command);
    void removeDiscount(String code);
    DiscountDetails getDiscountDetails(String code);
    List<DiscountDetails> getAllDiscounts();
}
