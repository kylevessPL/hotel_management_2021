package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.BookingsEntity;

import java.time.LocalDate;
import java.util.List;

public interface BookingsEntityDao extends JpaRepository<BookingsEntity, Integer> {

    Page<BookingsEntity> findAllByIdIn(List<Integer> idList, Pageable pageable);
    List<BookingsEntity> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);
}
