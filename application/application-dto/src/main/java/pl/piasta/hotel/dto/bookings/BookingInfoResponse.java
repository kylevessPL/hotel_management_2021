package pl.piasta.hotel.dto.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.dto.rooms.RoomInfoResponse;

import java.math.BigDecimal;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class BookingInfoResponse {

    @Schema(description = "Booking date details")
    private BookingDate bookingDate;
    @Schema(description = "Booked room details")
    private RoomInfoResponse room;
    @Schema(description = "Booked room final price")
    private BigDecimal finalPrice;
    @Schema(description = "Booking payment status", example = "PAYED")
    private String paymentStatus;
}
