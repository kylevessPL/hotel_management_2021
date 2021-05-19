package pl.piasta.hotel.api.bookings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.piasta.hotel.api.bookings.BookingRequest;
import pl.piasta.hotel.api.paymentforms.mapper.PaymentFormsMapper;
import pl.piasta.hotel.api.rooms.mapper.RoomsMapper;
import pl.piasta.hotel.domainmodel.bookings.Booking;
import pl.piasta.hotel.domainmodel.bookings.BookingCommand;
import pl.piasta.hotel.domainmodel.bookings.BookingInfo;
import pl.piasta.hotel.dto.bookings.BookingInfoResponse;
import pl.piasta.hotel.dto.bookings.BookingResponse;

@Mapper(uses = {PaymentFormsMapper.class, RoomsMapper.class, CustomersMapper.class})
public interface BookingsMapper {

    BookingResponse mapToResponse(Booking booking);
    @Mapping(source = "startDate", target = "dateDetails.startDate")
    @Mapping(source = "endDate", target = "dateDetails.endDate")
    BookingCommand mapToCommand(BookingRequest bookingRequest);
    @Mapping(source = "startDate", target = "period.startDate")
    @Mapping(source = "endDate", target = "period.endDate")
    BookingInfoResponse mapToResponse(BookingInfo bookingInfo);
}
