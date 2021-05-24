package pl.piasta.hotel.domain.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.domainmodel.rooms.RoomsPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private final RoomsRepository repository;

    @Override
    public RoomsPage getAllAvailableRoomsWithinDateRange(RoomCommand roomCommand) {
        DateDetails dateDetails = roomCommand.getDateDetails();
        PageProperties pageProperties = roomCommand.getPageProperties();
        SortProperties sortProperties = roomCommand.getSortProperties();
        return repository.getAllAvailableRoomsWithinDateRange(dateDetails, pageProperties, sortProperties);
    }
}
