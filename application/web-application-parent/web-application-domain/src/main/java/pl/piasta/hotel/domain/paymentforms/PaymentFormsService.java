package pl.piasta.hotel.domain.paymentforms;

import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;

import java.util.List;

public interface PaymentFormsService {

    List<PaymentForm> getAllPaymentForms();
}
