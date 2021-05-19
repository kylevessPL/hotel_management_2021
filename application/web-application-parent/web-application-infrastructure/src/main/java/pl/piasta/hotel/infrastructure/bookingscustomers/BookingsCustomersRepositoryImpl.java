package pl.piasta.hotel.infrastructure.bookingscustomers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.bookingscustomers.BookingsCustomersRepository;
import pl.piasta.hotel.infrastructure.dao.BookingsCustomersEntityDao;
import pl.piasta.hotel.infrastructure.model.BookingsCustomersEntity;

@Repository
@RequiredArgsConstructor
public class BookingsCustomersRepositoryImpl implements BookingsCustomersRepository {

    private final BookingsCustomersEntityDao dao;

    @Override
    @Transactional
    public void saveBookingCustomer(Integer bookingId, Integer customerId) {
        BookingsCustomersEntity entity = new BookingsCustomersEntity();
        updateEntity(entity, bookingId, customerId);
        dao.save(entity);
    }

    void updateEntity(BookingsCustomersEntity entity, Integer bookingId, Integer customerId) {
        entity.setBookingId(bookingId);
        entity.setCustomerId(customerId);
    }
}
