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
    PAYMENT_FORM_NOT_FOUND("P01", "Payment form not found"),
    DISCOUNT_CODE_ALREADY_EXISTS("D01", "Discount code already exists"),
    DISCOUNT_CODE_NOT_FOUND("D02", "Discount code not exists"),
    ROOM_NOT_AVAILABLE("R01", "Room not available"),
    ROOM_NOT_FOUND("R02", "Room not found"),
    MAX_PEOPLE_EXCEEDED("R03", "Max people in room exceeded"),
    VALIDATION_FAILED("V01", "Validation failed"),
    USERNAME_OCCUPIED("A01", "Username already in use"),
    EMAIL_OCCUPIED("A02", "Email already in use"),
    ACCOUNT_DISABLED("A03", "Account disabled"),
    REFRESH_TOKEN_EXPIRED("A04", "Refresh token expired"),
    REFRESH_TOKEN_NOT_FOUND("A05", "Refresh token invalid or already used"),
    USER_AVATAR_NOT_FOUND("U01", "User avatar not found"),
    FILE_UPLOAD_COMMON("C01", "File upload error");

    private final String code;
    private final String message;
}
