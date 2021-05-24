package pl.piasta.hotel.domain.rooms;

import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomFinalDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomsPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

import java.util.Optional;

public interface RoomsRepository {

    RoomsPage getAllAvailableRoomsWithinDateRange(DateDetails dateDetails, PageProperties pageProperties, SortProperties sortProperties);
    Optional<RoomDetails> getRoomDetails(Integer roomId);
    RoomFinalDetails getRoomFinalDetails(Integer roomId);
}
