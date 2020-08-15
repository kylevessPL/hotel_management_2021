package pl.piasta.hotel.api.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.rooms.mapper.RoomMapper;
import pl.piasta.hotel.domain.rooms.RoomsService;
import pl.piasta.hotel.dto.rooms.RoomDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RoomsServiceController {

    private final RoomMapper roomMapper;
    private final RoomsService roomsService;

    @GetMapping("/hotel/services/rooms")
    public List<RoomDto> getAllAvailableRoomsWithinDateRange(@RequestParam(name = "start-date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate, @RequestParam(name = "end-date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate, Pageable pageable) {

        List<Sort.Order> sortParams = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            String prop = order.getProperty();
            prop = prop.replace("-", "_");
            sortParams.add(new Sort.Order(order.getDirection(), prop));
        }

        return roomMapper.mapToDto(roomsService.getAllAvailableRoomsWithinDateRange(Date.valueOf(startDate), Date.valueOf(endDate), PageRequest.of(0, 50, Sort.by(sortParams))));
    }

}

