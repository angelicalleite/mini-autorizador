package br.com.vr.miniautorizador.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(e -> ((FieldError) e).getField(), ObjectError::getDefaultMessage));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return getErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), request.getContextPath());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        return getErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getSupportedMediaTypes().toString(), request.getContextPath());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        return getErrorMessage(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), request.getContextPath());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String errors = ex.getConstraintViolations().stream()
                .map(e -> e.getRootBeanClass().getName() + " " + e.getPropertyPath() + ": " + e.getMessage())
                .collect(Collectors.joining(","));

        return getErrorMessage(HttpStatus.BAD_REQUEST, errors, request.getContextPath());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return getErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getContextPath());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        return getErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getContextPath());
    }

    private ResponseEntity<ErrorMessage> getErrorMessage(HttpStatus status, String message, String description) {
        ErrorMessage errorMessage = ErrorMessage
                .builder()
                .statusCode(status.value())
                .message(message)
                .description(description)
                .date(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status.value()).body(errorMessage);
    }
}
