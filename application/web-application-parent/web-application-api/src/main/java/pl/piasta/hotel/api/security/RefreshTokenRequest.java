package pl.piasta.hotel.api.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenRequest implements Serializable {

    @Schema(description = "Refresh token", example = "4b1a0048-3d2f-462d-a99a-0d2f27255075")
    @NotBlank
    @Size(max = 36)
    private String token;
}
