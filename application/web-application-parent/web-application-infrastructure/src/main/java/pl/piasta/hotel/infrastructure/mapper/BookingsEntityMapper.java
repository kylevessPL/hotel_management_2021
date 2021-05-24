package pl.piasta.hotel.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.bookings.BookingCancellationDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingDate;
import pl.piasta.hotel.domainmodel.bookings.BookingFinalDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingsPage;
import pl.piasta.hotel.domainmodel.utils.PageMeta;
import pl.piasta.hotel.infrastructure.model.BookingsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingsEntityMapper {

    public BookingsPage mapToBookingsPage(
            List<BookingsEntity> bookingsEntityList,
            boolean isFirst, boolean isLast, boolean hasPrev, boolean hasNext,
            Integer currentPage, Integer totalPage, Long totalCount) {
        List<BookingFinalDetails> bookingFinalDetailsList = bookingsEntityList
                .stream()
                .map(e -> new BookingFinalDetails(mapToBookingDate(e), e.getRoomId(), e.getStatus()))
                .collect(Collectors.toList());
        return new BookingsPage(
                new PageMeta(isFirst, isLast, hasPrev, hasNext, currentPage, totalPage, totalCount),
                bookingFinalDetailsList);
    }

    public Optional<BookingFinalDetails> mapToBookingFinalDetails(Optional<BookingsEntity> bookingsEntity) {
        return bookingsEntity.map(e -> new BookingFinalDetails(mapToBookingDate(e), e.getRoomId(), e.getStatus()));
    }

    public Optional<BookingCancellationDetails> mapToBookingCancellationDetails(Optional<BookingsEntity> bookingsEntity) {
        return bookingsEntity.map(e -> new BookingCancellationDetails(mapToBookingDate(e), e.getStatus()));
    }

    private BookingDate mapToBookingDate(BookingsEntity booking) {
        return new BookingDate(booking.getBookDate(), booking.getStartDate(), booking.getEndDate());
    }
}
