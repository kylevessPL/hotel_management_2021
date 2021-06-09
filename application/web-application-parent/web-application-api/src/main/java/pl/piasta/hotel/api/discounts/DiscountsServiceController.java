package pl.piasta.hotel.api.discounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piasta.hotel.api.discounts.mapper.DiscountsMapper;
import pl.piasta.hotel.domain.discounts.DiscountsService;
import pl.piasta.hotel.domainmodel.discounts.DiscountCommand;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.dto.discounts.DiscountDetailsResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Tag(name = "Discounts API", description = "API performing operations on discount resources")
@Validated
@RestController
@RequestMapping("${app.api.base-path}/discounts")
@RequiredArgsConstructor
public class DiscountsServiceController {

    private final DiscountsService service;
    private final DiscountsMapper mapper;

    @SecurityRequirements
    @Operation(
            summary = "Get all discounts",
            operationId = "getAllDiscounts"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DiscountDetailsResponse> getAllDiscounts() {
        List<DiscountDetails> discounts = service.getAllDiscounts();
        return mapper.mapToResponse(discounts);
    }

    @Operation(
            summary = "Add discount",
            operationId = "addDiscount"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success",
                    content = @Content,
                    headers = @Header(
                            name = HttpHeaders.LOCATION,
                            description = "Location of the created resource",
                            schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "409", description = "Discount code already exists", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void addDiscount(@RequestBody @Valid DiscountRequest discountRequest, HttpServletResponse response) {
        DiscountCommand command = mapper.mapToCommand(discountRequest);
        service.addDiscount(command);
        String path = ServletUriComponentsBuilder.fromCurrentRequest()
                .queryParam("code", discountRequest.getCode())
                .encode()
                .build()
                .toUriString();
        response.setHeader(HttpHeaders.LOCATION, path);
        response.setContentLength(0);
    }

    @Operation(
            summary = "Remove discount",
            operationId = "removeDiscount"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax"),
            @ApiResponse(responseCode = "409", description = "Invalid discount code"),
            @ApiResponse(responseCode = "422", description = "Validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeDiscount(
            @Parameter(description = "Discount code") @RequestParam
            @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]*$") @Size(min = 2, max = 20) String code) {
        service.removeDiscount(code);
    }

    @SecurityRequirements
    @Operation(
            summary = "Get discount details",
            operationId = "getDiscountDetails"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Malformed request syntax", content = @Content),
            @ApiResponse(responseCode = "409", description = "Discount code does not exist", content = @Content),
            @ApiResponse(responseCode = "422", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public DiscountDetailsResponse getDiscountDetails(
            @Parameter(description = "Discount code") @RequestParam
            @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]*$") @Size(min = 2, max = 20) String code) {
        DiscountDetails discountDetails = service.getDiscountDetails(code);
        return mapper.mapToResponse(discountDetails);
    }
}
