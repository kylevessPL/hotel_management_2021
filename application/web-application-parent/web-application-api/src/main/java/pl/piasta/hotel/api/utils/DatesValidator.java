package pl.piasta.hotel.api.utils;

import pl.piasta.hotel.api.bookings.BookingRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatesValidator implements ConstraintValidator<ValidateDates, BookingRequest> {

    @Override
    public boolean isValid(BookingRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.getEndDate().isAfter(request.getStartDate());
    }
}
