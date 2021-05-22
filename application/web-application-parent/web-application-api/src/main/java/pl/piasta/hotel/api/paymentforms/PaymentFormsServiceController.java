package pl.piasta.hotel.api.paymentforms;

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
import pl.piasta.hotel.api.paymentforms.mapper.PaymentFormsMapper;
import pl.piasta.hotel.domain.paymentforms.PaymentFormsService;
import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;
import pl.piasta.hotel.dto.paymentforms.PaymentFormResponse;

import java.util.List;

@Tag(name = "Payment Forms API", description = "API performing operations on payment form resources")
@RestController
@RequestMapping("${app.api.base-path}/payment-forms")
@RequiredArgsConstructor
public class PaymentFormsServiceController {

    private final PaymentFormsMapper mapper;
    private final PaymentFormsService service;

    @SecurityRequirements
    @Operation(
            summary = "Get all payment forms",
            operationId = "getAllPaymentForms"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentFormResponse> getAllPaymentForms() {
        List<PaymentForm> paymentForms = service.getAllPaymentForms();
        return mapper.mapToResponse(paymentForms);
    }
}

