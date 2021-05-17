package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.BookingsCustomersEntity;

public interface BookingsCustomersEntityDao extends JpaRepository<BookingsCustomersEntity, Integer> {
}
