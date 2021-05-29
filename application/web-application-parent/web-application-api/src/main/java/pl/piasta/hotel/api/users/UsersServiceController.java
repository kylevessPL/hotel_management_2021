package pl.piasta.hotel.api.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piasta.hotel.api.bookings.BookingRequest;
import pl.piasta.hotel.api.bookings.mapper.BookingsMapper;
import pl.piasta.hotel.api.users.mapper.UsersMapper;
import pl.piasta.hotel.api.utils.PageResponse;
import pl.piasta.hotel.api.utils.ValidateMultipartFile;
import pl.piasta.hotel.domain.bookings.BookingsService;
import pl.piasta.hotel.domain.security.UserDetailsImpl;
import pl.piasta.hotel.domain.users.UsersService;
import pl.piasta.hotel.domainmodel.bookings.Booking;
import pl.piasta.hotel.domainmodel.bookings.BookingCommand;
import pl.piasta.hotel.domainmodel.utils.PageCommand;
import pl.piasta.hotel.dto.bookings.BookingInfoResponse;
import pl.piasta.hotel.dto.bookings.BookingResponse;
import pl.piasta.hotel.dto.bookings.UserBookingsPageResponse;
import pl.piasta.hotel.dto.users.AvatarImageResponse;
import pl.piasta.hotel.dto.users.PagedUserInfo;
import pl.piasta.hotel.dto.users.UsersPageResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "Users API", description = "API performing operations on user resources")
@RestController
@RequestMapping("${app.api.base-path}/users")
@RequiredArgsConstructor
public class UsersServiceController {

    private final UsersMapper usersMapper;
    private final BookingsMapper bookingsMapper;
    private final UsersService usersService;
    private final BookingsService bookingsService;

    @Operation(
            summary = "Get all users",
            operationId = "getAllUsers"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PagedUserInfo.class))),
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
    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<PagedUserInfo> getAllUsers(@ParameterObject @Valid UsersPageQuery query) {
        PageCommand command = usersMapper.mapToCommand(query);
        UsersPageResponse response = usersMapper.mapToResponse(usersService.getAllUsers(command));
        return new PageResponse<>(query.getSize(), response.getMeta(), response.getContent());
    }

    @Operation(
            summary = "Update user account status",
            operationId = "updateUserAccountStatus"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccountStatus(
            Authentication authentication,
            @Parameter(description = "User id") @PathVariable Integer id,
            @Valid @RequestBody UpdateAccountStatusRequest request) {
        usersService.updateAccountStatus(id, usersMapper.mapToCommand(request));
    }

    @Operation(
            summary = "Update current user password",
            operationId = "updateCurrentUserPassword"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "422", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/current/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUserPassword(Authentication authentication, @Valid @RequestBody UpdateUserPasswordRequest request) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        usersService.updateUserPassword(id, usersMapper.mapToCommand(request));
    }

    @Operation(
            summary = "Update current user avatar image",
            operationId = "updateCurrentUserAvatar"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "413", description = "File size too large"),
            @ApiResponse(responseCode = "417", description = "File type not supported"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(
            value = "/current/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUserAvatar(
            Authentication authentication,
            @ValidateMultipartFile(acceptedTypes = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE }) @RequestParam MultipartFile file) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        usersService.updateUserAvatar(id, file);
    }

    @Operation(
            summary = "Remove current user avatar image",
            operationId = "removeCurrentUserAvatar"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/current/avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeUserAvatar(Authentication authentication) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        usersService.removeUserAvatar(id);
    }

    @Operation(
            summary = "Get current user avatar image",
            operationId = "getCurrentUserAvatar"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content,
                    headers = {
                            @Header(
                                    name = HttpHeaders.CONTENT_DISPOSITION,
                                    description = "Attachment type and filename",
                                    schema = @Schema(type = "string")),
                            @Header(
                                    name = HttpHeaders.CONTENT_LENGTH,
                                    description = "Attachment size in bytes",
                                    schema = @Schema(type = "string")),
                    }),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "409", description = "Avatar image not exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/current/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getUserAvatar(Authentication authentication, HttpServletResponse response) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        AvatarImageResponse avatar = usersMapper.mapToResponse(usersService.getUserAvatar(id));
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment()
                        .filename(avatar.getName())
                        .build().toString());
        response.setContentLength(avatar.getData().length);
        return avatar.getData();
    }

    @Operation(
            summary = "Get current user info",
            operationId = "getCurrentUserInfo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/current/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDetails getCurrentUserInfo(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getDetails());
    }

    @Operation(
            summary = "Book a room",
            operationId = "makeBooking"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success",
                    content = @Content,
                    headers = @Header(
                            name = HttpHeaders.LOCATION,
                            description = "Location of the created resource",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "409", description = "Room unavailable or max people exceeded", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/current/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookingResponse makeBooking(
            Authentication authentication,
            @RequestBody @Valid BookingRequest bookingRequest,
            HttpServletResponse response) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        BookingCommand command = bookingsMapper.mapToCommand(bookingRequest);
        Booking booking = bookingsService.makeBooking(id, command);
        BookingResponse bookingResponse = bookingsMapper.mapToResponse(booking);
        String path = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/bookings/{id}")
                .buildAndExpand(bookingResponse.getBookingId())
                .encode()
                .getPath();
        response.setHeader(HttpHeaders.LOCATION, path);
        response.setContentLength(0);
        return bookingResponse;
    }

    @Operation(
            summary = "Get all current user bookings",
            operationId = "getAllCurrentUserBookings"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookingInfoResponse.class))),
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
    @GetMapping(value = "/current/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<BookingInfoResponse> getAllBookings(
            Authentication authentication,
            @ParameterObject @Valid UserBookingsPageQuery query) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        PageCommand command = usersMapper.mapToCommand(query);
        UserBookingsPageResponse response = usersMapper.mapToResponse(usersService.getAllUserBookings(id, command));
        return new PageResponse<>(query.getSize(), response.getMeta(), response.getContent());
    }
}

