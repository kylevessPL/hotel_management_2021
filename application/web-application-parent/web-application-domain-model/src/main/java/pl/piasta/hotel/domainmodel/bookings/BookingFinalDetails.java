package pl.piasta.hotel.domainmodel.bookings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public final class BookingFinalDetails {

    private final BookingDate bookingDate;
    private final Integer roomId;
    private final BigDecimal finalPrice;
    private final BookingStatus bookingStatus;
}
