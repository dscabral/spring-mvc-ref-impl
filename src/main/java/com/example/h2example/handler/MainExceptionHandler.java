package com.example.h2example.handler;

import com.example.h2example.model.ExceptionResponse;
import com.example.h2example.exception.BusinessException;
import com.example.h2example.exception.TutorialNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MainExceptionHandler {
    @ExceptionHandler({TutorialNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleServiceException(TutorialNotFoundException ex, HttpServletRequest request) {
        ExceptionResponse err = generateExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ExceptionResponse> handleServiceException(BusinessException ex, HttpServletRequest request) {
        ExceptionResponse err = generateExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    private ExceptionResponse generateExceptionResponse(HttpStatus httpStatus, String message, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.name(),
                message,
                request.getServletPath());
    }
}
