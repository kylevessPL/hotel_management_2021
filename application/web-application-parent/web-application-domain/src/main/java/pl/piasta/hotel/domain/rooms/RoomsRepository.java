package pl.piasta.hotel.domain.rooms;

import pl.piasta.hotel.domainmodel.rooms.Room;
import pl.piasta.hotel.domainmodel.rooms.RoomDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomFinalDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomsRepository {

    List<Room> getAllAvailableRoomsWithinDateRange(LocalDate startDate, LocalDate endDate);
    Optional<RoomDetails> getRoomDetails(Integer roomId);
    RoomFinalDetails getRoomFinalDetails(Integer roomId);
}
