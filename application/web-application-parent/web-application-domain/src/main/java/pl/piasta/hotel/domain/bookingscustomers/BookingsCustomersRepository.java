package pl.piasta.hotel.domain.bookingscustomers;

public interface BookingsCustomersRepository {

    void saveBookingCustomer(Integer bookingId, Integer customerId);
}
