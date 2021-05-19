package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.UsersBookingsEntity;

public interface UsersBookingsEntityDao extends JpaRepository<UsersBookingsEntity, Integer> {
}