package pl.piasta.hotel.api.utils;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.piasta.hotel.domainmodel.utils.ApplicationException;
import pl.piasta.hotel.domainmodel.utils.ErrorCode;
import pl.piasta.hotel.domainmodel.utils.FileUploadException;
import pl.piasta.hotel.domainmodel.utils.ResourceNotFoundException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, AccessDeniedException.class})
    protected ResponseEntity<Object> handleResourceNotFoundError(@NonNull WebRequest request,
                                                                 @NonNull HttpServletResponse response,
                                                                 @Nullable Object body,
                                                                 @NonNull Exception ex) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> handleMaxUploadSizeExceededError(MaxUploadSizeExceededException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.PAYLOAD_TOO_LARGE, request);
    }

    @ExceptionHandler({FileUploadException.class, MultipartException.class})
    protected ResponseEntity<Object> handleFileUploadError(FileUploadException ex) {
        HttpStatus status = HttpStatus.EXPECTATION_FAILED;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ex.getErrorCode().getCode(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleBookingError(ApplicationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ex.getErrorCode().getCode(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    protected ResponseEntity<Object> handleValidationError(Exception ex) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ErrorCode.VALIDATION_FAILED.getCode(),
                ErrorCode.VALIDATION_FAILED.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler({AuthenticationException.class, JwtException.class})
    protected ResponseEntity<Object> handleJwtExceptionError(@NonNull WebRequest request,
                                                             @NonNull HttpServletResponse response,
                                                             @Nullable Object body,
                                                             @NonNull Exception ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                "",
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler(DisabledException.class)
    protected ResponseEntity<Object> handleAccountDisabledError(DisabledException ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        logger.warn(status.toString(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                ErrorCode.ACCOUNT_DISABLED.getCode(),
                ErrorCode.ACCOUNT_DISABLED.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), status);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> anyExceptionHandler(Exception ex, WebRequest request) {
        try {
            return super.handleException(ex, request);
        } catch (Exception e) {
            return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        return handleValidationError(ex);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        return handleValidationError(ex);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex,
                                                             @Nullable Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatus status,
                                                             @NonNull WebRequest request) {
        logger.warn(status.toString(), ex);
        ErrorResponse errorBody = new ErrorResponse(
                status.value(),
                "",
                status.getReasonPhrase());
        return super.handleExceptionInternal(ex, errorBody, headers, status, request);
    }
}
