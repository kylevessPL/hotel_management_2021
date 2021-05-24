package pl.piasta.hotel.api.rooms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.piasta.hotel.api.rooms.RoomPageQuery;
import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.domainmodel.rooms.RoomInfo;
import pl.piasta.hotel.domainmodel.rooms.RoomsPage;
import pl.piasta.hotel.dto.rooms.RoomInfoResponse;
import pl.piasta.hotel.dto.rooms.RoomsPageResponse;

@Mapper
public interface RoomsMapper {

    RoomsPageResponse mapToResponse(RoomsPage roomsPage);
    RoomInfoResponse mapToResponse(RoomInfo room);
    @Mapping(source = "startDate", target = "dateDetails.startDate")
    @Mapping(source = "endDate", target = "dateDetails.endDate")
    @Mapping(source = "page", target = "pageProperties.page")
    @Mapping(source = "size", target = "pageProperties.size")
    @Mapping(source = "sortBy", target = "sortProperties.sortBy")
    @Mapping(source = "sortDir", target = "sortProperties.sortDir")
    RoomCommand mapToCommand(RoomPageQuery roomPageQuery);
}
