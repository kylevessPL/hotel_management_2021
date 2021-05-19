package pl.piasta.hotel.infrastructure.bookings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.bookings.BookingsRepository;
import pl.piasta.hotel.domainmodel.bookings.BookingCancellationDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingFinalDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingStatus;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.infrastructure.dao.BookingsEntityDao;
import pl.piasta.hotel.infrastructure.mapper.BookingsEntityMapper;
import pl.piasta.hotel.infrastructure.model.BookingsEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookingsRepositoryImpl implements BookingsRepository {

    private final BookingsEntityMapper bookingsEntityMapper;
    private final BookingsEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getBookingsRoomIdBetweenDates(DateDetails dateDetails) {
        LocalDate startDate = dateDetails.getStartDate();
        LocalDate endDate = dateDetails.getEndDate();
        return dao.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(startDate, endDate)
                .stream()
                .map(BookingsEntity::getRoomId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer saveBooking(BookingDetails bookingDetails) {
        BookingsEntity booking = new BookingsEntity();
        updateEntity(booking, bookingDetails);
        return dao.save(booking).getId();
    }

    @Override
    @Transactional
    public void cancelBooking(Integer bookingId) {
        BookingsEntity booking = dao.getOne(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        dao.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingFinalDetails> getBookingFinalDetails(Integer bookingId) {
        Optional<BookingsEntity> bookingsEntity = dao.findById(bookingId);
        return bookingsEntityMapper.mapToBookingFinalDetails(bookingsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookingCancellationDetails> getBookingCancellationDetails(Integer bookingId) {
        Optional<BookingsEntity> bookingsEntity = dao.findById(bookingId);
        return bookingsEntityMapper.mapToBookingCancellationDetails(bookingsEntity);
    }

    void updateEntity(BookingsEntity booking, BookingDetails bookingDetails) {
        booking.setBookDate(Instant.now());
        booking.setStartDate(bookingDetails.getDateDetails().getStartDate());
        booking.setEndDate(bookingDetails.getDateDetails().getEndDate());
        booking.setRoomId(bookingDetails.getRoomDetails().getRoomId());
        booking.setFinalPrice(bookingDetails.getFinalPrice());
    }
}
