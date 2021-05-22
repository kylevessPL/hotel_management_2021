package pl.piasta.hotel.api.bookings;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.bookings.mapper.BookingsMapper;
import pl.piasta.hotel.domain.bookings.BookingsService;
import pl.piasta.hotel.domainmodel.bookings.BookingInfo;
import pl.piasta.hotel.dto.bookings.BookingInfoResponse;

@Tag(name = "Bookings API", description = "API performing operations on booking resources")
@RestController
@RequestMapping("${app.api.base-path}/bookings")
@RequiredArgsConstructor
public class BookingsServiceController {

    private final BookingsService service;
    private final BookingsMapper mapper;

    @Operation(
            summary = "Get booking information",
            operationId = "getBookingInfo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @securityControl.hasBookingPermission(id)")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingInfoResponse getBookingInfo(@Parameter(description = "Booking id") @PathVariable Integer id) {
        BookingInfo bookingInfo = service.getBookingInfo(id);
        return mapper.mapToResponse(bookingInfo);
    }

    @Operation(
            summary = "Cancel booking",
            operationId = "cancelBooking"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "409", description = "Booking expired or already cancelled"),
            @ApiResponse(responseCode = "422", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN') OR @securityControl.hasBookingPermission(id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}/cancellation", produces = MediaType.APPLICATION_JSON_VALUE)
    public void cancelBooking(@Parameter(description = "Booking id") @PathVariable Integer id) {
        service.cancelBooking(id);
    }
}
