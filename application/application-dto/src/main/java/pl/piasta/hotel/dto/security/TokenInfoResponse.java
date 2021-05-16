package pl.piasta.hotel.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class TokenInfoResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.VFb0qJ1LRg_4ujbZoRMXnVkUgiuKq5KxWqNdbKq_G9Vvz-S1zZa9LPxtHWKa64zDl2ofkT8F6jBt_K4riU-fPg")
    private String accessToken;
    @Schema(description = "JWT refresh token", example = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.VFb0qJ1LRg_4ujbZoRMXnVkUgiuKq5KxWqNdbKq_G9Vvz-S1zZa9LPxtHWKa64zDl2ofkT8F6jBt_K4riU-fPg")
    private String refreshToken;
    @Schema(description = "Token type", example = "Bearer")
    private String type;
    @Schema(description = "Access token expiration time in seconds", example = "3600")
    private int expires;
    @Schema(description = "User id", example = "1")
    private Integer id;
    @Schema(description = "User username", example = "john.doe")
    private String username;
    @Schema(description = "User email", example = "example@example.com")
    private String email;
    @Schema(description = "User roles", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
    private Set<String> roles;
}
