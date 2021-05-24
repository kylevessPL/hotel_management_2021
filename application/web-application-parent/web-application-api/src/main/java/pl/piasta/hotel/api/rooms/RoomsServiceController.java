package pl.piasta.hotel.api.rooms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.rooms.mapper.RoomsMapper;
import pl.piasta.hotel.api.utils.PageResponse;
import pl.piasta.hotel.domain.rooms.RoomsService;
import pl.piasta.hotel.domainmodel.rooms.RoomCommand;
import pl.piasta.hotel.dto.rooms.RoomResponse;
import pl.piasta.hotel.dto.rooms.RoomsPageResponse;

import javax.validation.Valid;

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
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoomResponse.class))),
                    headers = {
                            @Header(name = HttpHeaders.LINK, description = "Pagination links", schema = @Schema(type = "string")),
                            @Header(name = "X-Count-Per-Page", description = "Number of results per page", schema = @Schema(type = "integer")),
                            @Header(name = "X-Current-Page", description = "Current page", schema = @Schema(type = "integer")),
                            @Header(name = "X-Total-Count", description = "Total number of results", schema = @Schema(type = "integer")),
                            @Header(name = "X-Total-Pages", description = "Total number of pages", schema = @Schema(type = "integer"))
                    }),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<RoomResponse> getAllAvailableRooms(@ParameterObject @Valid RoomPageQuery query) {
        RoomCommand command = mapper.mapToCommand(query);
        RoomsPageResponse response = mapper.mapToResponse(service.getAllAvailableRoomsWithinDateRange(command));
        return new PageResponse<>(query.getSize(), response.getMeta(), response.getContent());
    }
}

