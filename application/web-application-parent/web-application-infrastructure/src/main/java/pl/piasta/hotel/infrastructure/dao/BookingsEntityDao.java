package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.BookingsEntity;

import java.time.LocalDate;
import java.util.List;

public interface BookingsEntityDao extends JpaRepository<BookingsEntity, Integer> {

    List<BookingsEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);
}
