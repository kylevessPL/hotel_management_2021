package pl.piasta.hotel.domain.bookings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.piasta.hotel.domain.paymentforms.PaymentFormsRepository;
import pl.piasta.hotel.domain.payments.PaymentsRepository;
import pl.piasta.hotel.domainmodel.amenities.Amenity;
import pl.piasta.hotel.domainmodel.bookings.BookingStatus;
import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;
import pl.piasta.hotel.domainmodel.payments.PaymentStatus;
import pl.piasta.hotel.domainmodel.rooms.RoomFinalDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomInfo;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor

public final class BookingUtils {

    private final PaymentsRepository paymentsRepository;
    private final PaymentFormsRepository paymentFormsRepository;

    public PaymentStatus createPaymentStatus(Integer bookingId, BookingStatus bookingStatus) {
        PaymentStatus paymentStatus;
        if(bookingStatus.equals(BookingStatus.NOT_CONFIRMED)) {
            paymentStatus = PaymentStatus.NO_PAYMENT_INFO;
        } else if(bookingStatus.equals(BookingStatus.CANCELLED)) {
            paymentStatus = PaymentStatus.BOOKING_CANCELLED;
        } else {
            PaymentForm paymentForm = getBookingPaymentForm(bookingId);
            if(Arrays.asList("Cash", "Check").contains(paymentForm.getName())) {
                paymentStatus = PaymentStatus.UPPON_ARRIVAL;
            } else {
                paymentStatus = PaymentStatus.PAYED;
            }
        }
        return paymentStatus;
    }

    public RoomInfo createRoomInfo(RoomFinalDetails roomFinalDetails) {
        String roomNumber = roomFinalDetails.getRoomNumber();
        Integer bedAmount = roomFinalDetails.getBedAmount();
        List<Amenity> amenities = roomFinalDetails.getAmenities();
        return new RoomInfo(roomNumber, bedAmount, amenities);
    }

    private PaymentForm getBookingPaymentForm(Integer bookingId) {
        Integer paymentFormId = paymentsRepository.getBookingPaymentFormId(bookingId);
        return paymentFormsRepository.getPaymentForm(paymentFormId);
    }
}
