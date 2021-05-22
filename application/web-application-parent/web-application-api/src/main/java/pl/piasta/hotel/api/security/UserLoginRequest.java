package pl.piasta.hotel.api.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class UserLoginRequest implements Serializable {

    @Schema(description = "User username", example = "john.doe")
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @Schema(description = "User password", example = "7GJ13T%lwqXaJYo2@^")
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).+$")
    @Size(min = 6, max = 120)
    private String password;
}
