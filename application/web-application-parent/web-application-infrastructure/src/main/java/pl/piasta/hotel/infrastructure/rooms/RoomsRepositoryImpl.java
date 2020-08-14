package pl.piasta.hotel.infrastructure.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.piasta.hotel.domain.model.rooms.Room;
import pl.piasta.hotel.domain.rooms.RoomsRepository;
import pl.piasta.hotel.infrastructure.dao.RoomsEntityDao;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoomsRepositoryImpl implements RoomsRepository {

    private final RoomsEntityDao dao;

    @Override
    public List<Room> getAllAvailableRooms(Pageable pageable) {
        return dao.findAllAvailable(pageable).stream()
                .map(entity -> new Room(entity.getId(), entity.getBedAmount(), entity.getStandardPrice()))
                .collect(Collectors.toList());
    }

}
