package pl.piasta.hotel.domainmodel.bookings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public final class BookingDate {

    private final Instant bookDate;
    private final LocalDate startDate;
    private final LocalDate endDate;
}
