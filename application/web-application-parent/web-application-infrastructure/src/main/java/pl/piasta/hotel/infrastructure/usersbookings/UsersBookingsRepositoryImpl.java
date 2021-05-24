package pl.piasta.hotel.infrastructure.usersbookings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.usersbookings.UsersBookingsRepository;
import pl.piasta.hotel.infrastructure.dao.UsersBookingsEntityDao;
import pl.piasta.hotel.infrastructure.model.UsersBookingsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UsersBookingsRepositoryImpl implements UsersBookingsRepository {

    private final UsersBookingsEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllUserBookingsIdList(Integer userId) {
        return dao.findAllBookingIdByUserId(userId).stream()
                .mapToInt(UsersBookingsEntity::getBookingId)
                .boxed().collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Integer> getBookingUserId(Integer bookingId) {
        return dao.findById(bookingId)
                .flatMap(e -> Optional.of(e.getUserId()));
    }

    @Override
    @Transactional
    public void saveUserBooking(Integer userId, Integer bookingId) {
        UsersBookingsEntity entity = new UsersBookingsEntity();
        updateEntity(entity, userId, bookingId);
        dao.save(entity);
    }

    void updateEntity(UsersBookingsEntity entity, Integer userId, Integer bookingId) {
        entity.setUserId(userId);
        entity.setBookingId(bookingId);
    }
}
