package pl.piasta.hotel.domain.usersbookings;

import java.util.List;
import java.util.Optional;

public interface UsersBookingsRepository {

    List<Integer> getAllUserBookingsIdList(Integer userId);
    Optional<Integer> getBookingUserId(Integer bookingId);
    void saveUserBooking(Integer userId, Integer bookingId);
}
