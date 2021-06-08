package pl.piasta.hotel.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class PagedUserInfo {

    @Schema(description = "User id", example = "1")
    private Integer id;
    @Schema(description = "User username", example = "john.doe")
    private String username;
    @Schema(description = "User email", example = "example@example.com")
    private String email;
    @Schema(description = "Account roles", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]")
    private String[] roles;
    @Schema(description = "Account status", example = "DISABLED")
    private String status;
}
