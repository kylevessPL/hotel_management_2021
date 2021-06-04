package pl.piasta.hotel.api.users;

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
public class UpdateUserPasswordRequest implements Serializable {

    @Schema(description = "Old password", example = "3cvCD#&YSl6HO7ncGs$O")
    @NotBlank
    private String oldPassword;
    @Schema(description = "New password", example = "7GJ13T%lwqXaJYo2@^")
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).+$")
    @Size(min = 6, max = 120)
    private String newPassword;
}
