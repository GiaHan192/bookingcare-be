package com.company.myweb.controller.advice;

import com.company.myweb.entity.common.ApiException;
import com.company.myweb.entity.common.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {
    /**
     * Handle api e, e throw if api error
     *
     * @param e ApiException
     * @return ResponseEntity
     */
    private final ObjectMapper objectMapper;

    public AdviceController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
        e.printStackTrace();
        if (HttpStatus.FORBIDDEN.equals(e.getStatus()) && e.getMessage() == null) {
            e.setMessage("Bạn không có quyền truy cập!");
        }
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.failed(e.getMessage()));
    }

    /**
     * Handle bad request, exception throw if request body not match
     *
     * @param e ApiException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }

    /**
     * Handle method argument not valid, exception throw if method argument not valid
     *
     * @param e ApiException
     * @return ResponseEntity
     */
    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        try {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failed(objectMapper.writeValueAsString(errors)));
        } catch (JsonProcessingException ex) {
            throw ApiException.create(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(ex.getMessage());
        }
    }

    /**
     * Handle exception, exception throw while running project
     *
     * @param exception ApiException
     * @return ResponseEntity
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed(exception.getMessage()));
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failed(message));
    }

    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiResponse<?>> handleException(InvalidDataAccessResourceUsageException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failed("Tham số đầu vào đang sai"));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed("Bạn không có quyền truy cập!"));
    }
}
