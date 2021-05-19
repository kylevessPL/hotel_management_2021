package pl.piasta.hotel.api.paymentforms.mapper;

import org.mapstruct.Mapper;
import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;
import pl.piasta.hotel.dto.paymentforms.PaymentFormResponse;

import java.util.List;

@Mapper
public interface PaymentFormsMapper {

    List<PaymentFormResponse> mapToResponse(List<PaymentForm> paymentForms);
}
