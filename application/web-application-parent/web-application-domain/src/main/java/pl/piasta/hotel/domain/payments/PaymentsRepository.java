package pl.piasta.hotel.domain.payments;

public interface PaymentsRepository {

    Integer getBookingPaymentFormId(Integer bookingId);
}
