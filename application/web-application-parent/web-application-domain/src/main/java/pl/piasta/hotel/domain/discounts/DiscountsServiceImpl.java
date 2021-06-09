package pl.piasta.hotel.domain.discounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piasta.hotel.domainmodel.discounts.DiscountCommand;
import pl.piasta.hotel.domainmodel.discounts.DiscountDetails;
import pl.piasta.hotel.domainmodel.utils.ApplicationException;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountsServiceImpl implements DiscountsService {

    private final DiscountsRepository repository;

    @Override
    @Transactional
    public void addDiscount(DiscountCommand command) {
        checkDiscoutCodeValidity(command.getCode());
        repository.addDiscount(command.getCode(), command.getValue());
    }

    @Override
    @Transactional
    public void removeDiscount(String code) {
        if (repository.removeDiscount(code) == 0) {
            throw new ApplicationException(ErrorCode.DISCOUNT_CODE_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountDetails getDiscountDetails(String code) {
        return repository.getDiscountDetails(code)
                .orElseThrow(() -> new ApplicationException(ErrorCode.DISCOUNT_CODE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDetails> getAllDiscounts() {
        return repository.getDiscounts();
    }

    private void checkDiscoutCodeValidity(String code) {
        if(repository.discountExists(code)) {
            throw new ApplicationException(ErrorCode.DISCOUNT_CODE_ALREADY_EXISTS);
        }
    }
}
