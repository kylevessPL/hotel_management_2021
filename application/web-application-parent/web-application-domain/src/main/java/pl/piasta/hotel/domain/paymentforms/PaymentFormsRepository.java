package pl.piasta.hotel.domain.paymentforms;

import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;

import java.util.List;

public interface PaymentFormsRepository {

    List<PaymentForm> getAllPaymentForms();
    PaymentForm getPaymentForm(Integer paymentFormId);
}
