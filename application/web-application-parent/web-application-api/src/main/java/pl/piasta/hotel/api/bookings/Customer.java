package pl.piasta.hotel.api.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.customers.DocumentType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Schema(description = "Customer's first name", example = "James")
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @Schema(description = "Customer's last name", example = "Smith")
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;
    @Schema(description = "Customer's street name", example = "Ethels Lane")
    @NotBlank
    @Size(min = 2, max = 30)
    private String streetName;
    @Schema(description = "Customer's house number", example = "747")
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z ./]*$")
    @Size(max = 10)
    private String houseNumber;
    @Schema(description = "Customer's ZIP code", example = "11735")
    @NotBlank
    @Pattern(regexp = "^[0-9 \\-]*$")
    @Size(min = 2, max = 10)
    private String zipCode;
    @Schema(description = "Customer's city", example = "Farmingdale")
    @NotBlank
    @Size(min = 2, max = 30)
    private String city;
    @Schema(description = "Customer's document type")
    @NotNull
    private DocumentType documentType;
    @Schema(description = "Customer's document ID", example = "SB1565402")
    @NotBlank
    @Pattern(regexp = "^[0-9A-Z ]*$")
    @Size(min = 2, max = 10)
    private String documentId;
}
