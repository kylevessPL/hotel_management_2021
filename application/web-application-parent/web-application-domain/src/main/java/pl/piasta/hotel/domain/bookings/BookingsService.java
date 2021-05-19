package pl.piasta.hotel.domain.bookings;

import pl.piasta.hotel.domainmodel.bookings.Booking;
import pl.piasta.hotel.domainmodel.bookings.BookingCommand;
import pl.piasta.hotel.domainmodel.bookings.BookingInfo;

public interface BookingsService {

    Booking makeBooking(Integer userId, BookingCommand command);
    BookingInfo getBookingInfo(Integer id);
    void cancelBooking(Integer id);
    boolean hasPermission(Integer userId, Integer bookingId);
}
