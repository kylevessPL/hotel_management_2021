package pl.piasta.hotel.api.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.piasta.hotel.api.utils.ValidateDates;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Schema
@ValidateDates
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest implements Serializable {

    @Schema(description = "Room id", example = "1")
    @NotNull
    @Min(1)
    private Integer roomId;
    @Schema(description = "Additional services id's", example = "[\"1\", \"2\"]")
    private Integer[] additionalServices;
    @Schema(description = "Booking start date")
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @Schema(description = "Booking end date")
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    @Schema(description = "Discount code", example = "PROMO5")
    @Size(min = 2, max = 20)
    private String discountCode;
    @Schema(description = "Customers")
    @NotEmpty
    private List<Customer> customers;
}
