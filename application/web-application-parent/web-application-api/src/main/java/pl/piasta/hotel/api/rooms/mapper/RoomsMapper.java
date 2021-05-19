package pl.piasta.hotel.api.rooms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.piasta.hotel.api.rooms.RoomQuery;
import pl.piasta.hotel.domainmodel.rooms.Room;
import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.domainmodel.rooms.RoomInfo;
import pl.piasta.hotel.dto.rooms.RoomInfoResponse;
import pl.piasta.hotel.dto.rooms.RoomResponse;

import java.util.List;

@Mapper
public interface RoomsMapper {

    List<RoomResponse> mapToResponse(List<Room> rooms);
    RoomInfoResponse mapToResponse(RoomInfo room);
    @Mapping(source = "startDate", target = "dateDetails.startDate")
    @Mapping(source = "endDate", target = "dateDetails.endDate")
    @Mapping(source = "sortBy", target = "sortProperties.sortBy")
    @Mapping(source = "sortDir", target = "sortProperties.sortDir")
    RoomCommand mapToCommand(RoomQuery roomQuery);
}
