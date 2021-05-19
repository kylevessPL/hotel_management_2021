package pl.piasta.hotel.domain.usersbookings;

import java.util.Optional;

public interface UsersBookingsRepository {

    Optional<Integer> getBookingUserId(Integer bookingId);
    void saveUserBooking(Integer userId, Integer bookingId);
}
