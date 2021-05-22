package pl.piasta.hotel.api.rooms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.rooms.mapper.RoomsMapper;
import pl.piasta.hotel.domain.rooms.RoomsService;
import pl.piasta.hotel.domainmodel.rooms.Room;
import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.dto.rooms.RoomResponse;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Rooms API", description = "API performing operations on room resources")
@RestController
@RequestMapping("${app.api.base-path}/rooms")
@RequiredArgsConstructor
public class RoomsServiceController {

    private final RoomsMapper mapper;
    private final RoomsService service;

    @SecurityRequirements
    @Operation(
            summary = "Get all available rooms",
            operationId = "getAllAvailableRooms"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoomResponse> getAllAvailableRooms(@ParameterObject @Valid RoomQuery roomQuery) {
        RoomCommand command = mapper.mapToCommand(roomQuery);
        List<Room> rooms = service.getAllAvailableRoomsWithinDateRange(command);
        return mapper.mapToResponse(rooms);
    }
}

