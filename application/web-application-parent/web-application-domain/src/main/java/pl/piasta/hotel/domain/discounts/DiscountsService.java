package pl.piasta.hotel.domain.discounts;

import pl.piasta.hotel.domainmodel.discounts.DiscountCommand;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;

public interface DiscountsService {

    void addDiscount(DiscountCommand command);
    void removeDiscount(String code);
    DiscountDetails getDiscountDetails(String code);
}
