package pl.piasta.hotel.dto.bookings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.dto.paymentforms.PaymentFormDto;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto {

    private Integer bookingId;
    private BigDecimal finalPrice;
    private List<PaymentFormDto> paymentForms;

}