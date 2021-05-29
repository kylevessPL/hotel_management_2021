package pl.piasta.hotel.api.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class UserLoginRequest implements Serializable {

    @Schema(description = "User username", example = "john.doe")
    @NotBlank
    private String username;
    @Schema(description = "User password", example = "7GJ13T%lwqXaJYo2@^")
    @NotBlank
    private String password;
}
