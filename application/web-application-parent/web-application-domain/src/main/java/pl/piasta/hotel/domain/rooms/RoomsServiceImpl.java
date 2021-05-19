package pl.piasta.hotel.domain.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.rooms.Room;
import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.domainmodel.utils.SortDir;
import pl.piasta.hotel.domainmodel.utils.SortProperties;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private final RoomsRepository repository;

    @Override
    public List<Room> getAllAvailableRoomsWithinDateRange(RoomCommand roomCommand) {
        DateDetails dateDetails = roomCommand.getDateDetails();
        SortProperties sortProperties = roomCommand.getSortProperties();
        List<Room> rooms = repository.getAllAvailableRoomsWithinDateRange(dateDetails.getStartDate(), dateDetails.getEndDate());
        sortRooms(rooms, sortProperties);
        return rooms;
    }

    private void sortRooms(List<Room> rooms, SortProperties sortProperties) {
        Comparator<Room> comparator;
        if(sortProperties.getSortBy().equalsIgnoreCase("bedAmount")) {
            comparator = Comparator.comparing(Room::getBedAmount).thenComparing(Room::getStandardPrice);
        } else if(sortProperties.getSortBy().equalsIgnoreCase("standardPrice")) {
            comparator = Comparator.comparing(Room::getStandardPrice).thenComparing(Room::getBedAmount);
        } else {
            comparator = Comparator.comparing(Room::getId);
        }
        if(sortProperties.getSortDir() == SortDir.ASC) {
            rooms.sort(comparator);
        } else {
            rooms.sort(comparator.reversed());
        }
    }
}
