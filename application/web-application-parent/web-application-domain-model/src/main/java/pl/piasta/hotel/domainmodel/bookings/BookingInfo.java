package pl.piasta.hotel.domainmodel.bookings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.piasta.hotel.domainmodel.payments.PaymentStatus;
import pl.piasta.hotel.domainmodel.rooms.RoomInfo;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public final class BookingInfo {

    private final BookingDate bookingDate;
    private final RoomInfo room;
    private final BigDecimal finalPrice;
    private final PaymentStatus paymentStatus;
}
