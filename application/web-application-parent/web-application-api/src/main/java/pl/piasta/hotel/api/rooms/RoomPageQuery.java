package pl.piasta.hotel.api.rooms;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.piasta.hotel.api.utils.SortDir;
import pl.piasta.hotel.api.utils.ValidateDates;
import pl.piasta.hotel.api.utils.ValidateString;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ValidateDates
@NoArgsConstructor
@Getter
@Setter
public class RoomPageQuery {

    @Parameter(description = "Booking start date")
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @Parameter(description = "Booking end date")
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
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
            schema = @Schema(type = "string", allowableValues = { "id", "bedAmount", "standardPrice" }, defaultValue = "id"))
    @ValidateString(acceptedValues = { "id", "bedAmount", "standardPrice" })
    private String sortBy = "id";
    @Parameter(description = "Sort direction")
    private SortDir sortDir = SortDir.ASC;
}
