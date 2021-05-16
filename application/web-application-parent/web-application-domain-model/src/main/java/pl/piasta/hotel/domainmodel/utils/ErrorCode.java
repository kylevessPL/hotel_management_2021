package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    ADDITIONAL_SERVICE_NOT_FOUND("AS01", "Additional service not found"),
    BOOKING_ALREADY_CANCELLED("B01", "Booking already cancelled"),
    BOOKING_ALREADY_CONFIRMED("B02", "Booking already confirmed"),
    BOOKING_EXPIRED("B03", "Booking expired"),
    BOOKING_NOT_FOUND("B04", "Booking not found"),
    BOOKING_NOT_OWNED("B05", "Booking not owned"),
    PAYMENT_FORM_NOT_FOUND("P01", "Payment form not found"),
    ROOM_NOT_AVAILABLE("R01", "Room not available"),
    ROOM_NOT_FOUND("R02", "Room not found"),
    VALIDATION_FAILED("V01", "Validation failed"),
    USERNAME_OCCUPIED("A01", "Username already in use"),
    EMAIL_OCCUPIED("A02", "Email already in use"),
    BAD_CREDENTIALS("A03", "Bad credentials"),
    REFRESH_TOKEN_EXPIRED("A04", "Refresh token expired"),
    REFRESH_TOKEN_NOT_FOUND("A05", "Invalid refresh token");

    private final String code;
    private final String message;

}
