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
    PAYMENT_FORM_NOT_FOUND("P01", "Payment form not found"),
    DISCOUNT_CODE_ALREADY_EXISTS("D01", "Discount code already exists"),
    DISCOUNT_CODE_NOT_FOUND("D02", "Discount code not exists"),
    ROOM_NOT_AVAILABLE("R01", "Room not available"),
    ROOM_NOT_FOUND("R02", "Room not found"),
    MAX_PEOPLE_EXCEEDED("R03", "Max people in room exceeded"),
    VALIDATION_FAILED("V01", "Validation failed"),
    USERNAME_OCCUPIED("A01", "Username already in use"),
    EMAIL_OCCUPIED("A02", "Email already in use"),
    BAD_CREDENTIALS("A03", "Bad credentials"),
    ACCOUNT_DISABLED("A04", "Account disabled"),
    REFRESH_TOKEN_EXPIRED("A05", "Refresh token expired"),
    REFRESH_TOKEN_NOT_FOUND("A06", "Invalid refresh token"),
    USER_NOT_FOUND("U01", "User not found"),
    USER_AVATAR_NOT_FOUND("U02", "User avatar not found"),
    FILE_UPLOAD_COMMON("C01", "File upload error");

    private final String code;
    private final String message;
}
