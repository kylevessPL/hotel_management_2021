package pl.piasta.hotel.domain.rooms;

import pl.piasta.hotel.domain.model.rooms.Room;
import pl.piasta.hotel.domain.model.rooms.utils.RoomDetails;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RoomsRepository {

    List<Room> getAllAvailableRoomsWithinDateRange(Date startDate, Date endDate);
    Optional<RoomDetails> getRoomDetailsByRoomId(Integer roomId);

}
