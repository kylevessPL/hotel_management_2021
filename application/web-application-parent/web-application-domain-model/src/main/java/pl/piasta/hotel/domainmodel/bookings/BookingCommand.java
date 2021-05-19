package pl.piasta.hotel.domainmodel.bookings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.customers.CustomerDetails;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BookingCommand {

    private Integer roomId;
    private Integer[] additionalServices;
    private DateDetails dateDetails;
    private String discountCode;
    private List<CustomerDetails> customers;
}
