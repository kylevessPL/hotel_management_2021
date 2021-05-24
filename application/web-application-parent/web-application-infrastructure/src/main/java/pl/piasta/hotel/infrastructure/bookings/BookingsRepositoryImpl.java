package pl.piasta.hotel.infrastructure.bookings;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.bookings.BookingsRepository;
import pl.piasta.hotel.domainmodel.bookings.BookingCancellationDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingFinalDetails;
import pl.piasta.hotel.domainmodel.bookings.BookingStatus;
import pl.piasta.hotel.domainmodel.bookings.BookingsPage;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;
import pl.piasta.hotel.infrastructure.dao.BookingsEntityDao;
import pl.piasta.hotel.infrastructure.mapper.BookingsEntityMapper;
import pl.piasta.hotel.infrastructure.model.BookingsEntity;
import pl.piasta.hotel.infrastructure.utils.SortUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
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
        return dao.findByStartDateLessThanAndEndDateGreaterThan(endDate, startDate)
                .stream()
                .map(BookingsEntity::getRoomId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookingsPage getAllBookingsInList(List<Integer> idList, PageProperties pageProperties, SortProperties sortProperties) {
        Integer page = pageProperties.getPage();
        Integer size = pageProperties.getSize();
        Sort sort = SortUtils.createSortProperty(sortProperties);
        Page<BookingsEntity> result = getAllInIdListPaged(idList, page, size, sort);
        return bookingsEntityMapper.mapToBookingsPage(
                result.hasContent() ? result.getContent() : Collections.emptyList(),
                result.isFirst(),
                result.isLast(),
                result.hasPrevious(),
                result.hasNext(),
                result.getNumber() + 1,
                result.getTotalPages(),
                result.getTotalElements());
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

    private Page<BookingsEntity> getAllInIdListPaged(
            List<Integer> idList,
            Integer page, Integer size, Sort sort) {
        Page<BookingsEntity> result = dao.findAllByIdIn(idList, PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = dao.findAllByIdIn(idList, PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    void updateEntity(BookingsEntity booking, BookingDetails bookingDetails) {
        booking.setBookDate(Instant.now());
        booking.setStartDate(bookingDetails.getDateDetails().getStartDate());
        booking.setEndDate(bookingDetails.getDateDetails().getEndDate());
        booking.setRoomId(bookingDetails.getRoomDetails().getRoomId());
        booking.setFinalPrice(bookingDetails.getFinalPrice());
    }
}
