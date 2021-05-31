package pl.piasta.hotel.api.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.security.mapper.AuthMapper;
import pl.piasta.hotel.domain.security.AuthService;
import pl.piasta.hotel.domainmodel.security.RefreshTokenInfo;
import pl.piasta.hotel.domainmodel.security.TokenInfo;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;
import pl.piasta.hotel.domainmodel.utils.RefreshTokenException;
import pl.piasta.hotel.dto.security.RefreshTokenInfoResponse;
import pl.piasta.hotel.dto.security.TokenInfoResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Duration;

@Tag(name = "Authentication API", description = "API performing user authentication operations")
@RestController
@RequestMapping("${app.api.base-path}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMapper mapper;
    private final AuthService service;

    @Value("${app.security.refresh-token.expiration}")
    private int jwtRefreshExpirationMs;

    @SecurityRequirements
    @Operation(
            summary = "Authenticate user and get token",
            operationId = "signInUser"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials provided", content = @Content),
            @ApiResponse(responseCode = "403", description = "Account disabled", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenInfoResponse authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
        UserLoginCommand command = mapper.mapToCommand(loginRequest);
        TokenInfo tokenInfo = service.authenticateUser(command);
        ResponseCookie cookie = ResponseCookie.from("rftoken", tokenInfo.getRefreshToken())
                .path("/api/v1/auth")
                .maxAge(Duration.ofMillis(jwtRefreshExpirationMs))
                .httpOnly(true)
                .sameSite("Strict")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return mapper.mapToResponse(tokenInfo);
    }

    @SecurityRequirements
    @Operation(
            summary = "Register user",
            operationId = "signUpUser"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "409", description = "Username or email already in use"),
            @ApiResponse(responseCode = "422", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody @Valid UserRegisterRequest registerRequest) {
        UserRegisterCommand command = mapper.mapToCommand(registerRequest);
        service.registerUser(command);
    }

    @SecurityRequirements
    @Operation(
            summary = "Refresh access token",
            operationId = "refreshToken"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "409", description = "Invalid or expired refresh token provided", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public RefreshTokenInfoResponse refreshToken(
            @CookieValue(value = "rftoken", required = false) String cookieValue,
            @Valid @RequestBody(required = false) RefreshTokenRequest tokenRequest,
            HttpServletResponse response) {
        String token;
        if (cookieValue != null) {
            token = cookieValue;
        } else if (tokenRequest != null) {
            token = tokenRequest.getToken();
        } else {
            throw new RefreshTokenException();
        }
        RefreshTokenInfo refreshTokenInfo = service.refreshToken(token);
        ResponseCookie cookie = ResponseCookie.from("rftoken", refreshTokenInfo.getRefreshToken())
                .path("/api/v1/auth")
                .maxAge(Duration.ofMillis(jwtRefreshExpirationMs))
                .httpOnly(true)
                .sameSite("Strict")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return mapper.mapToResponse(refreshTokenInfo);
    }
}
