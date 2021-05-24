package pl.piasta.hotel.domainmodel.bookings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.piasta.hotel.domainmodel.utils.PageMeta;

import java.util.List;

@RequiredArgsConstructor
@Getter
public final class BookingsPage {

    private final PageMeta meta;
    private final List<BookingFinalDetails> content;
}
