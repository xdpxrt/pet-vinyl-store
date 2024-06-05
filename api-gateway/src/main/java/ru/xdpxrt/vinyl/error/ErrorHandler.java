package ru.xdpxrt.vinyl.error;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleValidationException(BadRequestException e) {
        return new ApiError(BAD_REQUEST.name(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException e) {
        return new ApiError(NOT_FOUND.name(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({DataAccessException.class, ConflictException.class})
    @ResponseStatus(CONFLICT)
    public ApiError handleConflictException(RuntimeException e) {
        return new ApiError(CONFLICT.name(), e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiError handleServerException(RuntimeException e) {
        return new ApiError(INTERNAL_SERVER_ERROR.name(), e.getMessage(), LocalDateTime.now());
    }
}