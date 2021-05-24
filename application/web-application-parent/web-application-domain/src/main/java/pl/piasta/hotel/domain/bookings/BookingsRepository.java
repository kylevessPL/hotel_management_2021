package pl.piasta.hotel.domain.bookings;

import pl.piasta.hotel.domainmodel.bookings.BookingCancellationDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingFinalDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingsPage;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

import java.util.List;
import java.util.Optional;

public interface BookingsRepository {

    List<Integer> getBookingsRoomIdBetweenDates(DateDetails dateDetails);
    BookingsPage getAllBookingsInList(List<Integer> idList, PageProperties pageProperties, SortProperties sortProperties);
    Integer saveBooking(BookingDetails booking);
    Optional<BookingFinalDetails> getBookingFinalDetails(Integer bookingId);
    Optional<BookingCancellationDetails> getBookingCancellationDetails(Integer bookingId);
    void cancelBooking(Integer bookingId);
}
