package pl.piasta.hotel.dto.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.dto.utils.PageMeta;

import java.util.List;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class UserBookingsPageResponse {

    private PageMeta meta;
    private List<BookingInfoResponse> content;
}
