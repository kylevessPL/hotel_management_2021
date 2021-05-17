package pl.piasta.hotel.api.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class UpdateAccountStatusRequest implements Serializable {

    @Schema(description = "Account status", example = "LOCKED")
    @NotNull
    private AccountStatus status;
}
