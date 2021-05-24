package pl.piasta.hotel.api.utils;

import pl.piasta.hotel.api.bookings.BookingRequest;
import pl.piasta.hotel.api.rooms.RoomPageQuery;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DatesValidator implements ConstraintValidator<ValidateDates, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if (obj instanceof BookingRequest) {
            return ((BookingRequest) obj).getEndDate().isAfter(((BookingRequest) obj).getStartDate());
        } else if (obj instanceof RoomPageQuery) {
            return ((RoomPageQuery) obj).getEndDate().isAfter(((RoomPageQuery) obj).getStartDate());
        }
        return true;
    }
}
