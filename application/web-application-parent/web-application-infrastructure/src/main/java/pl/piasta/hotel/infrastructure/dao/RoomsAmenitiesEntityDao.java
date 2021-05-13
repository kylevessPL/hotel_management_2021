package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.RoomsAmenitiesEntity;

import java.util.List;

public interface RoomsAmenitiesEntityDao extends JpaRepository<RoomsAmenitiesEntity, Integer> {

    List<RoomsAmenitiesEntity> findAllByRoomIdIn(List<Integer> roomId);
    List<RoomsAmenitiesEntity> findAllByRoomId(Integer roomId);

}
