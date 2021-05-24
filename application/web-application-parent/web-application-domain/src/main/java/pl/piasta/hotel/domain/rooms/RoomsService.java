package pl.piasta.hotel.domain.rooms;

import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.domainmodel.rooms.RoomsPage;

public interface RoomsService {

    RoomsPage getAllAvailableRoomsWithinDateRange(RoomCommand roomCommand);
}
