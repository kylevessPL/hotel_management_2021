package pl.piasta.hotel.api.discounts.mapper;

import org.mapstruct.Mapper;
import pl.piasta.hotel.api.discounts.DiscountRequest;
import pl.piasta.hotel.domainmodel.discounts.DiscountCommand;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.dto.discounts.DiscountDetailsResponse;

@Mapper
public interface DiscountsMapper {

    DiscountCommand mapToCommand(DiscountRequest command);
    DiscountDetailsResponse mapToResponse(DiscountDetails discountDetails);
}
