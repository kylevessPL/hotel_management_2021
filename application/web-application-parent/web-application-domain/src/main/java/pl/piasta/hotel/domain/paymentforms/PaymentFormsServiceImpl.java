package pl.piasta.hotel.domain.paymentforms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piasta.hotel.domainmodel.paymentforms.PaymentForm;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentFormsServiceImpl implements PaymentFormsService {

    private final PaymentFormsRepository repository;

    @Override
    public List<PaymentForm> getAllPaymentForms() {
        return repository.getAllPaymentForms();
    }
}
