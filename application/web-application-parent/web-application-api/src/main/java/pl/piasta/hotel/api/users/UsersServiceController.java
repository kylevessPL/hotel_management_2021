package pl.piasta.hotel.api.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.piasta.hotel.api.users.mapper.UsersMapper;
import pl.piasta.hotel.api.utils.PageResponse;
import pl.piasta.hotel.api.utils.ValidateMultipartFile;
import pl.piasta.hotel.domain.security.UserDetailsImpl;
import pl.piasta.hotel.domain.users.UsersService;
import pl.piasta.hotel.domainmodel.utils.PageCommand;
import pl.piasta.hotel.dto.users.AvatarImageResponse;
import pl.piasta.hotel.dto.users.PagedUserInfo;
import pl.piasta.hotel.dto.users.UsersPageResponse;

import javax.validation.Valid;

@Tag(name = "Users API", description = "API performing operations on user resources")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersServiceController {

    private final UsersMapper mapper;
    private final UsersService service;

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
        service.updateUserPassword(id, mapper.mapToCommand(request));
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
        service.updateUserAvatar(id, file);
    }

    @Operation(
            summary = "Get current user avatar image",
            operationId = "getCurrentUserAvatar"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "404", description = "Avatar image not exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.HEAD},
            value = "/current/avatar",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public AvatarImageResponse getUserAvatar(Authentication authentication) {
        Integer id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return mapper.mapToResponse(service.getUserAvatar(id));
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
    public void updateAccountStatus(@PathVariable Integer id, @Valid @RequestBody UpdateAccountStatusRequest request) {
        service.updateAccountStatus(id, mapper.mapToCommand(request));
    }

    @Operation(
            summary = "Get all users",
            operationId = "getAllUsers"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsersPageResponse.class))),
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
    public PageResponse<PagedUserInfo> getAll(@ParameterObject @Valid UsersPageQuery query) {
        PageCommand command = mapper.mapToCommand(query);
        UsersPageResponse response = mapper.mapToResponse(service.getAllUsers(command));
        return new PageResponse<>(query.getSize(), response.getMeta(), response.getContent());
    }
}

