package pl.piasta.hotel.infrastructure.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domain.rooms.RoomsRepository;
import pl.piasta.hotel.domainmodel.rooms.DateDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomFinalDetails;
import pl.piasta.hotel.domainmodel.rooms.RoomsPage;
import pl.piasta.hotel.domainmodel.utils.PageProperties;
import pl.piasta.hotel.domainmodel.utils.SortProperties;
import pl.piasta.hotel.infrastructure.dao.AmenitiesEntityDao;
import pl.piasta.hotel.infrastructure.dao.BookingsEntityDao;
import pl.piasta.hotel.infrastructure.dao.RoomsAmenitiesEntityDao;
import pl.piasta.hotel.infrastructure.dao.RoomsEntityDao;
import pl.piasta.hotel.infrastructure.mapper.RoomsEntityMapper;
import pl.piasta.hotel.infrastructure.model.AmenitiesEntity;
import pl.piasta.hotel.infrastructure.model.BookingsEntity;
import pl.piasta.hotel.infrastructure.model.RoomsAmenitiesEntity;
import pl.piasta.hotel.infrastructure.model.RoomsEntity;
import pl.piasta.hotel.infrastructure.utils.SortUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoomsRepositoryImpl implements RoomsRepository {

    private final RoomsEntityMapper roomsEntityMapper;
    private final RoomsEntityDao roomsDao;
    private final BookingsEntityDao bookingsDao;
    private final RoomsAmenitiesEntityDao roomAmenitiesDao;
    private final AmenitiesEntityDao amenitiesDao;

    @Override
    @Transactional(readOnly = true)
    public RoomsPage getAllAvailableRoomsWithinDateRange(DateDetails dateDetails, PageProperties pageProperties, SortProperties sortProperties) {
        List<Integer> bookedRooms = bookingsDao.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                dateDetails.getStartDate(), dateDetails.getEndDate())
                .stream()
                .map(BookingsEntity::getRoomId)
                .collect(Collectors.toList());
        Sort sort = SortUtils.createSortProperty(sortProperties);
        Page<RoomsEntity> result = bookedRooms.isEmpty()
                ? getAllPaged(pageProperties.getPage(), pageProperties.getSize(), sort)
                : getAllByIdNotInPaged(bookedRooms, pageProperties.getPage(), pageProperties.getSize(), sort);
        List<RoomsAmenitiesEntity> roomAmenities = roomAmenitiesDao.findAllByRoomIdIn(result.getContent()
                .stream()
                .map(RoomsEntity::getId)
                .distinct()
                .collect(Collectors.toList()));
        List<AmenitiesEntity> amenities = amenitiesDao.findAllByIdIn(roomAmenities
                .stream()
                .map(RoomsAmenitiesEntity::getAmenityId)
                .distinct()
                .collect(Collectors.toList()));
        Map<Integer, List<AmenitiesEntity>> roomAmenitiesMap = new HashMap<>();
        result.getContent().forEach(room -> {
            List<Integer> amenityList = roomAmenities
                    .stream()
                    .filter(a -> a.getRoomId().equals(room.getId()))
                    .map(RoomsAmenitiesEntity::getAmenityId)
                    .collect(Collectors.toList());
            roomAmenitiesMap.put(room.getId(), amenities
                    .stream()
                    .filter(amenity -> amenityList.contains(amenity.getId()))
                    .collect(Collectors.toList()));
        });
        return roomsEntityMapper.mapToRoom(result, roomAmenitiesMap);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomDetails> getRoomDetails(Integer roomId) {
        Optional<RoomsEntity> roomsEntity = roomsDao.findById(roomId);
        return roomsEntityMapper.mapToRoomDetails(roomsEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomFinalDetails getRoomFinalDetails(Integer roomId) {
        RoomsEntity room = roomsDao.getOne(roomId);
        List<RoomsAmenitiesEntity> roomAmenities = roomAmenitiesDao.findAllByRoomId(room.getId());
        List<AmenitiesEntity> amenities = amenitiesDao.findAllByIdIn(roomAmenities
                .stream()
                .map(RoomsAmenitiesEntity::getAmenityId)
                .distinct()
                .collect(Collectors.toList()));
        return roomsEntityMapper.mapToRoomFinalDetails(room, amenities);
    }

    private Page<RoomsEntity> getAllPaged(Integer page, Integer size, Sort sort) {
        Page<RoomsEntity> result = roomsDao.findAll(PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = roomsDao.findAll(PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }

    private Page<RoomsEntity> getAllByIdNotInPaged(List<Integer> idList, Integer page, Integer size, Sort sort) {
        Page<RoomsEntity> result = roomsDao.findByIdNotIn(idList, PageRequest.of(page - 1, size, sort));
        if (!result.hasContent() && result.getTotalPages() > 0) {
            result = roomsDao.findByIdNotIn(idList, PageRequest.of(result.getTotalPages() - 1, size, sort));
        }
        return result;
    }
}
