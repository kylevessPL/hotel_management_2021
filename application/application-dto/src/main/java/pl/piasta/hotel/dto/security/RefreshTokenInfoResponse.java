package pl.piasta.hotel.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenInfoResponse {

    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.VFb0qJ1LRg_4ujbZoRMXnVkUgiuKq5KxWqNdbKq_G9Vvz-S1zZa9LPxtHWKa64zDl2ofkT8F6jBt_K4riU-fPg")
    private String accessToken;
    @Schema(description = "Refresh token", example = "4b1a0048-3d2f-462d-a99a-0d2f27255075")
    private String refreshToken;
    @Schema(description = "Token type", example = "Bearer")
    private String type;
    @Schema(description = "Access token expiration time in seconds", example = "3600")
    private int expires;
}
