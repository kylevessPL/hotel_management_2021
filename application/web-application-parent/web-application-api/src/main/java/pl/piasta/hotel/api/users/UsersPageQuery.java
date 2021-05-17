package pl.piasta.hotel.api.users;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.api.utils.SortDir;
import pl.piasta.hotel.api.utils.ValidateString;

import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter
@Setter
public class UsersPageQuery {

    @Parameter(
            description = "Page number",
            schema = @Schema(type = "integer", defaultValue = "1"))
    @Min(value = 1, message = "Page number must be a positive integer value")
    private Integer page = 1;
    @Parameter(
            description = "Page size",
            schema = @Schema(type = "integer", defaultValue = "20"))
    @Min(value = 1, message = "Page size must be a positive integer value")
    private Integer size = 20;
    @Parameter(
            description = "Value to sort by",
            schema = @Schema(type = "string", allowableValues = { "id, username, email, roles, status" }, defaultValue = "id"))
    @ValidateString(acceptedValues = { "id", "username", "email", "roles", "status" })
    private String sortBy = "id";
    @Parameter(description = "Sort direction")
    private SortDir sortDir = SortDir.ASC;

}
