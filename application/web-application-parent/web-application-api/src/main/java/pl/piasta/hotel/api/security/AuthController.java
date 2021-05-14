package pl.piasta.hotel.api.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.security.mapper.AuthMapper;
import pl.piasta.hotel.domain.security.AuthService;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;
import pl.piasta.hotel.dto.security.UserLoginInfoResponse;

import javax.validation.Valid;

@Tag(name = "Authentication API", description = "API performing user authentication operations")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMapper mapper;
    private final AuthService service;

    @SecurityRequirements
    @Operation(
            summary = "Login user",
            operationId = "signInUser"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
            @ApiResponse(responseCode = "401", description = "Bad credentials provided"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLoginInfoResponse authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest) {
        UserLoginCommand command = mapper.mapToCommand(loginRequest);
        return mapper.mapToResponse(service.loginUser(command));
    }

    @SecurityRequirements
    @Operation(
            summary = "Register user",
            operationId = "signUpUser"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully registered user"),
            @ApiResponse(responseCode = "400", description = "Username or email already in use"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registerUser(@RequestBody @Valid UserRegisterRequest registerRequest) {
        UserRegisterCommand command = mapper.mapToCommand(registerRequest);
        service.registerUser(command);
    }
}
