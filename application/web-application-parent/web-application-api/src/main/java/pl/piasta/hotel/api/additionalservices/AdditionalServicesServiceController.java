package pl.piasta.hotel.api.additionalservices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piasta.hotel.api.additionalservices.mapper.AdditionalServicesMapper;
import pl.piasta.hotel.domain.additionalservices.AdditionalServicesService;
import pl.piasta.hotel.domainmodel.additionalservices.AdditionalService;
import pl.piasta.hotel.dto.additionalservices.AdditionalServiceResponse;

import java.util.List;

@Tag(name = "Additional Services API", description = "API performing operations on additional service resources")
@RestController
@RequestMapping("/additional-services")
@RequiredArgsConstructor
public class AdditionalServicesServiceController {

    private final AdditionalServicesMapper mapper;
    private final AdditionalServicesService service;

    @SecurityRequirements
    @Operation(
            summary = "Get all additional services",
            operationId = "getAllAdditionalServices"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdditionalServiceResponse> getAllAdditionalServices() {
        List<AdditionalService> additionalServices = service.getAllAdditionalServices();
        return mapper.mapToResponse(additionalServices);
    }
}

