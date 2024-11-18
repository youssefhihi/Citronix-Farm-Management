package com.ys.citronix.sharedkernel.api;

import com.ys.citronix.farmManagement.domain.exception.FieldCreationException;
import com.ys.citronix.farmManagement.domain.exception.TreeCreationException;
import com.ys.citronix.harvestManagement.domain.exception.HarvestCreationException;
import com.ys.citronix.sharedkernel.domain.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions for invalid method arguments annotated with @Valid.
     * For example, when input data does not meet validation constraints.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles exceptions when validation constraints are violated in @Validated methods.
     * For example, when a method parameter violates @Size, @NotNull, etc.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles exceptions when the HTTP method is not supported by the endpoint.
     * For example, sending a POST request to an endpoint that only supports GET.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "HTTP method not supported: " + ex.getMethod();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(message);
    }


    /**
     * Handles exceptions when an illegal argument is passed to a method.
     * For example, passing a null or invalid value where a valid value is required.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles exceptions when a user attempts to access a resource they are not authorized to.
     * For example, accessing an admin-only resource as a regular user.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: " + ex.getMessage());
    }

    /**
     * Handles exceptions when no handler is found for the requested URL and HTTP method.
     * For example, accessing a non-existent endpoint.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String message = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }




    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found Error : " + e.getMessage());
    }

    @ExceptionHandler(FieldCreationException.class)
    public ResponseEntity<Object> handleNotFoundException(FieldCreationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Field Creation Error : " + e.getMessage());
    }

    @ExceptionHandler(TreeCreationException.class)
    public ResponseEntity<Object> handleNotFoundException(TreeCreationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tree Creation Error : " + e.getMessage());
    }
    @ExceptionHandler(HarvestCreationException.class)
    public ResponseEntity<Object> handleNotFoundException(HarvestCreationException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Harvest Creation Error : " + e.getMessage());
    }
}
